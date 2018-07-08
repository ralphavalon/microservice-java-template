package com.example.template.controller.response;

import com.example.template.model.Template;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TemplateCreateResponse {
	
	private String id;
	
	public TemplateCreateResponse(Template repository) {
		this.id = repository.getId();
	}
	
}
