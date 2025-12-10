package pl.put.poznan.JSONtools.logic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Arrays;
import java.util.List;

public class TextLineDiffDecorator extends JsonDecorator {

    private final ObjectMapper mapper = new ObjectMapper();

    public TextLineDiffDecorator(JsonProcessorComponent decoratedComponent) {
        super(decoratedComponent);
    }

    @Override
    public JsonNode getJsonNode() throws JsonProcessingException {
        JsonNode inputNode = decoratedComponent.getJsonNode();

        if (!inputNode.has("fileA") || !inputNode.has("fileB")) {
            throw new IllegalArgumentException("JSON wejściowy do porównania musi zawierać pola 'fileA' i 'fileB'.");
        }

        String textA = inputNode.get("fileA").asText();
        String textB = inputNode.get("fileB").asText();

        List<String> linesA = Arrays.asList(textA.split("\\r?\\n"));
        List<String> linesB = Arrays.asList(textB.split("\\r?\\n"));

        ObjectNode resultNode = mapper.createObjectNode();
        ArrayNode diffsNode = mapper.createArrayNode();

        int maxLines = Math.max(linesA.size(), linesB.size());
        int diffCount = 0;

        for (int i = 0; i < maxLines; i++) {
            String lineA = (i < linesA.size()) ? linesA.get(i) : null;
            String lineB = (i < linesB.size()) ? linesB.get(i) : null;

            boolean areDifferent = false;

            if (lineA == null || lineB == null) {
                areDifferent = true;
            } else if (!lineA.equals(lineB)) {
                areDifferent = true;
            }

            if (areDifferent) {
                diffCount++;
                ObjectNode lineDiff = mapper.createObjectNode();
                lineDiff.put("line", i + 1);

                if (lineA != null) lineDiff.put("fileA", lineA.trim());
                if (lineB != null) lineDiff.put("fileB", lineB.trim());

                diffsNode.add(lineDiff);
            }
        }

        resultNode.put("status", diffCount == 0 ? "identical" : "different");
        resultNode.put("total_differences", diffCount);
        resultNode.set("details", diffsNode);

        return resultNode;
    }

    @Override
    public String getProcessedJson() throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getJsonNode());
    }
}