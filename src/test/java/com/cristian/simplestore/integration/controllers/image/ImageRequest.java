package com.cristian.simplestore.integration.controllers.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cristian.simplestore.integration.controllers.TestRequest;
import com.cristian.simplestore.utils.request.JsonResponse;
import com.cristian.simplestore.utils.request.RequestEntityBuilder;
import com.cristian.simplestore.utils.request.RequestSender;

@Component
public class ImageRequest extends TestRequest {

	public static final String IMAGE_BASE_URL = "/api/images";
	
	@Autowired
	public ImageRequest(RequestSender requestSender) {
		super(requestSender);
	}

	public JsonResponse sendGetImageRequest(String imageName) {
		RequestEntityBuilder requestBuilder = createGetImageRequest(imageName);
		ResponseEntity<String> response = send(requestBuilder.build());
		return new JsonResponse(response);
	}
	
	public RequestEntityBuilder createGetImageRequest(String imageName) {
		String url = IMAGE_BASE_URL + "/" + imageName;
		return new RequestEntityBuilder().url(url).httpMethod(HttpMethod.GET);
	}

}
