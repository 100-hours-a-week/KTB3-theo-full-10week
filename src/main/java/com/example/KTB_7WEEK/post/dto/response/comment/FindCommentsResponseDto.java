package com.example.KTB_7WEEK.post.dto.response.comment;

import java.util.List;

public class FindCommentsResponseDto {
    private long postId;
    private long totalPages;
    private long totalElements;
    private long size;
    private long currentPage;
    private boolean hasNext;
    private List<FindCommentResponseDto> contents;

    public FindCommentsResponseDto() {
    }

    public long getPostId() {
        return postId;
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

    public List<FindCommentResponseDto> getContents() {
        return contents;
    }

    public static FindCommentsResponseDto toDto(long postId, long totalPages, long totalElements,
                                                long size, long currentPage, boolean hasNext,
                                                List<FindCommentResponseDto> contents) {
        FindCommentsResponseDto dto = new FindCommentsResponseDto();
        dto.postId = postId;
        dto.totalPages = totalPages;
        dto.totalElements = totalElements;
        dto.currentPage = currentPage;
        dto.size = size;
        dto.hasNext = hasNext;
        dto.contents = contents;
        return dto;
    }
}

