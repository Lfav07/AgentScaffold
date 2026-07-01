package com.lfav07.agentscaffold.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .servers(List.of(new Server().url("http://localhost:8080").description("Local development server")))
                .info(new Info()
                        .title("AgentScaffold Backend API")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0"))
                );
    }
}
