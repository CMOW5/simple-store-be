package com.cristian.simplestore.application.category.update;

import javax.validation.constraints.NotNull;

import com.cristian.simplestore.application.category.CategoryCommand;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UpdateCategoryCommand extends CategoryCommand {
	
	@NotNull
	private Long id;
}