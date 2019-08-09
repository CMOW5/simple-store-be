package com.cristian.simplestore.infrastructure.web.controllers;

import javax.servlet.http.HttpServletRequest;

import com.cristian.simplestore.domain.pagination.Paginator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "currentPage", "currentPageUrl", "hasMorePages", "nextPage", "nextPageUrl", "hasPrevious",
		"previousPage", "previousPageUrl", "pageSize", "pageCount" })
class RequestPaginatorImpl implements RequestPaginator {

	private final Paginator paginator;
	private final HttpServletRequest request;
	private final int pageSize;
	private final int currentPageNumber;

	public RequestPaginatorImpl(Paginator paginator, HttpServletRequest request, int pageSize, int currentPageNumber) {
		this.paginator = paginator;
		this.request = request;
		this.pageSize = pageSize;
		this.currentPageNumber = currentPageNumber;
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
		return buildUrlPath(currentPageNumber, pageSize);
	}

	@Override
	public String getNextPageUrl() {
		return hasMorePages() ? buildUrlPath(getNextPage(), pageSize) : null;
	}

	@Override
	public String getPath() {
		return request.getRequestURL().toString();
	}

	private String buildUrlPath(int page, int size) {
		return getPath() + "?page=" + page + "&size=" + size;
	}

	/*
	 * $paginator = array(); $paginator["current_page"] = $data->currentPage(); DONE
	 * $paginator["current_page_url"] = $data->url($data->currentPage()); DONE
	 * $paginator["next_page_url"] = $data->nextPageUrl(); DONE
	 * $paginator["prev_page_url"] = $data->previousPageUrl(); DONE
	 * $paginator["per_page"] = $data->perPage(); DONE $paginator["last_page"] =
	 * $data->lastPage(); // DONE $paginator["has_more_pages"] =
	 * $data->hasMorePages(); DONE $paginator["path"] =
	 * $data->jsonSerialize()["path"]; $paginator["count"] = $data->count();
	 * $paginator["total"] = $data->total();
	 */
}