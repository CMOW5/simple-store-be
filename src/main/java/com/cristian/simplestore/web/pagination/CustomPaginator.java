package com.cristian.simplestore.web.pagination;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomPaginator extends Pageable {
  
  int getTotalPages();
  
  String getPreviousPageUrl();

  Integer getPreviousPage();

  boolean hasMorePages();

  Integer getNextPage();
  
  Integer getLastPage();

  String getCurrentPageUrl();
  
  int getPageCount();
  
  String getPath();  
  
  public static <T> CustomPaginator of(Page<T> paginatedResult, int page, int size, HttpServletRequest request) {
	  return new CustomPaginatorImpl(paginatedResult, page, size, request);
  }
}
