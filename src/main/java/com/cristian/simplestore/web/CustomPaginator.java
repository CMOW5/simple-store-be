package com.cristian.simplestore.web;

import org.springframework.data.domain.Pageable;

public interface CustomPaginator extends Pageable {

  int getTotalPages();

  String getPreviousPageUrl();

  Integer getPreviousPage();

  boolean hasMorePages();

  Integer getNextPage();

  String getCurrentPageUrl();
  
}
