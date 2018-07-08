package com.example.template.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;

import javax.ws.rs.NotFoundException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.template.helper.JsonHelper;
import com.example.template.model.Template;
import com.example.template.service.TemplateService;

@RunWith(SpringRunner.class)
@WebMvcTest(TemplateController.class)
@EnableSpringDataWebSupport
public class TemplateControllerUnitTest {
	
	@MockBean
	private TemplateService templateService;
	
	@Autowired
    private MockMvc mvc;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private Template template;
	
	@Before
	public void setUp() {
		template = new Template();
		template.setId("6e71d0d568e134c029203593b00a0103e7cdf30b");
		template.setField("field");
		template.setImmutableField("immutableField");
		template.setCreatedAt(LocalDateTime.now());
		template.setUpdatedAt(LocalDateTime.now());
		doReturn(template).when(templateService).save(any(Template.class));
		doReturn(new PageImpl<>(Arrays.asList(template))).when(templateService).getAll(any(PageRequest.class));
		doReturn(template).when(templateService).get(eq("6e71d0d568e134c029203593b00a0103e7cdf30b"));
		doThrow(new NotFoundException("unexisting_template")).when(templateService).get(eq("unexisting_template"));
	}

	@Test
	public void shouldCreateTemplate() throws Exception {
		String request = JsonHelper.getRequestFileAsString("template/create_template_success.json");
		String response = JsonHelper.getResponseFileAsString("template/create_template_success.json");

		mvc.perform(post("/templates").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(request)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isCreated())
				.andExpect(content().json(response));

		verify(templateService, times(1)).save(any(Template.class));
	}
	
	@Test
	public void shouldUpdateTemplate() throws Exception {
		template.setField("changed field");
		String request = JsonHelper.getRequestFileAsString("template/update_template_success.json");
		String response = JsonHelper.getResponseFileAsString("template/update_template_success.json");

		mvc.perform(put("/templates/6e71d0d568e134c029203593b00a0103e7cdf30b").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(request)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().json(response));

		verify(templateService, times(1)).save(any(Template.class));
	}
	
	@Test
	public void shouldDeleteTemplate() throws Exception {
		mvc.perform(delete("/templates/6e71d0d568e134c029203593b00a0103e7cdf30b").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isNoContent());

		verify(templateService, times(1)).remove(anyString());
	}

	@Test
	public void shouldGetAllTemplates() throws Exception {
		String response = JsonHelper.getResponseFileAsString("template/get_all_templates_success.json");

		mvc.perform(get("/templates").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().json(response));

		verify(templateService, times(1)).getAll(PageRequest.of(0, 10));
	}
	
	@Test
	public void shouldGetOneTemplate() throws Exception {
		String response = JsonHelper.getResponseFileAsString("template/get_one_template_success.json");

		mvc.perform(get("/templates/6e71d0d568e134c029203593b00a0103e7cdf30b").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().json(response));

		verify(templateService, times(1)).get("6e71d0d568e134c029203593b00a0103e7cdf30b");
	}
	
	@Test
	public void shouldThrowErrorWhenGetUnexistingTemplate() throws Exception {
		String response = JsonHelper.getResponseFileAsString("template/get_one_template_not_found.json");
		
		mvc.perform(get("/templates/unexisting_template")
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isNotFound())
				.andExpect(content().json(response));
		
		verify(templateService, times(1)).get("unexisting_template");
	}

}
