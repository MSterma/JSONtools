package pl.put.poznan.JSONtools.logic;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basic Json class used to parse String into JsonNode object and return Deminified Json  (Concrete component)
 */
public class BaseJsonComponent implements JsonProcessorComponent {
    private static final Logger logger = LoggerFactory.getLogger(BaseJsonComponent.class);

    /**
     * Serialized Json structure represented as tree
     */
    private final JsonNode rootNode;
    /**
     * Jackson object used to serialize Json structure
     */
    private final ObjectMapper mapper;
    /**
     * Constructor reads jsonString of type String and maps it into instance JsonNode class from Jackson
     * @param jsonString String containing json structure
     * @throws JsonProcessingException if input Json structure is invalid
     */
    public BaseJsonComponent(String jsonString) throws JsonProcessingException {
        logger.info("BaseJsonComponent: Initializing BaseJsonComponent with input string");
        this.mapper=new ObjectMapper();
        this.mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        this.rootNode = mapper.readTree(jsonString);
        logger.debug("BaseJsonComponent: parsed JSON as tree successfully");
    }
    /**
     * Transforms serialized Json into string
     * @return formatted (deminified) Json structure as string
     * @throws JsonProcessingException if Json structure is invalid
     */
    @Override
    public String getProcessedJson() throws JsonProcessingException {
        logger.debug("BaseJsonComponent: Processing JSON to string (Deminified/formatted Print)");
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
    }
    /**
     * @return rootNode of type JsonNode which contains Json structure serialized into object via Jackson
     */
    @Override
    public JsonNode getJsonNode() {
        return rootNode;
    }
}