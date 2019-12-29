package com.cristian.simplestore.integration.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cristian.simplestore.basetest.BaseIntegrationTest;
import com.cristian.simplestore.domain.product.Product;
import com.cristian.simplestore.domain.product.service.UpdateProductService;
import com.cristian.simplestore.utils.product.ProductGenerator;

public class UpdateProductServiceTest extends BaseIntegrationTest {

	@Autowired
	private ProductGenerator productGenerator;

	@Autowired
	private UpdateProductService updateProductService;

	@Test
	public void testItCorrectlyUpdatesTheProduct() {
		// arrange
		Product productToUpdate = productGenerator.saveRandomProductOnDB();
		Product newProductData = productGenerator.generateRandomProduct();
		Product product = new Product(productToUpdate.getId(), newProductData.getName(),
				newProductData.getDescription(), newProductData.getPrice(), newProductData.getPriceSale(),
				newProductData.isInSale(), newProductData.isActive(), newProductData.getCategory(),
				newProductData.getImages(), newProductData.getStock());

		// act
		Product updatedProduct = updateProductService.update(product);
		
		// assert
		assertThatProductIsEqualToProduct(updatedProduct, product);
	}
	
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
