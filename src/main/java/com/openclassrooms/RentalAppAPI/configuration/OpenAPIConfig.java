package com.openclassrooms.RentalAppAPI.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPIConfig 
{
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() 
    {
        return new OpenAPI()
                .info(new Info().title("Rental App API")
                                 .description("Rental App API description")
                                 .version("1.0"))
                                 .addSecurityItem(new SecurityRequirement()
                                 .addList("bearerAuth"))
                                 .components(
                                    new Components()
                                    .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")));
    }
}
