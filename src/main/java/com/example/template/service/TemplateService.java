package com.example.template.service;

import javax.ws.rs.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.template.dao.TemplateDao;
import com.example.template.model.Template;

@Service
public class TemplateService {
	
	@Autowired
	private TemplateDao templateDao;
	
	public Template save(Template template) {
		return templateDao.save(template);
	}

	public Page<Template> getAll(Pageable pageable) {
		return templateDao.findAll(pageable);
	}

	public Template get(String templateId) {
		return templateDao.findById(templateId)
				.orElseThrow(() -> new NotFoundException(templateId));
	}

	public void remove(String templateId) {
		templateDao.deleteById(templateId);
	}

}
