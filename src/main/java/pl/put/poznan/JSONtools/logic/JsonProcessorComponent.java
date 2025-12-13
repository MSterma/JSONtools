package pl.put.poznan.JSONtools.logic;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * This interface defines methods for all further components (Component)
 */
public interface JsonProcessorComponent {

    /**
     * Transforms serialized Json into string
     * @return processed Json structure as string
     * @throws JsonProcessingException if input Json structure is invalid
     */
    String getProcessedJson() throws JsonProcessingException;

    /**
     * @return rootNode of type JsonNode which contains Json structure serialized into object via Jackson
     * @throws JsonProcessingException if input Json structure is invalid
     */
    JsonNode getJsonNode() throws JsonProcessingException;
}