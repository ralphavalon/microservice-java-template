package com.example.template.model;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class DateAware {
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@PrePersist
	void onCreate() {
		this.setCreatedAt(LocalDateTime.now());
	}

	@PreUpdate
	void onUpdate() {
		this.setUpdatedAt(LocalDateTime.now());
	}

}
