package com.cristian.simplestore.infrastructure.web.controllers;

import javax.servlet.http.HttpServletRequest;

import com.cristian.simplestore.domain.pagination.Paginator;

public interface RequestPaginator extends Paginator {
	String getPreviousPageUrl();
	
	String getCurrentPageUrl();
	
	String getNextPageUrl();
	
	String getPath();

	static RequestPaginator of(Paginator paginator, HttpServletRequest request, int size, int page) {
		return new RequestPaginatorImpl(paginator, request, size, page);
	}
}
