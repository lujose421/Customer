package com.nttdata.customer.utils;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Project Banking - OpenAPI 3.0")
                        .description("This is the version of an Api created with MongoDB, reactive programming y functional programming for customer manteniment.\n"
                                + "\nSome useful links:\n" +
                                "\nThe repository in GitHub: https://github.com/lujose421/Customer")

                        .termsOfService("http://swagger.io/terms/")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                        .version("1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("Find out more about Swagger")
                        .url("http://swagger.io"))
                .servers(
                        Arrays.asList(
                                new Server().url("http://localhost:8080").description("Local server Swagger")
                        )
                );
    }
}
