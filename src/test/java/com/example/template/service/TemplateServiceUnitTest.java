package com.example.template.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import javax.ws.rs.NotFoundException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.template.dao.TemplateDao;
import com.example.template.model.Template;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TemplateServiceUnitTest {
	
	@MockBean
	private TemplateDao templateDao;
	
	@Autowired
	private TemplateService templateService;
	
	@Test
	public void shouldSaveTemplate() {
		templateService.save(new Template());
		
		verify(templateDao, times(1)).save(any(Template.class));
	}
	
	@Test
	public void shouldFindAllTemplates() {
		templateService.getAll(PageRequest.of(0, 10));
		
		verify(templateDao, times(1)).findAll(any(PageRequest.class));
	}
	
	@Test
	public void shouldFindOneTemplate() {
		doReturn(Optional.of(new Template())).when(templateDao).findById(anyString());
		templateService.get("123");
		
		verify(templateDao, times(1)).findById(anyString());
	}
	
	@Test
	public void shouldRemoveTemplate() {
		templateService.remove("123");
		
		verify(templateDao, times(1)).deleteById(anyString());
	}
	
	@Test(expected=NotFoundException.class)
	public void shouldThrowErrorWhenDoesntFindOneTemplate() {
		doReturn(Optional.ofNullable(null)).when(templateDao).findById(eq("unexisting_template"));
		templateService.get("unexisting_template");
	}

}
