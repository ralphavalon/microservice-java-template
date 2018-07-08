package com.example.template.controller.response;

import java.util.ArrayList;

import com.example.template.model.Template;

import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@NoArgsConstructor
public class TemplateResponseList extends ArrayList<TemplateResponse> {

	public TemplateResponseList(Iterable<Template> templates) {
		if (templates != null) {
			for (Template template : templates) {
				add(new TemplateResponse(template));
			}
		}
	}
	
}
