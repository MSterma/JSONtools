package pl.put.poznan.JSONtools.logic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This interface defines methods for all further components (Component)
 */
public interface JsonProcessorComponent {

    /**
     * @return processed Json structure as string
     */
    String getProcessedJson() throws JsonProcessingException;

    /**
     * @return Json structure for decorators' purposes
     */
    JsonNode getJsonNode() throws JsonProcessingException;
}