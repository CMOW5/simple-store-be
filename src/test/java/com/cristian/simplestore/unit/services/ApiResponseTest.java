package com.cristian.simplestore.unit.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.FieldError;

import com.cristian.simplestore.BaseTest;
import com.cristian.simplestore.web.utils.response.ApiError;
import com.cristian.simplestore.web.utils.response.ApiResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiResponseTest extends BaseTest {
	
	@Test
	public void testsTheResponseBody() {
		ApiResponse apiResponse = new ApiResponse();
		ResponseEntity<?> response = apiResponse.content("some content").status(HttpStatus.BAD_REQUEST).build();
		
		String expectedBody = "{content=some content, status=400}";
		assertThat(response.getBody().toString()).isEqualTo(expectedBody);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void testsTheResponseErrors() {
		long ERRORS_SIZE = 4;
		List<FieldError> errors = createErrors(4);
		
		ResponseEntity<Map<String, Object>> response = 
				(ResponseEntity<Map<String, Object>>) new ApiResponse().content("some content")
				.status(HttpStatus.BAD_REQUEST)
				.errors(errors)
				.build();
		
		List<ApiError> responseErrors = (List<ApiError>) response.getBody().get("errors");
		
		assertThat(responseErrors.size()).isEqualTo(ERRORS_SIZE);
		assertThatErrorsFieldsAreCorrect(responseErrors);
	}
	
	private List<FieldError> createErrors(long numberOfErrors) {
		List<FieldError> errors = new ArrayList<>();
		
		for (int i = 0; i < numberOfErrors; i++) {
			String objectName = "ObjectName" + i;
			String field = "field" + i;
			String defaultMessage = "defaultMessage" + i;
			FieldError error = new FieldError(objectName, field, defaultMessage);
			errors.add(error);
		}
		
		return errors;
	}
	
	private void assertThatErrorsFieldsAreCorrect(List<ApiError> responseErrors) {
		for (int i = 0; i < responseErrors.size(); i++) {
			ApiError error = responseErrors.get(i);
			String expectedField = "field" + i;
			String expectedDefaultMessage = "defaultMessage" + i;
			assertThat(error.getField()).isEqualTo(expectedField);
			assertThat(error.getDefaultMessage()).isEqualTo(expectedDefaultMessage);
		}
	}
}
