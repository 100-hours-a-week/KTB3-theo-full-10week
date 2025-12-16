package com.todayseafood.api.app.util.paginationPolicy;

import org.springframework.data.domain.Sort;

public interface PaginationPolicy<T> {
    int size();

    public Sort sort();
}
