package pl.put.poznan.JSONtools.logic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
/**
 * Concrete Decorator: Filtruje pola w JSON.
 */


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;

public class FilterColumnsDecorator extends JsonDecorator {

    private final List<String> fieldsToKeep;
    private final ObjectMapper mapper = new ObjectMapper();

    public FilterColumnsDecorator(JsonProcessorComponent decoratedComponent, List<String> fieldsToKeep) {
        super(decoratedComponent);
        this.fieldsToKeep = fieldsToKeep;
    }

    @Override
    public JsonNode getJsonNode() throws JsonProcessingException {
        JsonNode originalNode = decoratedComponent.getJsonNode();

        JsonNode copiedNode = originalNode.deepCopy();

        Filter(copiedNode);

        return copiedNode;
    }

    /**
     * Recursive method searches through Json represented as tree  in order to filter out columns
     */
    private void Filter(JsonNode node) {
        if (node.isObject()) {
            ObjectNode objectNode = (ObjectNode) node;

            List<String> fieldNames = new ArrayList<>();
            objectNode.fieldNames().forEachRemaining(fieldNames::add);

            for (String fieldName : fieldNames) {
                if (fieldsToKeep.contains(fieldName)) {
                    Filter(objectNode.get(fieldName));
                } else {
                    objectNode.remove(fieldName);
                }
            }
        } else if (node.isArray()) {
            ArrayNode arrayNode = (ArrayNode) node;
            for (JsonNode item : arrayNode) {
                Filter(item);
            }
        }
    }
    /**
     * @return Json structure for decorators' purposes
     */
    @Override
    public String getProcessedJson() throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getJsonNode());
    }
}