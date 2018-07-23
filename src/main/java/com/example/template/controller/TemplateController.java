package com.example.template.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.template.config.swagger.ApiPageable;
import com.example.template.controller.request.TemplateCreateRequest;
import com.example.template.controller.request.TemplateUpdateRequest;
import com.example.template.controller.response.TemplateCreateResponse;
import com.example.template.controller.response.TemplateResponse;
import com.example.template.controller.response.TemplateResponseList;
import com.example.template.model.Template;
import com.example.template.service.TemplateService;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(tags= {"templates"})
public class TemplateController {

	@Autowired
	private TemplateService templateService;

	@GetMapping(value = "/templates", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	@ApiPageable
	public Page<TemplateResponse> getTemplates(@ApiIgnore @PageableDefault Pageable pageable) {
		Page<Template> templates = templateService.getAll(pageable);
		return new PageImpl<>(new TemplateResponseList(templates.getContent()), pageable, templates.getTotalElements());
	}
	
	@GetMapping(value = "/templates/{template_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public TemplateResponse getTemplate(@PathVariable(name="template_id") String templateId) {
		return new TemplateResponse(templateService.get(templateId));
	}
	
	@PostMapping(value = "/templates", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public TemplateCreateResponse createTemplate(@RequestBody @Valid TemplateCreateRequest request) {
		Template savedTemplate = templateService.save(request.toModel());
		return new TemplateCreateResponse(savedTemplate);
	}
	
	@PutMapping(value = "/templates/{template_id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public TemplateResponse updateTemplate(@PathVariable(name="template_id") String templateId,
			@RequestBody @Valid TemplateUpdateRequest request) {
		Template savedTemplate = templateService.get(templateId);
		templateService.save(request.toModel(savedTemplate));
		return new TemplateResponse(savedTemplate);
	}
	
	@DeleteMapping(value = "/templates/{template_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteTemplate(@PathVariable(name="template_id") String templateId) {
		templateService.remove(templateId);
	}
	
}
