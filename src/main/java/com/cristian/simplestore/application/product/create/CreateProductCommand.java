package com.cristian.simplestore.application.product.create;

import com.cristian.simplestore.application.product.ProductCommand;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CreateProductCommand extends ProductCommand {

}