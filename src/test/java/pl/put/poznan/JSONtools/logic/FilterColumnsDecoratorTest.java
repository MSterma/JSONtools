package pl.put.poznan.JSONtools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterColumnsDecoratorTest {

    @Test
    void testIncludeSimpleField() throws JsonProcessingException {
        String json = "{\"keep\":\"yes\", \"remove\":\"no\"}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(baseComponent, Collections.singletonList("keep"), false);
        JsonNode result = decorator.getJsonNode();

        assertTrue(result.has("keep"));
        assertFalse(result.has("remove"));
    }

    @Test
    void testIncludeNestedField() throws JsonProcessingException {
        String json = "{\"root\": {\"childKeep\":\"yes\", \"childRemove\":\"no\"}}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(baseComponent, Arrays.asList("root", "childKeep"), false);
        JsonNode result = decorator.getJsonNode();

        assertNotNull(result.get("root"));
        assertTrue(result.get("root").has("childKeep"));
        assertFalse(result.get("root").has("childRemove"));
    }

    @Test
    void testIncludeFieldInArray() throws JsonProcessingException {
        String json = "[{\"id\":1, \"data\":\"a\"}, {\"id\":2, \"data\":\"b\"}]";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(baseComponent, Collections.singletonList("id"), false);
        JsonNode result = decorator.getJsonNode();

        assertEquals(1, result.get(0).get("id").asInt());
        assertFalse(result.get(0).has("data"));
    }

    @Test
    void testIncludeEmptyTargetList() throws JsonProcessingException {
        String json = "{\"a\":1, \"b\":2}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(baseComponent, Collections.emptyList(), false);
        JsonNode result = decorator.getJsonNode();

        assertEquals(0, result.size());
    }

    @Test
    void testIncludeMultipleFields() throws JsonProcessingException {
        String json = "{\"a\":1, \"b\":2, \"c\":3}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(baseComponent, Arrays.asList("a", "c"), false);
        JsonNode result = decorator.getJsonNode();

        assertTrue(result.has("a"));
        assertFalse(result.has("b"));
        assertTrue(result.has("c"));
    }

    @Test
    void testIncludeNonExistentField() throws JsonProcessingException {
        String json = "{\"exist\":1}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(baseComponent, Collections.singletonList("missing"), false);
        JsonNode result = decorator.getJsonNode();

        assertFalse(result.has("exist"));
        assertFalse(result.has("missing"));
    }


    @Test
    void testExcludeSimpleField() throws JsonProcessingException {
        String json = "{\"keep\":\"yes\", \"remove\":\"no\"}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(baseComponent, Collections.singletonList("remove"), true);
        JsonNode result = decorator.getJsonNode();

        assertTrue(result.has("keep"));
        assertFalse(result.has("remove"));
    }

    @Test
    void testExcludeNestedField() throws JsonProcessingException {
        String json = "{\"root\": {\"safe\":\"ok\", \"secret\":\"hide\"}}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(baseComponent, Collections.singletonList("secret"), true);
        JsonNode result = decorator.getJsonNode();

        assertTrue(result.get("root").has("safe"));
        assertFalse(result.get("root").has("secret"));
    }

    @Test
    void testExcludeFieldInArray() throws JsonProcessingException {
        String json = "[{\"visible\":1, \"hidden\":0}, {\"visible\":2, \"hidden\":0}]";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(baseComponent, Collections.singletonList("hidden"), true);
        JsonNode result = decorator.getJsonNode();

        assertTrue(result.get(0).has("visible"));
        assertFalse(result.get(0).has("hidden"));
    }

    @Test
    void testExcludeEmptyList() throws JsonProcessingException {
        String json = "{\"a\":1, \"b\":2}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(baseComponent, Collections.emptyList(), true);
        JsonNode result = decorator.getJsonNode();

        assertEquals(2, result.size());
        assertTrue(result.has("a"));
        assertTrue(result.has("b"));
    }

    @Test
    void testExcludeWholeObject() throws JsonProcessingException {
        String json = "{\"meta\": {\"ver\":1}, \"data\": \"x\"}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(baseComponent, Collections.singletonList("meta"), true);
        JsonNode result = decorator.getJsonNode();

        assertFalse(result.has("meta"));
        assertTrue(result.has("data"));
    }

    @Test
    void testOriginalNodeNotModified() throws JsonProcessingException {
        String json = "{\"removeMe\":1}";
        JsonProcessorComponent baseComponent = new BaseJsonComponent(json);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(baseComponent, Collections.singletonList("removeMe"), true);
        JsonNode result = decorator.getJsonNode();
        JsonNode original = baseComponent.getJsonNode();

        assertFalse(result.has("removeMe"));
        assertTrue(original.has("removeMe"));
        assertNotSame(original, result);
    }
}