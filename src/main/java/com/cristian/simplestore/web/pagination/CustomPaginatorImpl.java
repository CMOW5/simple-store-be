package com.cristian.simplestore.web.pagination;

import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "currentPage", "currentPageUrl", "hasMorePages" ,"nextPage", "nextPageUrl", 
  "hasPrevious" ,"previousPage", "previousPageUrl", "pageSize", "totalPages", "offset"})
public class CustomPaginatorImpl implements CustomPaginator {

  private Page<?> page;
  private Pageable paginationInfo;
  private HttpServletRequest request;
  private int currentPageNumber;
  private int pageSize;
    
  public <T> CustomPaginatorImpl(Page<T> page, int currentPageNumber, int pageSize, HttpServletRequest request) {
    this.page = page;
    this.paginationInfo = page.getPageable();
    this.currentPageNumber = currentPageNumber;
    this.pageSize = pageSize;
    this.request = request;
  }
   

  @Override
  @JsonProperty("currentPage")
  public int getPageNumber() {
    return paginationInfo.getPageNumber();
  }

  @Override
  public String getCurrentPageUrl() {
    return buildUrlPath(currentPageNumber, pageSize);
  }

  @Override
  public Integer getNextPage() {
    return hasMorePages() ? page.nextPageable().getPageNumber() : null;
  }
  
  @JsonProperty
  @Override
  public boolean hasMorePages() {
    return page.hasNext();
  }
  
  public String getNextPageUrl() {
    return hasMorePages() ? buildUrlPath(getNextPage(), pageSize) : null;
  }
  
  @Override
  public Integer getPreviousPage() {
    return hasPrevious() ? page.previousPageable().getPageNumber() : null;
  }
  
  @Override
  @JsonProperty
  public boolean hasPrevious() {
    return paginationInfo.hasPrevious();
  }
  
  @Override
  public String getPreviousPageUrl() {
    return hasPrevious() ? buildUrlPath(getPreviousPage(), pageSize) : null;
  }
  
  @Override
  public int getPageSize() {
    return paginationInfo.getPageSize();
  }

  @Override
  public long getOffset() {
    return paginationInfo.getOffset();
  }

  @Override
  public Sort getSort() {
    return paginationInfo.getSort();
  }

  @Override
  public Pageable next() {
    return paginationInfo.next();
  }

  @Override
  public Pageable previousOrFirst() {
    return paginationInfo.previousOrFirst();
  }

  @Override
  public Pageable first() {
    return paginationInfo.first();
  }
   
  @Override
  public int getTotalPages() {
    return page.getTotalPages();
  }

  @Override
  public String getPath() {
    return request.getRequestURL().toString();
  }

  @Override
  public Integer getLastPage() {
    return page.getTotalPages() - 1;
  }
  
  private String buildUrlPath(int page, int size) {
    return getPath() + "?page="+ page + "&size=" + size;
  }

  @Override
  public int getPageCount() {
    return page.getContent().size();
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
