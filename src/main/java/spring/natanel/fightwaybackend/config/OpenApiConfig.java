package spring.natanel.fightwaybackend.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "FightWay Project",
                description = "Manage my Products Store",
                version = "1.0",
                contact = @Contact(
                        name = "Nate",
                        url = "https://springdoc.org/#Introduction",
                        email = "natanell777@gmail.com"
                ),
                license = @License( //Legal license
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "https://uc89b07cb06ee64da867c91bcd0a.dl.dropboxusercontent.com/cd/0/inline2/CIn2_RafBQS0ZEs2_J416DxvUeX46AlJZFFpgh614nCQaj-tps8mAp1bFjK57_18ZKm0twsnBCPunarR5CJ3Gw7iHyMWFPsMBDG0BNL74KfmFRnb-1STVozZ4LabgRNXMBh8WWxZGDwyuQTkL94UwGxV7SDL7744VwcQTWUdqLBA2rOmnQoYFbwgPzBBIS3zFTw8_ISneYItSIawkte7RS7v5i-6yvQo2tw-GkxClUoWq7DUfAnscFs8fAFmwFOPSzDpKAPBjsIUwxyhZhBqPN-scyWfzFABxRr_gis_TkNNeYZXzTyg2GcyASelNkzc2acGNDA7asy92paFI-IZb4KosWrm7bNx1hx8xK6hxfE1AMNnHh5A5n6eoNW_eYME7cs/file"
        )
)


@SecuritySchemes(
        @SecurityScheme(
                name = "Bearer Authentication",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "JWT",
                scheme = "Bearer"
        )
)
public class OpenApiConfig {
}
