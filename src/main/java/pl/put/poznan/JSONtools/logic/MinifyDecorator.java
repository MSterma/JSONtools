package pl.put.poznan.JSONtools.logic;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This class is used to minify given Json and return it as String (Concrete decorator)
 */
public class MinifyDecorator extends JsonDecorator {
    /**
     * Jackson object used to convert serialized Json to string containing Json without white spaces
     */
    private final ObjectMapper mapper = new ObjectMapper();
    /**
     * Initialize using constructor from superclass
     *  @param decoratedComponent Object that implements JsonProcessorComponent such as BaseJsonComponent or any concrete decorator object
     */
    public MinifyDecorator(JsonProcessorComponent decoratedComponent) {
        super(decoratedComponent);
    }
    /**
     * Minify the structure by removing whitespaces and return string with json structure
     * @throws JsonProcessingException if input Json structure is invalid
     */
    @Override
    public String getProcessedJson() throws JsonProcessingException {
        return mapper.writeValueAsString(decoratedComponent.getJsonNode());
    }
}