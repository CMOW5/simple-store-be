package com.cristian.simplestore.application.category.update;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoryCommand {
	private Long id;
	
	private String name;

	private MultipartFile image;

	private Long parentId;
}
