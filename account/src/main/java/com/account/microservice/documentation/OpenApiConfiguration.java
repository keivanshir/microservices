package com.account.microservice.documentation;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class OpenApiConfiguration {

    @Value("${app.auth.type}")
    private String authType;

    @Bean
    public OpenAPI accountManagementOpenAPI(){

        Components components = new Components();
        SecurityRequirement securityRequirement = new SecurityRequirement();

        if ("basic".equalsIgnoreCase(authType)) {
            // Define Basic Auth security scheme
            components.addSecuritySchemes("BasicAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("basic"));
            securityRequirement.addList("BasicAuth");
        } else if ("jwt".equalsIgnoreCase(authType)) {
            // Define Bearer Token (JWT) security scheme
            components.addSecuritySchemes("BearerAuth", new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT"));
            securityRequirement.addList("BearerAuth");
        }

        return new OpenAPI()
                .components(components)
                .addSecurityItem(securityRequirement)
                .info(new Info()
                        .title("Account APIs")
                        .contact(new Contact().name("Keivan Shirkoubian")
                                .email("keivan.shir.74@gmail.com")
                                .url("https://github.com/keivanshir/mohaymen_task"))
                        .description("This is account microservice API documentation")
                        .version("1.0.0"));
    }
}
