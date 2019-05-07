package com.cristian.simplestore.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "currentPage", "currentPageUrl", "hasMorePages" ,"nextPage", "nextPageUrl", 
  "hasPrevious" ,"previousPage", "previousPageUrl", "pageSize", "totalPages", "offset"})
public class CustomPaginatorImp implements CustomPaginator {

  Page<?> page;
  Pageable pageable;
  
  public <T> CustomPaginatorImp(Page<T> page) {
    this.page = page;
    this.pageable = page.getPageable();
  }
  
  @Override
  @JsonProperty("currentPage")
  public int getPageNumber() {
    return pageable.getPageNumber();
  }

  // TODO
  @Override
  public String getCurrentPageUrl() {
    return "";
  }

  // TODO
  @Override
  public Integer getNextPage() {
    return hasMorePages() ? page.nextPageable().getPageNumber() : null;
  }
  
  //TODO
  @JsonProperty
  @Override
  public boolean hasMorePages() {
    return page.hasNext();
  }
  
  //TODO
  public String getNextPageUrl() {
    return "";
  }
  
  // TODO
  @Override
  public Integer getPreviousPage() {
    return hasPrevious() ? page.previousPageable().getPageNumber() : null;
  }
  
  @Override
  @JsonProperty
  public boolean hasPrevious() {
    return pageable.hasPrevious();
  }
  
  // TODO
  @Override
  public String getPreviousPageUrl() {
    return "";
  }
  
  @Override
  public int getPageSize() {
    return pageable.getPageSize();
  }

  @Override
  public long getOffset() {
    return pageable.getOffset();
  }

  @Override
  public Sort getSort() {
    return pageable.getSort();
  }

  @Override
  public Pageable next() {
    return pageable.next();
  }

  @Override
  public Pageable previousOrFirst() {
    return pageable.previousOrFirst();
  }

  @Override
  public Pageable first() {
    return pageable.first();
  }
   
  // TODO
  @Override
  public int getTotalPages() {
    return page.getTotalPages();
  }
  
  /*
   * $paginator = array(); 
   * $paginator["current_page"] = $data->currentPage(); DONE
   * $paginator["current_page_url"] = $data->url($data->currentPage()); DONE
   * $paginator["next_page_url"] = $data->nextPageUrl(); DONE 
   * $paginator["prev_page_url"] = $data->previousPageUrl();  DONE
   * $paginator["per_page"] = $data->perPage();  DONE
   * $paginator["last_page"] = $data->lastPage();  // DONE
   * $paginator["has_more_pages"] = $data->hasMorePages();  DONE
   * $paginator["path"] = $data->jsonSerialize()["path"];  
   * $paginator["count"] = $data->count(); $paginator["total"] = $data->total();
   */
}
