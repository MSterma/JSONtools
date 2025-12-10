package pl.put.poznan.JSONtools.logic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.ArrayList;
/**
 * Concrete Decorator: Filtruje pola w JSON.
 */


import com.fasterxml.jackson.databind.node.ArrayNode;

public class FilterColumnsDecorator extends JsonDecorator {

    private final List<String> fieldsToKeep;
    private final ObjectMapper mapper = new ObjectMapper();
    private  final boolean excludeMode;

    public FilterColumnsDecorator(JsonProcessorComponent decoratedComponent, List<String> fieldsToKeep) {
        super(decoratedComponent);
        this.fieldsToKeep = fieldsToKeep;
        this.excludeMode=false;
    }
    public FilterColumnsDecorator(JsonProcessorComponent decoratedComponent, List<String> fieldsToKeep, boolean excludeMode) {
        super(decoratedComponent);
        this.fieldsToKeep = fieldsToKeep;
        this.excludeMode=excludeMode;
    }


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
                if (fieldsToKeep.contains(fieldName)) {
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
                if (fieldsToKeep.contains(fieldName)) {
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
     * @return Json structure  with specified or without specified fields depending on mode flag
     */
    @Override
    public String getProcessedJson() throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getJsonNode());
    }
}