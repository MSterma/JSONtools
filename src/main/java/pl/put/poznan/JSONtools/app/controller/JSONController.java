package pl.put.poznan.JSONtools.app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.JSONtools.logic.*;
import java.util.List;


/**
 * REST Controller handling JSON transformation, formatting, filtering,
 * and text comparison operations. Uses the Decorator design pattern to apply
 * processing steps dynamically.
 */

@RestController
@RequestMapping("/json")
public class JSONController {

    /**
     * Minifies (removes whitespace from) the provided JSON string.
     *
     * @param inputJson The string containing the JSON to be minified.
     * @return HTTP 200 response with the minified JSON or 400 in case of an invalid format.
     */
    @PostMapping("/minify")
    public ResponseEntity<String> minifyJson(@RequestBody String inputJson) {
        try {
            JsonProcessorComponent base = new BaseJsonComponent(inputJson);
            JsonProcessorComponent minified = new MinifyDecorator(base);

            return ResponseEntity.ok(minified.getProcessedJson());

        } catch (JsonProcessingException e) {
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
        try {
            JsonProcessorComponent base = new BaseJsonComponent(inputJson);

            return ResponseEntity.ok(base.getProcessedJson());

        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("Invalid JSON format!");
        }
    }


    /**
     * Filters the JSON, keeping only the specified fields (columns).
     *
     * @param inputJson The string containing the JSON to be filtered.
     * @param fields A list of field names (keys) to be kept in the result.
     * @return HTTP 200 response with the filtered JSON or 400 in case of an invalid format.
     */
    @PostMapping("/filter")
    public ResponseEntity<String> filterJson(
            @RequestBody String inputJson,
            @RequestParam List<String> fields
    ) {
        try {
            JsonProcessorComponent base = new BaseJsonComponent(inputJson);

            JsonProcessorComponent filtered = new FilterColumnsDecorator(base, fields);

            return ResponseEntity.ok(filtered.getProcessedJson());

        } catch (JsonProcessingException e) {
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
        try {
            JsonProcessorComponent base = new BaseJsonComponent(inputJson);
            JsonProcessorComponent diff = new TextLineDiffDecorator(base);

            return ResponseEntity.ok(diff.getProcessedJson());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("Invalid JSON format!");
        }
    }




}

