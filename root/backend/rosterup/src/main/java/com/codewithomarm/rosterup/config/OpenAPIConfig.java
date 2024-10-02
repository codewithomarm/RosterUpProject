package com.codewithomarm.rosterup.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI(){
        Contact contact = new Contact()
                .email("codewithomarm@gmail.com")
                .name("Omar Montoya")
                .url("https://github.com/codewithomarm");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("RosterUp API")
                .version("1.0")
                .contact(contact)
                .description("REST API documentation for RosterUp Application.")
                .termsOfService("https://github.com/codewithomarm")
                .license(license);

        return new OpenAPI().info(info);
    }
}
