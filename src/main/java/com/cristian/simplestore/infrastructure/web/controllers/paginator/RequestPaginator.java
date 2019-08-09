package com.cristian.simplestore.infrastructure.web.controllers.paginator;

import javax.servlet.http.HttpServletRequest;

import com.cristian.simplestore.domain.pagination.Paginator;

public interface RequestPaginator extends Paginator {
	String getPreviousPageUrl();
	
	String getCurrentPageUrl();
	
	String getNextPageUrl();
	
	String getPath();

	static RequestPaginator of(Paginator paginator, HttpServletRequest request, int pageSize, int currentPage) {
		return new HttpRequestPaginator(paginator, request, pageSize, currentPage);
	}
}
