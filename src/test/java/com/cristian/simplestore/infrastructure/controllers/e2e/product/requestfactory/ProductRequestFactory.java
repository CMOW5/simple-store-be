package com.cristian.simplestore.infrastructure.controllers.e2e.product.requestfactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import com.cristian.simplestore.utils.MultiPartFormBuilder;
import com.cristian.simplestore.utils.request.RequestEntityBuilder;

public final class ProductRequestFactory {

	private static final String ADMIN_PRODUCTS_BASE_URL = "/api/admin/products";
	
	private ProductRequestFactory() {}

    public static RequestEntityBuilder createFindAllProductsRequest() {
        String url = ADMIN_PRODUCTS_BASE_URL;
        return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.GET);
    }

    public static RequestEntityBuilder createFindProductByIdRequest(Long id) {
        String url = ADMIN_PRODUCTS_BASE_URL + "/" + id;
        return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.GET);
    }

    public static RequestEntityBuilder createProductCreateRequest(MultiPartFormBuilder form) {
        String url = ADMIN_PRODUCTS_BASE_URL;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = form.build();
        return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.POST).headers(headers).body(body);
    }

    public static RequestEntityBuilder createProductUpdateRequest(Long productId, MultiPartFormBuilder form) {
        String url = ADMIN_PRODUCTS_BASE_URL + "/" + productId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = form.build();
        return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.PUT).headers(headers).body(body);
    }

    public static RequestEntityBuilder createProductDeleteRequest(Long productId) {
        String url = ADMIN_PRODUCTS_BASE_URL + "/" + productId;
        return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.DELETE);
    }
}