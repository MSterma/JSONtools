package pl.put.poznan.JSONtools.app;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    //http://localhost:8080/swagger-ui/index.html#/
    @Bean
    public OpenAPI jsonToolsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("JSON Tools API")
                        .description("Proste GUI do minifikacji, formatowania i porównywania JSON")
                        .version("1.0.0"));
    }
}