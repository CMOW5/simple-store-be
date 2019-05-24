package com.cristian.simplestore.web.pagination;

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
}
