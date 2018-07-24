package com.example.template.client.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.template.client.ProviderClient;
import com.example.template.model.Template;
import com.github.rozidan.springboot.logger.Loggable;

@Service
@Loggable(ignore=Exception.class)
public class TemplateClientService implements ProviderClient {
	
	@Value("${provider.url}")
	private String url;
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	@Retryable(maxAttempts = 2)
	public String callProvider(Template template) {
		HttpEntity<Template> entity = new HttpEntity<Template>(template, getRequestHeaders());
		return restTemplate.postForObject(url, entity, String.class);
	}

	private HttpHeaders getRequestHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
		return headers;
	}
	

}
