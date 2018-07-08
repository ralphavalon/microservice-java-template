package com.example.template.controller.response;

import com.example.template.model.Template;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TemplateResponse {
	
	private String id;
	private String field;
	private String immutableField;
	
	public TemplateResponse(Template template) {
		this.id = template.getId();
		this.field = template.getField();
		this.immutableField = template.getImmutableField();
	}
	
}
