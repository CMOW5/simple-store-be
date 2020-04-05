package com.cristian.simplestore.infrastructure.web.controllers.image.dto;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
public class ImageDto {
	
	private Long id;
	
	private String url;
}
