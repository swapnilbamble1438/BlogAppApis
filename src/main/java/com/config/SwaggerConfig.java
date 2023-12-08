package com.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	public static final String Authorization_Header = "Authorization";

	private ApiKey  apiKeys()
	{
		return new ApiKey("JWT",Authorization_Header,"header");
		
	}
	
	private List<SecurityContext> securityContexts()
	{
		return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
	}
	
	private List<SecurityReference> sf()
	{
		AuthorizationScope scope = new AuthorizationScope("global","accessEverything");
		
		return Arrays.asList(new SecurityReference("JWT",new AuthorizationScope[]{scope}));
	}
	
	@Bean
	public Docket api()
	{
		

		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis(RequestHandlerSelectors
				.any())
				.paths(PathSelectors.any()).build();
	}
	
	private ApiInfo getInfo()
	{
		return new ApiInfo("Blog App Appi: Backend Course",
				"This Project is developed by Swapnil Bamble",
				"1.0", "Terms of Service", 
				new Contact("Swapnil","http://localhost:8080/swagger-ui/index.html",
						"swapnil@gmail.com"),
						"License of APIs", "API License URL",
						Collections.emptyList());
	}

}
