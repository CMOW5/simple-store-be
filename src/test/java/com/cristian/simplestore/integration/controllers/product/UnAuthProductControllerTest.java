package com.cristian.simplestore.integration.controllers.product;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import com.cristian.simplestore.integration.controllers.BaseIntegrationTest;
import com.cristian.simplestore.integration.controllers.product.request.UnauthenticatedProductRequest;
import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.product.ProductFormUtils;
import com.cristian.simplestore.utils.request.JsonResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UnAuthProductControllerTest extends BaseIntegrationTest {

	@Autowired
	private ProductFormUtils productFormUtils;

	@Autowired
	private UnauthenticatedProductRequest request;

	@Test
	public void testItFailsFindsAllProductsWithoutCredentials()
			throws JsonParseException, JsonMappingException, IOException {
		JsonResponse response = request.sendFindAllProductsRequest();
		assertResponseHasUnauthorizedStatus(response);
	}

	@Test
	public void testItFailsFindProductByIdWithoutCredentials()
			throws JsonParseException, JsonMappingException, IOException {
		Long productId = 1L;

		JsonResponse response = request.sendFindProductByIdRequest(productId);

		assertResponseHasUnauthorizedStatus(response);
	}

	@Test
	public void testItFailsCreateProductWithoutCredentials()
			throws JsonParseException, JsonMappingException, IOException {
		MultiPartFormBuilder form = productFormUtils.generateRandomProductCreateRequestForm();

		JsonResponse response = request.sendProductCreateRequest(form);

		assertResponseHasUnauthorizedStatus(response);
	}

	@Test
	public void testItFailsUpdateProductWithoutCredentials()
			throws JsonParseException, JsonMappingException, IOException {
		Long productId = 1L;
		MultiPartFormBuilder form = productFormUtils.generateRandomProductUpdateRequestForm();

		JsonResponse response = request.sendProductUpdateRequest(productId, form);

		assertResponseHasUnauthorizedStatus(response);
	}

	@Test
	public void testItFailsDeletesProductWithoutCredentials()
			throws JsonParseException, JsonMappingException, IOException {
		Long productId = 1L;

		JsonResponse response = request.sendProductDeleteRequest(productId);

		assertResponseHasUnauthorizedStatus(response);
	}

	private void assertResponseHasUnauthorizedStatus(JsonResponse response) {
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
	}
}
