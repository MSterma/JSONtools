package pl.put.poznan.JSONtools.logic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Abstract class Json is blueprint for all further decorators (Decorator)
 */
public abstract class JsonDecorator implements JsonProcessorComponent {

    protected JsonProcessorComponent decoratedComponent;
    /**
     *  Constructor initializes decoratedComponent
     */
    public JsonDecorator(JsonProcessorComponent decoratedComponent) {
        this.decoratedComponent = decoratedComponent;
    }
    /**
     * @return formatted Json structure as string
     */
    @Override
    public String getProcessedJson() throws JsonProcessingException {
        return decoratedComponent.getProcessedJson();
    }
    /**
     * @return Json structure for decorators' purposes
     */
    @Override
    public JsonNode getJsonNode() throws JsonProcessingException {
        return decoratedComponent.getJsonNode();
    }
}