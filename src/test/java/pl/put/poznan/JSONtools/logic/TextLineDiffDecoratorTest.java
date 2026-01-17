package pl.put.poznan.JSONtools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TextLineDiffDecoratorTest {

    @Test
    void testThrowsWhenFileAMissing() throws JsonProcessingException {
        String json = "{\"fileB\": \"content\"}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);
        TextLineDiffDecorator decorator = new TextLineDiffDecorator(baseComponent);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            decorator.getJsonNode();
        });
        assertTrue(thrown.getMessage().contains("fileA"));
    }

    @Test
    void testThrowsWhenFileBMissing() throws JsonProcessingException {
        String json = "{\"fileA\": \"content\"}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);
        TextLineDiffDecorator decorator = new TextLineDiffDecorator(baseComponent);

        assertThrows(IllegalArgumentException.class, () -> decorator.getJsonNode());
    }

    @Test
    void testIdenticalTexts() throws JsonProcessingException {
        String json = "{\"fileA\": \"line1\\nline2\", \"fileB\": \"line1\\nline2\"}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);
        TextLineDiffDecorator decorator = new TextLineDiffDecorator(baseComponent);

        JsonNode result = decorator.getJsonNode();

        assertEquals("identical", result.get("status").asText());
        assertEquals(0, result.get("total_differences").asInt());
        assertEquals(0, result.get("details").size());
    }

    @Test
    void testDifferentContentSameLength() throws JsonProcessingException {
        String json = "{\"fileA\": \"line1\\nlineA\", \"fileB\": \"line1\\nlineB\"}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);
        TextLineDiffDecorator decorator = new TextLineDiffDecorator(baseComponent);

        JsonNode result = decorator.getJsonNode();

        assertEquals("different", result.get("status").asText());
        assertEquals(1, result.get("total_differences").asInt());
        assertEquals(2, result.get("details").get(0).get("line").asInt()); // Druga linia różna
        assertEquals("lineA", result.get("details").get(0).get("fileA").asText());
    }

    @Test
    void testFileALonger() throws JsonProcessingException {
        String json = "{\"fileA\": \"line1\\nline2\", \"fileB\": \"line1\"}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);
        TextLineDiffDecorator decorator = new TextLineDiffDecorator(baseComponent);

        JsonNode result = decorator.getJsonNode();

        assertEquals(1, result.get("total_differences").asInt());
        assertTrue(result.get("details").get(0).has("fileA"));
        // fileB nie ma tej linii, w wyniku JSON powinno być albo brak pola albo null (zależnie od implementacji)
        // W dostarczonym kodzie: if (lineB != null) put... więc pola nie będzie
        assertFalse(result.get("details").get(0).has("fileB"));
    }

    @Test
    void testFileBLonger() throws JsonProcessingException {
        String json = "{\"fileA\": \"line1\", \"fileB\": \"line1\\nline2\"}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);
        TextLineDiffDecorator decorator = new TextLineDiffDecorator(baseComponent);

        JsonNode result = decorator.getJsonNode();

        assertEquals(1, result.get("total_differences").asInt());
        assertFalse(result.get("details").get(0).has("fileA"));
        assertTrue(result.get("details").get(0).has("fileB"));
    }

    @Test
    void testEmptyFiles() throws JsonProcessingException {
        String json = "{\"fileA\": \"\", \"fileB\": \"\"}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);
        TextLineDiffDecorator decorator = new TextLineDiffDecorator(baseComponent);

        JsonNode result = decorator.getJsonNode();

        assertEquals("identical", result.get("status").asText());
        assertEquals(0, result.get("total_differences").asInt());
    }

    @Test
    void testGetProcessedJsonOutput() throws JsonProcessingException {
        String json = "{\"fileA\": \"abc\", \"fileB\": \"def\"}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);
        TextLineDiffDecorator decorator = new TextLineDiffDecorator(baseComponent);

        String resultString = decorator.getProcessedJson();

        assertTrue(resultString.contains("status"));
        assertTrue(resultString.contains("details"));

        assertTrue(resultString.contains("\n") || resultString.contains("\r\n"));
    }
}