package com.cristian.simplestore.infrastructure.web.pagination;

import javax.servlet.http.HttpServletRequest;

import com.cristian.simplestore.domain.pagination.Paginator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "currentPage", "currentPageUrl", "hasMorePages", "nextPage", "nextPageUrl", "hasPrevious",
		"previousPage", "previousPageUrl", "pageSize", "pageCount" })
class HttpRequestPaginator implements RequestPaginator {

	private final Paginator paginator;
	private final HttpServletRequest request;
	private final int pageSize;
	private final int currentPage;

	public HttpRequestPaginator(Paginator paginator, HttpServletRequest request, int pageSize, int currentPage) {
		this.paginator = paginator;
		this.request = request;
		this.pageSize = pageSize;
		this.currentPage = currentPage;
	}
	
	@Override
	public int getCurrentPage() {
		return paginator.getCurrentPage();
	}

	@Override
	public int getTotalPages() {
		return paginator.getTotalPages();
	}

	@Override
	public int getPreviousPage() {
		return paginator.getPreviousPage();
	}

	@Override
	@JsonProperty
	public boolean hasPrevious() {
		return paginator.hasPrevious();
	}

	@Override
	@JsonProperty
	public boolean hasMorePages() {
		return paginator.hasMorePages();
	}

	@Override
	public int getNextPage() {
		return paginator.getNextPage();
	}

	@Override
	public int getLastPage() {
		return paginator.getLastPage();
	}

	@Override
	public int getPageCount() {
		return paginator.getPageCount();
	}

	@Override
	public String getPreviousPageUrl() {
		return hasPrevious() ? buildUrlPath(getPreviousPage(), pageSize) : null;
	}

	@Override
	public String getCurrentPageUrl() {
		return buildUrlPath(currentPage, pageSize);
	}

	@Override
	public String getNextPageUrl() {
		return hasMorePages() ? buildUrlPath(getNextPage(), pageSize) : null;
	}

	@Override
	public String getPath() {
		return request.getRequestURL().toString();
	}
	
	@Override
	public long getTotalElements() {
		return paginator.getTotalElements();
	}

	private String buildUrlPath(int page, int size) {
		return getPath() + "?page=" + page + "&size=" + size;
	}

	
}