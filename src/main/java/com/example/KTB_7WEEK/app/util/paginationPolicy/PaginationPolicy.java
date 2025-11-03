package com.example.KTB_7WEEK.app.util.paginationPolicy;

import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.function.Predicate;

public interface PaginationPolicy<T> {
    int size();

    public Sort sort();
}
