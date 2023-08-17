package com.example.shop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Value("${version}")
    private static String version;
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        Info info = new Info()
                .title("Shop API")
                .description("This document describes Shop API.")
                .version(version);
        Server server = new Server().description("Dev server");
        return new OpenAPI().info(info).servers(List.of(server));
    }
}