package pl.put.poznan.JSONtools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TextLineDiffDecoratorInteractionTest {

    @Mock
    private JsonProcessorComponent mockComponent;

    @Mock
    private JsonNode mockNode;

    @Mock
    private JsonNode mockTextNode;

    @Test
    void verifyComponentInteraction() throws JsonProcessingException {
        when(mockComponent.getJsonNode()).thenReturn(mockNode);
        when(mockNode.has(anyString())).thenReturn(true);
        when(mockNode.get(anyString())).thenReturn(mockTextNode);
        when(mockTextNode.asText()).thenReturn("");

        TextLineDiffDecorator decorator = new TextLineDiffDecorator(mockComponent);
        decorator.getJsonNode();

        verify(mockComponent, times(1)).getJsonNode();
    }

    @Test
    void verifyCheckForFileA() throws JsonProcessingException {
        when(mockComponent.getJsonNode()).thenReturn(mockNode);

        when(mockNode.has("fileA")).thenReturn(false);

        TextLineDiffDecorator decorator = new TextLineDiffDecorator(mockComponent);

        try {
            decorator.getJsonNode();
        } catch (IllegalArgumentException ignored) {
        }

        verify(mockNode).has("fileA");
    }

    @Test
    void verifyCheckForFileB() throws JsonProcessingException {
        when(mockComponent.getJsonNode()).thenReturn(mockNode);
        when(mockNode.has("fileA")).thenReturn(true);
        when(mockNode.has("fileB")).thenReturn(false); // Tu wyrzuci błąd

        TextLineDiffDecorator decorator = new TextLineDiffDecorator(mockComponent);

        try {
            decorator.getJsonNode();
        } catch (IllegalArgumentException ignored) {
        }

        verify(mockNode).has("fileB");
    }

    @Test
    void verifyGetContentCalls() throws JsonProcessingException {
        when(mockComponent.getJsonNode()).thenReturn(mockNode);
        when(mockNode.has("fileA")).thenReturn(true);
        when(mockNode.has("fileB")).thenReturn(true);

        when(mockNode.get("fileA")).thenReturn(mockTextNode);
        when(mockNode.get("fileB")).thenReturn(mockTextNode);
        when(mockTextNode.asText()).thenReturn("dummy content");

        TextLineDiffDecorator decorator = new TextLineDiffDecorator(mockComponent);
        decorator.getJsonNode();

        verify(mockNode, times(1)).get("fileA");
        verify(mockNode, times(1)).get("fileB");
    }
}