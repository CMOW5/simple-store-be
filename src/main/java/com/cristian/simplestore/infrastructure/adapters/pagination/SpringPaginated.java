package com.cristian.simplestore.infrastructure.adapters.pagination;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cristian.simplestore.domain.pagination.Paginated;
import com.cristian.simplestore.domain.pagination.Paginator;

public class SpringPaginated<T> implements Paginated<T> {

	private final List<T> content;
	private final Paginator paginator;
	
	public static <E, T> Paginated<T> of(Page<E> paginated, List<T> content) {
		Paginator paginator = new SpringPaginator<>(paginated);
		return new SpringPaginated<>(content, paginator);
	}

	private SpringPaginated(List<T> content, Paginator paginator) {
		this.content = content;
		this.paginator = paginator;
	}
 	
	@Override
	public List<T> getContent() {
		return content;
	}

	@Override
	public Paginator getPaginator() {
		return paginator;
	}	
}
