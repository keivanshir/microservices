package com.transaction.microservice.documentation;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {


    @Value("${app.auth.type}")
    private String authType;

    @Bean
    public OpenAPI transactionManagementSystemOpenAPI(){

        Components components = new Components();
        SecurityRequirement securityRequirement = new SecurityRequirement();

        if ("basic".equalsIgnoreCase(authType)) {
            // Define Basic Auth security scheme
            components.addSecuritySchemes("BasicAuth", new io.swagger.v3.oas.models.security.SecurityScheme()
                    .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                    .scheme("basic"));
            securityRequirement.addList("BasicAuth");
        } else if ("jwt".equalsIgnoreCase(authType)) {
            // Define Bearer Token (JWT) security scheme
            components.addSecuritySchemes("BearerAuth", new io.swagger.v3.oas.models.security.SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT"));
            securityRequirement.addList("BearerAuth");
        }

        return new OpenAPI()
                .components(components)
                .addSecurityItem(securityRequirement)
                .info(new Info()
                        .title("Transaction management APIs")
                        .contact(new Contact().name("Keivan Shirkoubian")
                                .email("keivan.shir.74@gmail.com")
                                .url("https://github.com/keivanshir/mohaymen_task"))
                        .description("This is transaction microservice API documentation")
                        .version("1.0.0"));
    }
}
