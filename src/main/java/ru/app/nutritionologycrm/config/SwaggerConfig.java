package ru.app.nutritionologycrm.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Task Management System",
                version = "1.0",
                description = "CRM-система для нутрициологов",
                contact = @Contact(
                        name = "Daniil Yagodkin",
                        email = "agodkidaniil@gmail.com",
                        url = "https://t.me/cfogoogle"
                )
        )
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {
}
