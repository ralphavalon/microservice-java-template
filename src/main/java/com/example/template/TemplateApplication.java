package com.example.template;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableRetry
@EnableSpringDataWebSupport
public class TemplateApplication {

	public static void main(String[] args) {
		final SpringApplication application = new SpringApplication(TemplateApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
}
