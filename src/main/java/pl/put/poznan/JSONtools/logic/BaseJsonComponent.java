package pl.put.poznan.JSONtools.logic;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;

/**
 * Basic Json class (concrete component)
 */
public class BaseJsonComponent implements JsonProcessorComponent {

    private final JsonNode rootNode;
    private final ObjectMapper mapper;
    /**
     * Constructor reads @param jsonString and maps it into instance JsonNode class from Jackson
     */
    public BaseJsonComponent(String jsonString) throws JsonProcessingException {
        this.mapper=new ObjectMapper();
        this.mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        this.rootNode = mapper.readTree(jsonString);
    }
    /**
     * @return formatted (deminified) Json structure as string
     */
    @Override
    public String getProcessedJson() throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
    }
    /**
     * @return Json structure for decorators' purposes
     */
    @Override
    public JsonNode getJsonNode() {
        return rootNode;
    }
}