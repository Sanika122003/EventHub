package com.eventhub.eventservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI eventHubOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("EventHub Event Service API")
                        .description("REST APIs for managing events in the EventHub platform.")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Sanika Shinde")
                                .email("sanika2@gmail.com")
                        ));
    }
}
