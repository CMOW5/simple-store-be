package com.cristian.simplestore.infrastructure.adapters.pagination;

import org.springframework.data.domain.Page;

import com.cristian.simplestore.domain.pagination.Paginator;

public class SpringPaginator<T> implements Paginator {

	private Page<T> page;
	
	public SpringPaginator(Page<T> page) {
		this.page = page;
	}
	
	@Override
	public int getCurrentPage() {
		return page.getNumber();
	}

	@Override
	public int getTotalPages() {
		return page.getTotalPages();
	}

	@Override
	public int getPreviousPage() {
		return hasPrevious() ? page.previousPageable().getPageNumber() : getCurrentPage();
	}

	@Override
	public boolean hasMorePages() {
		return page.hasNext() && page.nextPageable().isPaged();
	}

	@Override
	public int getNextPage() {
		return hasMorePages() ? page.nextPageable().getPageNumber() : getCurrentPage();
	}

	@Override
	public int getLastPage() {
		 return page.getTotalPages() - 1;
	}

	@Override
	public int getPageCount() {
		 return page.getContent().size();
	}

	@Override
	public boolean hasPrevious() {
		return page.getPageable().hasPrevious() && page.nextPageable().isPaged();
	}
}
