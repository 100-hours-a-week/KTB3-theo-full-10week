package com.example.KTB_10WEEK.swagger.controller.post;

import com.example.KTB_10WEEK.security.principal.UserPrincipal;
import com.example.KTB_10WEEK.post.dto.request.CreatePostRequestDto;
import com.example.KTB_10WEEK.post.dto.request.UpdateMyPostRequestDto;
import com.example.KTB_10WEEK.post.dto.request.comment.CreateCommentRequestDto;
import com.example.KTB_10WEEK.post.dto.request.comment.UpdateCommentRequestDto;
import com.example.KTB_10WEEK.app.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Public Post", description = "게시글 API")
public interface PostApiDoc {
    // POST
    @GetMapping
    @Operation(summary = "전체 게시글 목록 조회", description = "페이지 단위 전체 게시글 목록을 조회합니다.")
    public ResponseEntity<BaseResponse> findPosts(@RequestParam(name = "page", defaultValue = "0") int page,
                                                        @RequestParam(name = "size", defaultValue = "10") int size);

    @Operation(summary = "게시글 조회", description = "게시글 PK를 통해 특정 게시글을 조회합니다.")
    public ResponseEntity<BaseResponse> findPostById(@PathVariable("postId")
                                                           @NotNull
                                                           @Positive Long postId);

    @Operation(summary = "게시글에 대한 전체 댓글 조회", description = "페이지 단위 게시글에 대한 전체 댓글을 조회합니다.")
    public ResponseEntity<BaseResponse> findCommentByPostId(@PathVariable("postId")
                                                            @NotNull
                                                            @Positive Long postId,
                                                            @RequestParam(name = "page", defaultValue = "0") int page);

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 등록합니다.")
    public ResponseEntity<BaseResponse> createPost(@Valid
                                                         @ModelAttribute CreatePostRequestDto request,
                                                         @AuthenticationPrincipal UserPrincipal principal);

    @Operation(summary = "조회 수 증가", description = "게시글 PK를 통해 조회 수를 1증가 합니다.")
    public ResponseEntity<BaseResponse> increaseViewCount(@PathVariable("postId")
                                                          @NotNull
                                                          @Positive Long postId);

    @Operation(summary = "게시글 좋아요 활성화", description = "회원 PK와 게시글 PK를 통해 종아요 기능을 활성화 합니다.")
    public ResponseEntity<BaseResponse> likePost(@PathVariable("postId")
                                                 @NotNull
                                                 @Positive Long postId,
                                                 @AuthenticationPrincipal UserPrincipal principal);

    @Operation(summary = "게시글 좋아요 비 활성화", description = "회원 PK와 게시글 PK를 통해 종아요 기능을 비 활성화 합니다.")
    public ResponseEntity<BaseResponse> cancelLikePost(@PathVariable("postId")
                                                       @NotNull
                                                       @Positive Long postId,
                                                       @AuthenticationPrincipal UserPrincipal principal);

    @Operation(summary = "게시글 수정", description = "게시글 PK를 통해 게시글의 내용을 수정합니다.")
    public ResponseEntity<BaseResponse> updatePost(@PathVariable("postId")
                                                         @NotNull
                                                         @Positive Long myPostId,
                                                         @Valid
                                                         @ModelAttribute UpdateMyPostRequestDto request,
                                                         @AuthenticationPrincipal UserPrincipal principal);


    @Operation(summary = "게시글 삭제", description = "게시글 PK를 통해 특정 게시글을 삭제합니다.")
    public ResponseEntity<BaseResponse> deletePostById(@PathVariable("postId")
                                                       @NotNull
                                                       @Positive Long postId,
                                                       @AuthenticationPrincipal UserPrincipal principal);

    // COMMENT
    @Operation(summary = "댓글 등록", description = "게시글 PK를 통해 새로운 댓글을 등록합니다.")
    public ResponseEntity<BaseResponse> createComment(@PathVariable("postId")
                                                      @NotNull
                                                      @Positive Long postId,
                                                      @RequestBody
                                                      @Valid CreateCommentRequestDto request,
                                                      @AuthenticationPrincipal UserPrincipal principal);

    @Operation(summary = "댓글 수정", description = "게시글 PK와 댓글의 PK를 조회하여 특정 댓글의 내용을 수정합니다.")
    public ResponseEntity<BaseResponse> updateComment(@PathVariable("postId")
                                                      @NotNull
                                                      @Positive Long postId,
                                                      @PathVariable("commentId")
                                                      @NotNull
                                                      @Positive Long commentId,
                                                      @RequestBody
                                                      @Valid UpdateCommentRequestDto request,
                                                      @AuthenticationPrincipal UserPrincipal principal);

    @Operation(summary = "댓글 삭제", description = "게시글 PK와 댓글 PK를 통해 특정 댓글을 삭제합니다.")
    public ResponseEntity<BaseResponse> deleteCommentById(@PathVariable("postId")
                                                          @Positive
                                                          @NotNull Long postId,
                                                          @PathVariable("commentId")
                                                          @NotNull
                                                          @Positive Long commentId,
                                                          @AuthenticationPrincipal UserPrincipal principal);
}
