package com.cristian.simplestore.integration.infrastructure.web.product.databuilder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import com.cristian.simplestore.utils.request.MultiPartFormBuilder;
import com.cristian.simplestore.utils.request.RequestEntityBuilder;

public class ProductRequestFactory {

    public static RequestEntityBuilder createFindAllProductsRequest() {
        String url = "/api/admin/products";
        return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.GET);
    }

    public static RequestEntityBuilder createFindProductByIdRequest(Long id) {
        String url = "/api/admin/products/" + id;
        return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.GET);
    }

    public static RequestEntityBuilder createProductCreateRequest(MultiPartFormBuilder form) {
        String url = "/api/admin/products";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = form.build();
        return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.POST).headers(headers).body(body);
    }

    public static RequestEntityBuilder createProductUpdateRequest(Long productId, MultiPartFormBuilder form) {
        String url = "/api/admin/products/" + productId;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = form.build();
        return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.PUT).headers(headers).body(body);
    }

    public static RequestEntityBuilder createProductDeleteRequest(Long productId) {
        String url = "/api/admin/products/" + productId;
        return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.DELETE);
    }
}
