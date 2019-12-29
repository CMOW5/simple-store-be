package com.cristian.simplestore.application.category;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public abstract class CategoryCommand {
	@NotNull
	@Size(min = 3, max = 100)
	private String name;

	private MultipartFile image;
	
	private Long parentId;
}