package com.cristian.simplestore.integration.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cristian.simplestore.basetest.BaseIntegrationTest;
import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.service.CreateProductService;
import com.cristian.simplestore.utils.product.ProductGenerator;

public class CreateProductServiceTest extends BaseIntegrationTest {

	@Autowired
	private CreateProductService createProductService;
	
	@Autowired
    private ProductGenerator productGenerator;
	
		
	@Test
	public void testItSavesTheProductOnDb() {
		// arrange
		Product product = productGenerator.generateRandomProduct();
		
		Product createdProduct = createProductService.create(product);
		
		// assert
		assertThatProductIsEqualToProduct(createdProduct, product);
	}
	
	/*
	private Product findById(Long id) {
		return readProductService.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}
	*/
	
	private void assertThatProductIsEqualToProduct(Product actualProduct,
		      Product expectedProduct) {
		    assertThat(actualProduct.getName()).isEqualTo(expectedProduct.getName());
		    assertThat(actualProduct.getDescription()).isEqualTo(expectedProduct.getDescription());
		    assertThat(actualProduct.getPrice()).isEqualTo(expectedProduct.getPrice());
		    assertThat(actualProduct.getPriceSale()).isEqualTo(expectedProduct.getPriceSale());
		    assertThat(actualProduct.isInSale()).isEqualTo(expectedProduct.isInSale());
		    assertThat(actualProduct.isActive()).isEqualTo(expectedProduct.isActive());
		    assertThat(actualProduct.getStock()).isEqualTo(expectedProduct.getStock());
		    assertThat(actualProduct.getImages().size()).isEqualTo(expectedProduct.getImages().size());
	}
}
