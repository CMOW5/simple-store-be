package com.cristian.simplestore.domain.pagination;

public interface Paginator {
	int getTotalPages();
	
	int getCurrentPage();
	
	int getNextPage();
	
	int getPreviousPage();
	
	int getLastPage();

	boolean hasPrevious();
	
	boolean hasMorePages();

	int getPageCount();
}
