package pl.put.poznan.JSONtools.logic;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This class is used to minify given Json and return it as String (Concrete Decorator)
 */
public class MinifyDecorator extends JsonDecorator {

    private final ObjectMapper mapper = new ObjectMapper();
    /**
     * Construct using constructor from superclass
     */
    public MinifyDecorator(JsonProcessorComponent decoratedComponent) {
        super(decoratedComponent);
    }
    /**
     * Minify the structure by removing whitespaces and @return string
     */
    @Override
    public String getProcessedJson() throws JsonProcessingException {
        return mapper.writeValueAsString(decoratedComponent.getJsonNode());
    }
}