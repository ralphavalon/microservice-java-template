package com.example.template.client.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.template.client.ProviderClient;
import com.example.template.model.Template;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TemplateClientServiceUnitTest {
	
	@MockBean
	private RestTemplate restTemplate;
	
	@Autowired
	private ProviderClient providerClient;
	
	@SuppressWarnings("unchecked")
	private ArgumentCaptor<HttpEntity<Template>> httpEntity = ArgumentCaptor.forClass(HttpEntity.class);
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldCallProvider() {
		Template template = new Template();
		providerClient.callProvider(template);
		
		verify(restTemplate, times(1)).postForObject(anyString(), httpEntity.capture(), any(Class.class));
		
		assertThat(template.equals(httpEntity.getValue().getBody()));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldRetryWhenError() {
		doThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR))
		.doReturn("{}")
			.when(restTemplate).postForObject(anyString(), any(HttpEntity.class), any(Class.class));
		
		Template template = new Template();
		providerClient.callProvider(template);
		
		verify(restTemplate, times(2)).postForObject(anyString(), httpEntity.capture(), any(Class.class));
		
		assertThat(template.equals(httpEntity.getValue().getBody()));
	}

}
