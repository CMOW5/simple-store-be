package com.cristian.simplestore.unit.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.persistence.entities.Product;
import com.cristian.simplestore.utils.DbCleaner;
import com.cristian.simplestore.utils.ProductTestsUtils;
import com.cristian.simplestore.web.dto.ProductResponseDto;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProductResponseDto extends BaseTest {
	
	@Autowired 
	private ProductTestsUtils productUtils;
	
	@Autowired
	private DbCleaner dbCleaner;
	
	@Before
	public void setUp() {
		cleanUpDb();
	}
	
	@After
	public void tearDown() {
		cleanUpDb();
	}
	
	private void cleanUpDb() {
		dbCleaner.cleanProductsTable();
		dbCleaner.cleanCategoriesTable();
	}
	
	@Test
	public void convertEntityToDto() {
		Product product = productUtils.saveRandomProductOnDBWithImages();
		ProductResponseDto productDto = new ProductResponseDto(product);
		
		assertThatImageAndDtoDataAreEqual(product, productDto);
	}


	private void assertThatImageAndDtoDataAreEqual(Product product, ProductResponseDto productDto) {
		assertThat(product.getId()).isEqualTo(productDto.getId());
		assertThat(product.getName()).isEqualTo(productDto.getName());
		assertThat(product.getDescription()).isEqualTo(productDto.getDescription());
		assertThat(product.getPrice()).isEqualTo(productDto.getPrice());
		assertThat(product.getPriceSale()).isEqualTo(productDto.getPriceSale());
		assertThat(product.isInSale()).isEqualTo(productDto.isInSale());
		assertThat(product.isActive()).isEqualTo(productDto.isActive());
		assertThat(product.getStock()).isEqualTo(productDto.getStock());
		assertThat(product.getCategory().getId()).isEqualTo(productDto.getCategory().getId());
		assertThat(product.getImages().size()).isEqualTo(productDto.getImages().size());	
	}
}
