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

    public static void main(String[] args) {
        SpringApplication.run(JSONtoolsApplication.class, args);
    }
}
