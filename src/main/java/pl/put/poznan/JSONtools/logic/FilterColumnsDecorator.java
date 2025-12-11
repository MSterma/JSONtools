package pl.put.poznan.JSONtools.logic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.node.ArrayNode;
/**
 * This class is used to filter fields from given Json. Variable excludeMode specifies whether given fields
 *  should be included or excluded from output structure (Concrete Decorator).
 */
public class FilterColumnsDecorator extends JsonDecorator {

    private final List<String> targetFields;
    private final ObjectMapper mapper = new ObjectMapper();
    private  final boolean excludeMode;
    /**
     * This constructor initializes decoratedComponent, list of fields to keep in output Json, default value of mode flag.
     */
    public FilterColumnsDecorator(JsonProcessorComponent decoratedComponent, List<String> targetFields) {
        super(decoratedComponent);
        this.targetFields = targetFields;
        this.excludeMode=false;
    }
    /**
     * This constructor initializes decoratedComponent, list of targetFields from output Json, and excludeMode flag.
     * Depending on flag value targetFields are treated as fields to keep (false) or fields to exclude (true).
     */
    public FilterColumnsDecorator(JsonProcessorComponent decoratedComponent, List<String> targetFields, boolean excludeMode) {
        super(decoratedComponent);
        this.targetFields = targetFields;
        this.excludeMode=excludeMode;
    }
    /**
     * @return Json structure for decorators' purposes
     */
    @Override
    public JsonNode getJsonNode() throws JsonProcessingException {
        JsonNode originalNode = decoratedComponent.getJsonNode();

        JsonNode copiedNode = originalNode.deepCopy();
    if(excludeMode){
        filterExclude(copiedNode);
    }else{
        filter(copiedNode);
    }


        return copiedNode;
    }

    /**
     * Recursive method searches through Json represented as tree  in order to include specified fields
     */
    private void filter(JsonNode node) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;

            List<String> fieldNames = new ArrayList<>();
            objectNode.fieldNames().forEachRemaining(fieldNames::add);

            for (String fieldName : fieldNames) {
                if (targetFields.contains(fieldName)) {
                    filter(objectNode.get(fieldName));
                } else {
                    objectNode.remove(fieldName);
                }
            }
        } else if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            for (JsonNode item : arrayNode) {
                filter(item);
            }
        }
    }
    /**
     * Recursive method searches through Json represented as tree  in order to exclude specified fields
     */
    private void filterExclude(JsonNode node) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;
            List<String> fieldNames = new ArrayList<>();
            objectNode.fieldNames().forEachRemaining(fieldNames::add);
            for (String fieldName : fieldNames) {
                if (targetFields.contains(fieldName)) {
                    objectNode.remove(fieldName);
                } else {
                    filterExclude(objectNode.get(fieldName));
                }
            }
        } else if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            for (JsonNode item : arrayNode) {
                filterExclude(item);
            }
        }
    }

    /**
     * @return Json structure  with or without specified fields depending on mode flag
     */
    @Override
    public String getProcessedJson() throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getJsonNode());
    }
}