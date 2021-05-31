package com.project.bootcamp;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BootcampApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootcampApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${application.description}") String description,
                                 @Value("${application.version}") String version,
                                 @Value("${application.urlTermsOfService}") String urlTermsOfService,
                                 @Value("${application.licenseName}") String licenseName,
                                 @Value("${application.urlLicense}") String urlLicense) {
        return new OpenAPI()
                .info(new Info()
                        .title(description)
                        .version(version)
                        .termsOfService(urlTermsOfService)
                        .license(new License().name(licenseName).url(urlLicense)));
    }
}
