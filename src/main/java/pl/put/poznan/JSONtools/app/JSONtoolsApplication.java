package pl.put.poznan.JSONtools.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.put.poznan.JSONtools.logic.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;


@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer.rest"})
public class JSONtoolsApplication {

    public static void main(String[] args) throws IOException {
        String filePathString ="deminified.json";
        Path path = Paths.get(filePathString);
        String content;
        try {
            content = Files.readString(path);
        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu pliku: " + e.getMessage());
            return;
        }
        List<String> fieldsToKeep = Arrays.asList("volume","height");
        JsonProcessorComponent component = new BaseJsonComponent(content);
        component=new FilterColumnsDecorator(component,fieldsToKeep,true);
        String minifiedContent = component.getProcessedJson();
        Path outputPath  =Paths.get("filter.json");
        Files.writeString(outputPath,minifiedContent);
        SpringApplication.run(JSONtoolsApplication.class, args);
    }
}
