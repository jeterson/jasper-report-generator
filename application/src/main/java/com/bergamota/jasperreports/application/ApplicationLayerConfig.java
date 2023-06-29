package com.bergamota.jasperreports.application;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.bergamota.jasperreports.application"})
/*@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition(security = @SecurityRequirement(name = "Bearer Authentication"))
 */
public class ApplicationLayerConfig {
}
