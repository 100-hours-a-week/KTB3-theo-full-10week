package com.example.KTB_7WEEK.post.dto.response;

import java.util.List;

public class FindPostsResponseDto {
    private long totalPages;
    private long totalElements;
    private long size;
    private long currentPage;
    private boolean hasNext;
    private List<FindPostResponseDto> contents;

    public FindPostsResponseDto() {
    }

    public long getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public long getSize() {
        return size;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public boolean hasNext() {
        return hasNext;
    }

    public List<FindPostResponseDto> getContents() {
        return contents;
    }

    public static FindPostsResponseDto toDto(long totalPages, long totalElements, long size,
                                             long currentPage, boolean hasNext,
                                             List<FindPostResponseDto> contents) {
        FindPostsResponseDto dto = new FindPostsResponseDto();
        dto.totalPages = totalPages;
        dto.totalElements = totalElements;
        dto.size = size;
        dto.currentPage = currentPage;
        dto.hasNext = hasNext;
        dto.contents = contents;

        return dto;
    }
}
