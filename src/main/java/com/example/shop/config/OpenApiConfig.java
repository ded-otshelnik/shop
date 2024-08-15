package com.example.shop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {
    @Value("${version}")
    private String version;

    @Bean
    public OpenAPI securedOpenAPI(){
        String schemeName = "bearerAuth";
        String bearerFormat = "JWT";
        String scheme = "bearer";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(schemeName))
                .components(new Components().addSecuritySchemes(
                        schemeName, new SecurityScheme()
                                .name(schemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat(bearerFormat)
                                .in(SecurityScheme.In.HEADER)
                                .scheme(scheme)
                ))
                .info(new Info()
                        .title("Shop REST Application")
                        .description("Shop REST Application API using Spring Boot")
                        .version(version)
                        .summary("This app implements shop as REST Server. " +
                                "App based on Spring Boot 3 and PostgreSQL database.")
                        .contact(new Contact()
                                .name("Budnikov Andrey"))
                )
                .servers(List.of(new Server()
                        .url("http://localhost:8080/")
                        .description("Shop server URL"))
                );
    }
}
