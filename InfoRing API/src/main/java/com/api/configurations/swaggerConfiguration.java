package com.api.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class swaggerConfiguration {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiKey apiKeys() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private List<SecurityContext> securityContexts() {
        return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
    }

    private List<SecurityReference> sf() {

        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");

        return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] { scope }));
    }

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).securityContexts(securityContexts())
                .securitySchemes(List.of(apiKeys())).select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo getInfo() {

        return new ApiInfo("InfoRing Application : Spring Boot (REST API)",
                "This API is developed by Rohit Parihar", "1.0", "Terms of Service",
                new Contact("Rohit", "", "rohitparih@gmail.com"),
                "License of APIS", "API license URL", Collections.emptyList());
    };

}
