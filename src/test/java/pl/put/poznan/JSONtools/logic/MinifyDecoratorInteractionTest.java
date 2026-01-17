package pl.put.poznan.JSONtools.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MinifyDecoratorInteractionTest {

    @Mock
    private JsonProcessorComponent mockComponent;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void verifyGetJsonNodeCalledDuringProcessing() throws JsonProcessingException {
        MinifyDecorator decorator = new MinifyDecorator(mockComponent);
        JsonNode dummyNode = mapper.createObjectNode();

        when(mockComponent.getJsonNode()).thenReturn(dummyNode);

        decorator.getProcessedJson();

        verify(mockComponent, times(1)).getJsonNode();
    }

    @Test
    void verifyGetJsonNodeCalledDirectly() throws JsonProcessingException {
        MinifyDecorator decorator = new MinifyDecorator(mockComponent);

        decorator.getJsonNode();

        verify(mockComponent, times(1)).getJsonNode();
    }
}