package com.test.userapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableOpenApi
public class UserApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI(
            @Value("${application-description}") String appDesciption,
            @Value("${application-version}") String appVersion) {
        return new OpenAPI().info(
                new Info().title("User API")
                        .version(appVersion).description(appDesciption)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("MIT licensed.")
                                .url("http://springdoc.org")));

    }
}
