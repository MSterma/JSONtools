package pl.put.poznan.JSONtools.logic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.node.ArrayNode;
/**
 * This class is used to filter fields from given Json (Concrete decorator).
 */
public class FilterColumnsDecorator extends JsonDecorator {
    /**
     * Fields that should remain in output Json structure
     */
    private final List<String> targetFields;
    /**
     * Jackson object used to convert serialized Json to string containing formatted Json
     */
    private final ObjectMapper mapper = new ObjectMapper();
    /**
     * Depending on this variable's value targetFields are treated as fields to keep (false) or fields to exclude (true)
     */
    private  final boolean excludeMode;
    /**
     * This constructor initializes decoratedComponent, list of fields to keep in output Json, default value of mode flag.
     *  @param decoratedComponent Object that implements JsonProcessorComponent such as BaseJsonComponent or any concrete decorator object
     */
    public FilterColumnsDecorator(JsonProcessorComponent decoratedComponent, List<String> targetFields) {
        super(decoratedComponent);
        this.targetFields = targetFields;
        this.excludeMode=false;
    }
    /**
     * This constructor initializes decoratedComponent, list of targetFields from output Json, and excludeMode flag.
     *   @param decoratedComponent Object that implements JsonProcessorComponent such as BaseJsonComponent or any concrete decorator object
      *  @param targetFields Fields that should remain in output Json structure
     * @param excludeMode Depending on this variable's value targetFields are treated as fields to keep (false) or fields to exclude (true)
     */
    public FilterColumnsDecorator(JsonProcessorComponent decoratedComponent, List<String> targetFields, boolean excludeMode) {
        super(decoratedComponent);
        this.targetFields = targetFields;
        this.excludeMode=excludeMode;
    }
    /**
     * Invokes correct filtration method and returns filtered serialized Json structure
     * @return Json structure for decorators' purposes
     * @throws JsonProcessingException if input Json structure is invalid
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
     * @param node rootNode of type JsonNode which contains Json structure serialized into object via Jackson
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
     * @param node rootNode of type JsonNode which contains Json structure serialized into object via Jackson
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
     * Transforms serialized Json into string
     * @return Json structure  with or without specified fields depending on mode flag
     * @throws JsonProcessingException if input Json structure is invalid
     */
    @Override
    public String getProcessedJson() throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getJsonNode());
    }
}