package com.example.template.controller.request;

import javax.validation.constraints.NotBlank;

import com.example.template.model.Template;

import lombok.Getter;

@Getter
public class TemplateCreateRequest {
	
	@NotBlank
	private String field;
	@NotBlank
	private String immutableField;
	
	public Template toModel() {
		Template template = new Template();
		template.setField(this.field);
		template.setImmutableField(this.immutableField);
		return template;
	}
	
}
