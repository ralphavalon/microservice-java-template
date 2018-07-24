package com.example.template.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.UUIDGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@EqualsAndHashCode(callSuper=false)
@ToString
@Entity
public class Template extends DateAware {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", 
					strategy = "org.hibernate.id.UUIDGenerator",
					parameters = {
						@Parameter(name = UUIDGenerator.UUID_GEN_STRATEGY_CLASS,
								   value = "org.hibernate.id.uuid.StandardRandomStrategy") 
						})
	private String id;
	private String field;
	private String immutableField;

}
