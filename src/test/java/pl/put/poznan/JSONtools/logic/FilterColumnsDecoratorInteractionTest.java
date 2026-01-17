package pl.put.poznan.JSONtools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilterColumnsDecoratorInteractionTest {

    @Mock
    private JsonProcessorComponent mockComponent;

    @Mock
    private ObjectNode mockOriginalNode;

    @Mock
    private ObjectNode mockCopiedNode;

    @Test
    void verifyComponentInteraction() throws JsonProcessingException {
        when(mockComponent.getJsonNode()).thenReturn(mockOriginalNode);
        when(mockOriginalNode.deepCopy()).thenReturn(mockCopiedNode);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(mockComponent, Collections.emptyList());
        decorator.getJsonNode();

        verify(mockComponent, times(1)).getJsonNode();
    }

    @Test
    void verifyDeepCopyCalled() throws JsonProcessingException {
        when(mockComponent.getJsonNode()).thenReturn(mockOriginalNode);
        when(mockOriginalNode.deepCopy()).thenReturn(mockCopiedNode);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(mockComponent, Collections.emptyList());
        decorator.getJsonNode();

        verify(mockOriginalNode, times(1)).deepCopy();
    }

    @Test
    void verifyIsObjectCheck() throws JsonProcessingException {
        when(mockComponent.getJsonNode()).thenReturn(mockOriginalNode);
        when(mockOriginalNode.deepCopy()).thenReturn(mockCopiedNode);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(mockComponent, Collections.emptyList());
        decorator.getJsonNode();

        verify(mockCopiedNode, atLeastOnce()).isObject();
    }

    @Test
    void verifyIsArrayCheck() throws JsonProcessingException {
        when(mockComponent.getJsonNode()).thenReturn(mockOriginalNode);
        when(mockOriginalNode.deepCopy()).thenReturn(mockCopiedNode);
        when(mockCopiedNode.isObject()).thenReturn(false);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(mockComponent, Collections.emptyList());
        decorator.getJsonNode();

        verify(mockCopiedNode, times(1)).isArray();
    }

    @Test
    void verifyFieldNamesCalled() throws JsonProcessingException {
        when(mockComponent.getJsonNode()).thenReturn(mockOriginalNode);
        when(mockOriginalNode.deepCopy()).thenReturn(mockCopiedNode);
        when(mockCopiedNode.isObject()).thenReturn(true);
        when(mockCopiedNode.fieldNames()).thenReturn(Collections.emptyIterator());

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(mockComponent, Collections.singletonList("target"));
        decorator.getJsonNode();

        verify(mockCopiedNode, times(1)).fieldNames();
    }

    @Test
    void verifyRemoveCalledInWhitelistMode() throws JsonProcessingException {
        when(mockComponent.getJsonNode()).thenReturn(mockOriginalNode);
        when(mockOriginalNode.deepCopy()).thenReturn(mockCopiedNode);
        when(mockCopiedNode.isObject()).thenReturn(true);

        Iterator<String> fields = Collections.singletonList("unwanted").iterator();
        when(mockCopiedNode.fieldNames()).thenReturn(fields);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(mockComponent, Collections.singletonList("wanted"), false);
        decorator.getJsonNode();

        verify(mockCopiedNode, times(1)).remove("unwanted");
    }

    @Test
    void verifyGetCalledForRecursionInWhitelist() throws JsonProcessingException {
        when(mockComponent.getJsonNode()).thenReturn(mockOriginalNode);
        when(mockOriginalNode.deepCopy()).thenReturn(mockCopiedNode);
        when(mockCopiedNode.isObject()).thenReturn(true);

        Iterator<String> fields = Collections.singletonList("target").iterator();
        when(mockCopiedNode.fieldNames()).thenReturn(fields);

        JsonNode mockChild = mock(JsonNode.class);
        when(mockCopiedNode.get("target")).thenReturn(mockChild);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(mockComponent, Collections.singletonList("target"), false);
        decorator.getJsonNode();

        verify(mockCopiedNode, times(1)).get("target");
    }

    @Test
    void verifyRemoveCalledInBlacklistMode() throws JsonProcessingException {
        when(mockComponent.getJsonNode()).thenReturn(mockOriginalNode);
        when(mockOriginalNode.deepCopy()).thenReturn(mockCopiedNode);
        when(mockCopiedNode.isObject()).thenReturn(true);

        Iterator<String> fields = Collections.singletonList("badField").iterator();
        when(mockCopiedNode.fieldNames()).thenReturn(fields);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(mockComponent, Collections.singletonList("badField"), true);
        decorator.getJsonNode();

        verify(mockCopiedNode, times(1)).remove("badField");
    }

    @Test
    void verifyGetCalledForRecursionInBlacklist() throws JsonProcessingException {
        when(mockComponent.getJsonNode()).thenReturn(mockOriginalNode);
        when(mockOriginalNode.deepCopy()).thenReturn(mockCopiedNode);
        when(mockCopiedNode.isObject()).thenReturn(true);

        Iterator<String> fields = Collections.singletonList("goodField").iterator();
        when(mockCopiedNode.fieldNames()).thenReturn(fields);

        JsonNode mockChild = mock(JsonNode.class);
        when(mockCopiedNode.get("goodField")).thenReturn(mockChild);

        FilterColumnsDecorator decorator = new FilterColumnsDecorator(mockComponent, Collections.singletonList("badField"), true);
        decorator.getJsonNode();

        verify(mockCopiedNode, times(1)).get("goodField");
        verify(mockCopiedNode, never()).remove("goodField");
    }
}