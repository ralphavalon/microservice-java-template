package com.example.template.config.swagger;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.template.TemplateApplication;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import({BeanValidatorPluginsConfiguration.class})
public class SwaggerConfig implements WebMvcConfigurer {
	
    @Bean
    public Docket apiDocket() {                
        return new Docket(DocumentationType.SWAGGER_2)          
          .select()
          .apis(RequestHandlerSelectors.basePackage(TemplateApplication.class.getPackageName()))
          .paths(PathSelectors.any())
          .build()
          .tags(new Tag("templates", "Templates are the template object."))
          .useDefaultResponseMessages(false)
          .apiInfo(metaData());
    }
    
    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
        	.addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
        	.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
    
    private ApiInfo metaData() {
    	  return new ApiInfo(
    	            "TITLE",
    	            "DESCRIPTION",
    	            "VERSION",
    	            "TERMS OF SERVICE URL",
    	            new Contact("NAME","URL","EMAIL"),
    	            "LICENSE",
    	            "LICENSE URL",
    	            Collections.emptyList()
    	    );
    }
    
}
