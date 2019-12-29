package com.cristian.simplestore.utils.request.jsonrequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cristian.simplestore.utils.request.sender.RequestSender;

@Service
public class JsonRequestSender {

    @Autowired
    RequestSender requestSender;

    public JsonResponse send(RequestEntity<?> request) {
        ResponseEntity<String> response = requestSender.send(request, String.class);
        return new JsonResponse(response);
    }
}