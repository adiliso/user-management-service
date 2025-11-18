package com.adil.usermanagementservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        servers = {
                @Server(url = "/", description = "Production Server"),
                @Server(url = "http://localhost:8080", description = "Development Server")
        }
)
public class OpenApiConfig {
}