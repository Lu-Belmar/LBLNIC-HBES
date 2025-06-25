package com.example.HardBoiledEgg.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EcoMarket SPA - HardBoiledEgg API")
                        .version("1.0.0")
                        .description("API para la gestión completa de EcoMarket, incluyendo Productos, Clientes, Ventas, Inventario y más. Desarrollado por H.B.E.S.")
                        .contact(new Contact()
                                .name("H.B.E.S. (Hard Boiled Egg Solutions)")
                                .email("contacto@hbes.cl")));
    }
}