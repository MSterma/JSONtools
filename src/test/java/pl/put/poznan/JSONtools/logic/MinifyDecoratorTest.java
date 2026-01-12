package pl.put.poznan.JSONtools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MinifyDecoratorTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testMinifySimpleJson() throws JsonProcessingException {
        String jsonString = "{ \"name\" : \"John\", \"age\" : 30 }";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(jsonString);
        MinifyDecorator decorator = new MinifyDecorator(baseComponent);

        String result = decorator.getProcessedJson();
        assertEquals("{\"name\":\"John\",\"age\":30}", result);
    }

    @Test
    void testMinifyNestedJson() throws JsonProcessingException {
        String jsonString = "{ \"user\" : { \"id\" : 1, \"active\" : true } }";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(jsonString);
        MinifyDecorator decorator = new MinifyDecorator(baseComponent);

        String result = decorator.getProcessedJson();
        assertEquals("{\"user\":{\"id\":1,\"active\":true}}", result);
    }

    @Test
    void testMinifyArrayJson() throws JsonProcessingException {
        String jsonString = "[ \"apple\", \"banana\", \"cherry\" ]";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(jsonString);
        MinifyDecorator decorator = new MinifyDecorator(baseComponent);

        String result = decorator.getProcessedJson();
        assertEquals("[\"apple\",\"banana\",\"cherry\"]", result);
    }

    @Test
    void testMinifyEmptyJson() throws JsonProcessingException {
        String jsonString = "{}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(jsonString);
        MinifyDecorator decorator = new MinifyDecorator(baseComponent);

        String result = decorator.getProcessedJson();
        assertEquals("{}", result);
    }

    @Test
    void testMinifyJsonWithSpacesInValues() throws JsonProcessingException {
        String jsonString = "{ \"description\" : \"To jest tekst ze spacjami\" }";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(jsonString);
        MinifyDecorator decorator = new MinifyDecorator(baseComponent);

        String result = decorator.getProcessedJson();
        assertEquals("{\"description\":\"To jest tekst ze spacjami\"}", result);
    }

    @Test
    void testGetJsonNodeIdentity() throws JsonProcessingException {
        String jsonString = "{\"key\":\"val\"}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(jsonString);
        MinifyDecorator decorator = new MinifyDecorator(baseComponent);

        JsonNode actualNode = decorator.getJsonNode();

        assertEquals("val", actualNode.get("key").asText());
        assertEquals(baseComponent.getJsonNode(), actualNode);
    }
}