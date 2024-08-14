package com.example.shop.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Shop REST Application API",
                description = "Shop REST Application",
                version = "0.0.1-SNAPSHOT",
                contact = @Contact(
                        name = "Budnikov Andrey"
                )
        )
)
public class OpenApiConfig {
}
