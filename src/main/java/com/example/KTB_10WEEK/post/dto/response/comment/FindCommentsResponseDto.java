package com.example.KTB_10WEEK.post.dto.response.comment;

import lombok.Getter;

import java.util.List;

@Getter
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

