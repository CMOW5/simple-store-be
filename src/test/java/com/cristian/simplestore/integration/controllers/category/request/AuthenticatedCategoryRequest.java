package com.cristian.simplestore.integration.controllers.category.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.request.RequestEntityBuilder;
import com.cristian.simplestore.utils.request.RequestSender;
import com.cristian.simplestore.utils.request.TokenGenerator;

@Component
public class AuthenticatedCategoryRequest extends CategoryRequest {

	protected TokenGenerator tokenGenerator;

	@Autowired
	public AuthenticatedCategoryRequest(RequestSender requestSender, TokenGenerator tokenGenerator) {
		super(requestSender);
		this.tokenGenerator = tokenGenerator;
	}

	@Override
	public RequestEntityBuilder createFindAllCategoriesRequest() {
		RequestEntityBuilder requestBuilder = super.createFindAllCategoriesRequest();
		return addJwtAuthToRequest(requestBuilder);
	}

	@Override
	protected RequestEntityBuilder createFindCategoryByIdRequest(Long id) {
		RequestEntityBuilder requestBuilder = super.createFindCategoryByIdRequest(id);
		return addJwtAuthToRequest(requestBuilder);
	}

	@Override
	protected RequestEntityBuilder createCategoryCreateRequest(MultiPartFormBuilder form) {
		RequestEntityBuilder requestBuilder = super.createCategoryCreateRequest(form);
		return addJwtAuthToRequest(requestBuilder);
	}

	@Override
	protected RequestEntityBuilder createCategoryUpdateRequest(Long categoryId, MultiPartFormBuilder form) {
		RequestEntityBuilder requestBuilder = super.createCategoryUpdateRequest(categoryId, form);
		return addJwtAuthToRequest(requestBuilder);
	}

	@Override
	protected RequestEntityBuilder createCategoryDeleteRequest(Long categoryId) {
		RequestEntityBuilder requestBuilder = super.createCategoryDeleteRequest(categoryId);
		return addJwtAuthToRequest(requestBuilder);
	}

	private RequestEntityBuilder addJwtAuthToRequest(RequestEntityBuilder requestBuilder) {
		return requestBuilder.withJwtAuth(tokenGenerator);
	}
}
