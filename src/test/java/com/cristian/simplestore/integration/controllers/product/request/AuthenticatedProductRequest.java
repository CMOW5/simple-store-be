package com.cristian.simplestore.integration.controllers.product.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.request.RequestEntityBuilder;
import com.cristian.simplestore.utils.request.RequestSender;
import com.cristian.simplestore.utils.request.TokenGenerator;

@Component
public class AuthenticatedProductRequest extends ProductRequest {

	protected TokenGenerator tokenGenerator;
	
	@Autowired
	public AuthenticatedProductRequest(RequestSender requestSender, TokenGenerator tokenGenerator) {
		super(requestSender);
		this.tokenGenerator = tokenGenerator;
	}

	@Override
	protected RequestEntityBuilder createFindAllProductsRequest() {
		RequestEntityBuilder requestBuilder = super.createFindAllProductsRequest();
		return addJwtAuthToRequest(requestBuilder);
	}

	@Override
	protected RequestEntityBuilder createFindProductByIdRequest(Long id) {
		RequestEntityBuilder requestBuilder = super.createFindProductByIdRequest(id);
		return addJwtAuthToRequest(requestBuilder);
	}

	@Override
	protected RequestEntityBuilder createProductCreateRequest(MultiPartFormBuilder form) {
		RequestEntityBuilder requestBuilder = super.createProductCreateRequest(form);
		return addJwtAuthToRequest(requestBuilder);
	}

	@Override
	protected RequestEntityBuilder createProductUpdateRequest(Long categoryId, MultiPartFormBuilder form) {
		RequestEntityBuilder requestBuilder = super.createProductUpdateRequest(categoryId, form);
		return addJwtAuthToRequest(requestBuilder);
	}

	@Override
	protected RequestEntityBuilder createProductDeleteRequest(Long categoryId) {
		RequestEntityBuilder requestBuilder = super.createProductDeleteRequest(categoryId);
		return addJwtAuthToRequest(requestBuilder);
	}
	
	private RequestEntityBuilder addJwtAuthToRequest(RequestEntityBuilder requestBuilder) {
	    return requestBuilder.withJwtAuth(tokenGenerator);
	 }
}
