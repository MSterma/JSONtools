package pl.put.poznan.JSONtools.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.put.poznan.JSONtools.logic.BaseJsonComponent;
import pl.put.poznan.JSONtools.logic.FilterColumnsDecorator;
import pl.put.poznan.JSONtools.logic.JsonProcessorComponent;
import pl.put.poznan.JSONtools.logic.MinifyDecorator;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer.rest"})
public class JSONtoolsApplication {

    public static void main(String[] args) throws IOException {
        String filePathString ="minified.json";
        Path path = Paths.get(filePathString);
        String content;
        try {
            content = Files.readString(path);
        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu pliku: " + e.getMessage());
            return;
        }
        List<String> fieldsToKeep = Arrays.asList("volume", "height", "name","children","id");
        JsonProcessorComponent component = new BaseJsonComponent(content);
        component=new FilterColumnsDecorator(component,fieldsToKeep);
        String minifiedContent = component.getProcessedJson();
        Path outputPath  =Paths.get("filtered.json");
        Files.writeString(outputPath,minifiedContent);
        SpringApplication.run(JSONtoolsApplication.class, args);
    }
}
