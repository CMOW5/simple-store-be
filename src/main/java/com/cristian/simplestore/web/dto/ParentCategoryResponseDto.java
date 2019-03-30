package com.cristian.simplestore.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentCategoryResponseDto {
	private Long id;
	private String name;
}
