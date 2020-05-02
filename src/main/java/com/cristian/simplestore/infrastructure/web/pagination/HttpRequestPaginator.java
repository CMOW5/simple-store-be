package com.cristian.simplestore.infrastructure.web.pagination;

import javax.servlet.http.HttpServletRequest;

import com.cristian.simplestore.domain.pagination.Paginator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.*;
import java.util.stream.Collectors;

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
		return getPath() + "?" + buildRequestParams(page, size);
	}

	private String buildRequestParams(int page, int size) {
		Map<String, String[]> paramMap = new HashMap<>(request.getParameterMap());

		paramMap.put("page", new String[]{String.valueOf(page)});
		paramMap.put("size", new String[]{String.valueOf(size)});

		List<String> singleQueryParams =
				paramMap.entrySet().stream()
						.map(stringEntry -> String.format("%s=%s", stringEntry.getKey(),  stringEntry.getValue()[0]))
						.collect(Collectors.toList());

		return String.join("&", singleQueryParams);
	}

	
}