package com.example.template.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.template.model.Template;

public interface TemplateDao extends JpaRepository<Template, String>{

}
