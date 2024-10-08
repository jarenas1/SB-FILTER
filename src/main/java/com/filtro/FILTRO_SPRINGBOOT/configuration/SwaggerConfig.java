package com.filtro.FILTRO_SPRINGBOOT.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info = @Info(
                title = "GESTION DE PALETS Y ENVIOS",
                description = "En este swagger podras ver adkjjnfdjsdfdssd",
                termsOfService = "url de términos y condiciones",
                version = "1.0.0", //versión
                contact = @Contact( //información de creador
                        name = "Juan Arenas",
                        url = "pagina de contacto",
                        email = "juanjoarenas1218@gmail.com"
                )
        ),
        servers = { //configuramos los servers
                @Server(
                        description = "Nombre server",
                        url = "http://localhost:8050"
                ),
                @Server(
                        description = "Server product",
                        url = "https:aksaks.com"
                )
        },
        security = @SecurityRequirement(
                name = "seguridadDeJuan"
        )
)
@SecurityScheme(
        name = "seguridadDeJuan",
        description="descriptiiioooon",
        type = SecuritySchemeType.HTTP, //HTTP se trabaja con token
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER, //donde va el token,
        scheme = "bearer",
        bearerFormat = "JWT"
)

public class SwaggerConfig {
}
