package com.cristian.simplestore.application.category.create;

import com.cristian.simplestore.application.category.CategoryCommand;

import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CreateCategoryCommand extends CategoryCommand { 
}
