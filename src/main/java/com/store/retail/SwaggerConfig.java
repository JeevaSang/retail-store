package com.store.retail;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.store.retail.controller")).build().apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
        return new ApiInfo("Retail Store REST API", "On a retail website, the following discounts apply:\n"
                + "1. If the user is an employee of the store, he gets a 30% discount\n"
                + "2. If the user is an affiliate of the store, he gets a 10% discount\n"
                + "3. If the user has been a customer for over 2 years, he gets a 5% discount.\n"
                + "4. For every $100 on the bill, there would be a $ 5 discount (e.g. for $ 990, you get $ 45 as a discount).\n"
                + "5. The percentage based discounts do not apply on groceries.\n"
                + "6. A user can get only one of the percentage based discounts on a bill.", "API TOS",
                "Terms of service", new Contact("Jeeva", "www.example.com", "jeevasang@gmail.com"), "License of API",
                "API license URL", Collections.emptyList());
    }
}
