package pl.put.poznan.JSONtools.app.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.JSONtools.logic.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST Controller handling JSON transformation, formatting, filtering,
 * and text comparison operations. Uses the Decorator design pattern to apply
 * processing steps dynamically.
 */

@RestController
@RequestMapping("/json")
public class JSONController {
    private static final Logger logger = LoggerFactory.getLogger(JSONController.class);
    /**
     * Minifies (removes whitespace from) the provided JSON string.
     *
     * @param inputJson The string containing the JSON to be minified.
     * @return HTTP 200 response with the minified JSON or 400 in case of an invalid format.
     */
    @PostMapping("/minify")
    public ResponseEntity<String> minifyJson(@RequestBody String inputJson) {
        logger.info("Received POST request to minify Json Structure");
        try {
            JsonProcessorComponent base = new BaseJsonComponent(inputJson);
            JsonProcessorComponent minified = new MinifyDecorator(base);
            String output=minified.getProcessedJson();
            logger.debug("Json structure minified");
            return ResponseEntity.ok(output);

        } catch (JsonProcessingException e) {
            logger.debug("Invalid JSON file format: {}",inputJson);
            return ResponseEntity.badRequest().body("Invalid JSON format!");
        }
    }


    /**
     * Pretty-prints the provided JSON string, converting minified JSON
     * into a human-readable formatted form.
     *
     * @param inputJson The string containing the JSON to be formatted.
     * @return HTTP 200 response with the formatted JSON or 400 in case of an invalid format.
     */
    @PostMapping("/pretty")
    public ResponseEntity<String> prettyJson(@RequestBody String inputJson){
        logger.info("Received POST request to pretty print Json Structure");
        try {
            JsonProcessorComponent base = new BaseJsonComponent(inputJson);
            String output =base.getProcessedJson();
            logger.debug("Json structure formatted");
            return ResponseEntity.ok(output);

        } catch (JsonProcessingException e) {
            logger.debug("Invalid JSON file format: {}",inputJson);
            return ResponseEntity.badRequest().body("Invalid JSON format!");
        }
    }


    /**
     * Filters the JSON using a whitelist — only the given fields remain.
     *
     * @param inputJson The string containing the JSON to be filtered.
     * @param fields A list of field names (keys) to keep in the result.
     * @return HTTP 200 with the filtered JSON or 400 if input is invalid.
     */
    @PostMapping("/filter/whitelist")
    public ResponseEntity<String> filterWhitelist(
            @RequestBody String inputJson,
            @RequestParam List<String> fields
    ) {
        logger.info("Received POST request to filter (whitelist) Json Structure");
        try {
            JsonProcessorComponent base = new BaseJsonComponent(inputJson);
            JsonProcessorComponent filtered = new FilterColumnsDecorator(base, fields, false);
            String output =filtered.getProcessedJson();
            logger.debug("Json structure filtered (whitelist)");
            return ResponseEntity.ok(output);

        } catch (JsonProcessingException e) {
            logger.debug("Invalid JSON file format: {}",inputJson);
            return ResponseEntity.badRequest().body("Invalid JSON format!");
        }
    }


    /**
     * Filters the JSON using a blacklist — all given fields are removed.
     *
     * @param inputJson The JSON content to process.
     * @param fields A list of field names (keys) to remove from the result.
     * @return HTTP 200 with the filtered JSON or 400 in case of invalid input.
     */
    @PostMapping("/filter/blacklist")
    public ResponseEntity<String> filterBlacklist(
            @RequestBody String inputJson,
            @RequestParam List<String> fields
    ) {
        logger.info("Received POST request to filter (blacklist) Json Structure");
        try {
            JsonProcessorComponent base = new BaseJsonComponent(inputJson);
            JsonProcessorComponent filtered = new FilterColumnsDecorator(base, fields, true);
            String output =filtered.getProcessedJson();
            logger.debug("Json structure filtered (blacklist)");
            return ResponseEntity.ok(output);

        } catch (JsonProcessingException e) {
            logger.debug("Invalid JSON file format: {}",inputJson);
            return ResponseEntity.badRequest().body("Invalid JSON format!");
        }
    }


    /**
     * Compares two text values (provided inside a JSON object as fields "fileA" and "fileB")
     * and returns a JSON report listing all differing lines.
     *
     * @param inputJson JSON containing the text fields "fileA" and "fileB".
     * @return HTTP 200 with the diff result, or 400 if input is invalid.
     */
    @PostMapping("/diff")
    public ResponseEntity<String> diffJson(@RequestBody String inputJson) {
        logger.info("Received POST request to compare files");
        try {
            JsonProcessorComponent base = new BaseJsonComponent(inputJson);
            JsonProcessorComponent diff = new TextLineDiffDecorator(base);
            String output = diff.getProcessedJson();
            logger.debug("Text comparison completed");
            return ResponseEntity.ok(output);

        } catch (IllegalArgumentException e) {
            logger.debug("Invalid arguments for comparison: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JsonProcessingException e) {
            logger.debug("Invalid JSON file format: {}",inputJson);
            return ResponseEntity.badRequest().body("Invalid JSON format!");
        }
    }




}

