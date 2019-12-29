package com.cristian.simplestore.application.product.update;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.cristian.simplestore.application.product.ProductCommand;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UpdateProductCommand extends ProductCommand {
	
	@NotNull
	private Long id;

	private List<Long> imagesIdsToDelete = new ArrayList<>();
}
