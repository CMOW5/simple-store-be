package com.cristian.simplestore.domain.pagination;

import java.util.List;

public interface Paginated<T> {
	List<T> getContent();

	Paginator getPaginator();
}
