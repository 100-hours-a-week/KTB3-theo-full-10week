package com.example.KTB_10WEEK.post.controller;

import com.example.KTB_10WEEK.app.security.principal.UserPrincipal;
import com.example.KTB_10WEEK.post.dto.request.CancelLikePostRequestDto;
import com.example.KTB_10WEEK.post.dto.request.LikePostRequestDto;
import com.example.KTB_10WEEK.app.swagger.controller.post.PostApiDoc;
import com.example.KTB_10WEEK.post.dto.request.CreatePostRequestDto;
import com.example.KTB_10WEEK.post.dto.request.comment.UpdateCommentRequestDto;
import com.example.KTB_10WEEK.app.response.BaseResponse;
import com.example.KTB_10WEEK.post.service.PostService;
import com.example.KTB_10WEEK.post.dto.request.UpdateMyPostRequestDto;
import com.example.KTB_10WEEK.post.dto.request.comment.CreateCommentRequestDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PublicPostController implements PostApiDoc {

    private final PostService publicPostService;

    public PublicPostController(PostService publicPostService) {
        this.publicPostService = publicPostService;
    }

    /**
     * Get Mapping
     **/
    @GetMapping // 전체 게시글 목록 조회
    public ResponseEntity<BaseResponse> findPublicPosts(@RequestParam(name = "page", defaultValue = "0") int page,
                                                        @RequestParam(name = "size", defaultValue = "10") int size) {
        BaseResponse response = publicPostService.findPosts(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{postId}") // 게시글 조회
    public ResponseEntity<BaseResponse> findPublicPostById(@PathVariable("postId")
                                                           @NotNull @Positive Long postId) {
        BaseResponse response = publicPostService.findPostById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{postId}/comment") // 게시글에 대한 댓글 조회
    public ResponseEntity<BaseResponse> findCommentByPostId(@PathVariable("postId")
                                                            @NotNull @Positive Long postId,
                                                            @RequestParam(name = "page", defaultValue = "0") int page) {
        BaseResponse response = publicPostService.findCommentByPostId(postId, page);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Post Mapping
     **/
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // 게시글 생성
    public ResponseEntity<BaseResponse> createPublicPost(@Valid
                                                         @ModelAttribute CreatePostRequestDto request,
                                                         @AuthenticationPrincipal UserPrincipal principal) {
        BaseResponse response = publicPostService.createPost(request, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/{postId}/hit") // 조회 수 증가
    public ResponseEntity<BaseResponse> increaseViewCount(@PathVariable("postId")
                                                          @NotNull
                                                          @Positive Long postId) {
        BaseResponse response = publicPostService.increaseViewCount(postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{postId}/like") // 좋아요 활성화
    public ResponseEntity<BaseResponse> likePost(@PathVariable("postId")
                                                 @NotNull
                                                 @Positive Long postId,
                                                 @AuthenticationPrincipal UserPrincipal principal) {
        BaseResponse response = publicPostService.likePost(postId, principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{postId}/like/cancel") // 좋아요 비 활성화
    public ResponseEntity<BaseResponse> cancelLikePost(@PathVariable("postId")
                                                       @NotNull
                                                       @Positive Long postId,
                                                       @AuthenticationPrincipal UserPrincipal principal) {
        BaseResponse response = publicPostService.cancelLikePost(postId, principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/{postId}/comment") // 댓글 등록
    public ResponseEntity<BaseResponse> createComment(@PathVariable("postId")
                                                      @NotNull
                                                      @Positive Long postId,
                                                      @RequestBody
                                                      @Valid CreateCommentRequestDto request,
                                                      @AuthenticationPrincipal UserPrincipal principal) {
        BaseResponse response = publicPostService.createComment(postId, request, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Patch Mapping
     **/
    @PatchMapping("/{postId}/comment/{commentId}") // 댓글 수정
    public ResponseEntity<BaseResponse> updateComment(@PathVariable("postId")
                                                      @NotNull
                                                      @Positive Long postId,
                                                      @PathVariable("commentId")
                                                      @NotNull
                                                      @Positive Long commentId,
                                                      @RequestBody
                                                      @Valid UpdateCommentRequestDto request,
                                                      @AuthenticationPrincipal UserPrincipal principal) {
        BaseResponse response = publicPostService.updateCommentById(postId, commentId, request, principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{postId}") // 나의 게시글 수정
    public ResponseEntity<BaseResponse> updatePublicPost(@PathVariable("postId")
                                                         @NotNull
                                                         @Positive Long myPostId,
                                                         @Valid
                                                         @ModelAttribute UpdateMyPostRequestDto request,
                                                         @AuthenticationPrincipal UserPrincipal principal) {
        BaseResponse response = publicPostService.updateMyPost(myPostId, request, principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Delete Mapping
     **/
    @DeleteMapping("/{postId}") // 게시글 삭제
    public ResponseEntity<BaseResponse> deletePostById(@PathVariable("postId")
                                                       @NotNull
                                                       @Positive Long postId,
                                                       @AuthenticationPrincipal UserPrincipal principal) {
        BaseResponse response = publicPostService.deletePostById(postId, principal);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}/comment/{commentId}") // 댓글 삭제
    public ResponseEntity<BaseResponse> deleteCommentById(@PathVariable("postId")
                                                          @Positive
                                                          @NotNull Long postId,
                                                          @PathVariable("commentId")
                                                          @NotNull
                                                          @Positive Long commentId,
                                                          @AuthenticationPrincipal UserPrincipal principal) {
        BaseResponse response = publicPostService.deleteCommentById(postId, commentId, principal);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
