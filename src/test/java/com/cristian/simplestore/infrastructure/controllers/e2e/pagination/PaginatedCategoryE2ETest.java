package com.cristian.simplestore.infrastructure.controllers.e2e.pagination;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.cristian.simplestore.infrastructure.controllers.BaseIntegrationTest;
import com.cristian.simplestore.infrastructure.controllers.e2e.category.requestfactory.CategoryRequestFactory;
import com.cristian.simplestore.utils.category.CategoryGenerator;
import com.cristian.simplestore.utils.request.JsonRequestSender;
import com.cristian.simplestore.utils.request.JsonResponse;
import com.cristian.simplestore.utils.request.RequestEntityBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PaginatedCategoryE2ETest extends BaseIntegrationTest {

	@Autowired
	private CategoryGenerator categoryGenerator;

	@Autowired
	private JsonRequestSender requestSender;

	@Test
	public void testItFindsAllCategories() throws IOException {
		int maxCategoriesSize = 20;
		categoryGenerator.saveRandomCategoriesOnDb(maxCategoriesSize);
		Integer page = 0;
		Integer size = 5;

		RequestEntityBuilder request = CategoryRequestFactory.createFindAllCategoriesRequest()
				.addRequestParam("page", String.valueOf(page)).addRequestParam("size", String.valueOf(size));

		JsonResponse response = requestSender.send(request.build());
		
		List<?> responseCategories = (List<?>) response.getContent(List.class);

		Map<?, ?> paginator = response.getPaginator(Map.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseCategories.size()).isEqualTo(size);
		assertThat(paginator.get("currentPage")).isEqualTo(page);
		assertThat(paginator.get("hasMorePages")).isEqualTo(true);
		assertThat(paginator.get("nextPage")).isEqualTo(page + 1);
		assertThat(paginator.get("hasPrevious")).isEqualTo(false);
		assertThat(paginator.get("previousPage")).isEqualTo(0);
		assertThat(paginator.get("pageCount")).isEqualTo(size);
		assertThat(paginator.get("totalPages")).isEqualTo(((int) (maxCategoriesSize / size)));
	}
}