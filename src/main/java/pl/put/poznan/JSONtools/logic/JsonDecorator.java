package pl.put.poznan.JSONtools.logic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Abstract class Json is blueprint for all further decorators (Decorator)
 */
public abstract class JsonDecorator implements JsonProcessorComponent {
    /**
     * Object that implements JsonProcessorComponent such as BaseJsonComponent or any concrete decorator object
     */
    protected JsonProcessorComponent decoratedComponent;
    /**
     *  Constructor initializes decoratedComponent
     *  @param decoratedComponent Object that implements JsonProcessorComponent such as BaseJsonComponent or any concrete decorator object
     */
    public JsonDecorator(JsonProcessorComponent decoratedComponent) {
        this.decoratedComponent = decoratedComponent;
    }
    /**
     * Transforms serialized Json into string
     * @return formatted Json structure as string
     * @throws JsonProcessingException if input Json structure is invalid
     */
    @Override
    public String getProcessedJson() throws JsonProcessingException {
        return decoratedComponent.getProcessedJson();
    }
    /**
     * @return rootNode of type JsonNode which contains Json structure serialized into object via Jackson
     * @throws JsonProcessingException if input Json structure is invalid
     */
    @Override
    public JsonNode getJsonNode() throws JsonProcessingException {
        return decoratedComponent.getJsonNode();
    }
}