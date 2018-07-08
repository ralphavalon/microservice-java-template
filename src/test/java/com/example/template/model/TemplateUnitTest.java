package com.example.template.model;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class TemplateUnitTest {
	
	@Test
	public void shouldCreateTemplate() throws Exception {
		EqualsVerifier.forClass(Template.class)
		.withIgnoredFields("createdAt", "updatedAt")
		.verify();
	}
	
}
