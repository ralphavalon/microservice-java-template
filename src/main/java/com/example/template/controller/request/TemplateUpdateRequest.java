package com.example.template.controller.request;

import javax.validation.constraints.NotBlank;

import com.example.template.model.Template;

import lombok.Getter;

@Getter
public class TemplateUpdateRequest {
	
	@NotBlank
	private String field;
	
	public Template toModel(Template template) {
		template.setField(this.field);
		return template;
	}

}
