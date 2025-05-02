package org.ademun.timetableapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI().info(new Info().title("Timetable API").version("1.0")
        .description("API для управления расписанием в университете")
        .contact(new Contact().name("Author").email("vaadim011@gmail.com")).license(
            new License().name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
  }
}
