package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Permite solicitudes CORS a todas las rutas
                registry.addMapping("/**")  // Aplica la configuración a todas las rutas
                        .allowedOrigins("http://localhost:3000")  // Asegúrate de que esta es la URL donde corre tu React
                        .allowedMethods("GET", "POST", "PUT", "DELETE")  // Métodos HTTP permitidos
                        .allowedHeaders("*");  // Permite todos los encabezados
            }
        };
    }
}

