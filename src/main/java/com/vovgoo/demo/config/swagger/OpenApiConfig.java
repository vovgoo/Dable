package com.vovgoo.demo.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Dable API")
                        .version("0.0.1")
                        .description("Documentation of Dable API")
                        .contact(new Contact()
                                .name("vovgoo dev.")
                                .url("https://github.com/vovgoo/Dable")
                        )
                )
                .components(new Components()
                        .addSecuritySchemes(OpenApiConstants.SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(OpenApiConstants.SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                );
    }
}
