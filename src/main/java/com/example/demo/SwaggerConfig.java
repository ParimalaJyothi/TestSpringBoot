package com.example.demo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
					.directModelSubstitute(Calendar.class, Date.class)
					.groupName("api")
					.select()
					.apis(RequestHandlerSelectors.any())
					.paths(PathSelectors.ant("/**"))
					.build()
					.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		Contact contact = new Contact("John Doe", "contact website", "email@email.com");

		ArrayList<VendorExtension> list = new ArrayList<>();
		list.add(new StringVendorExtension("", ""));
		return new ApiInfo("REST API Documentation", "REST API", "v1", "Terms of Service",
				contact, "Test license", "/licenseUrl", list);
	}
}