package com.todayseafood.api.post.controller;

import com.todayseafood.api.app.response.BaseResponse;
import com.todayseafood.api.post.dto.request.CreatePostRequestDto;
import com.todayseafood.api.post.dto.request.UpdateMyPostRequestDto;
import com.todayseafood.api.post.dto.request.comment.CreateCommentRequestDto;
import com.todayseafood.api.post.dto.request.comment.UpdateCommentRequestDto;
import com.todayseafood.api.post.service.PostService;
import com.todayseafood.api.security.principal.UserPrincipal;
import com.todayseafood.api.swagger.controller.post.PostApiDoc;
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
     * POST
     **/
    @GetMapping
    public ResponseEntity<BaseResponse> findPosts(@RequestParam(name = "page", defaultValue = "0") int page,
                                                  @RequestParam(name = "size", defaultValue = "10") int size) {
        BaseResponse response = publicPostService.findPosts(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<BaseResponse> findPostById(@PathVariable("postId")
                                                     @NotNull @Positive Long postId) {
        BaseResponse response = publicPostService.findPostById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{postId}/comment")
    public ResponseEntity<BaseResponse> findCommentByPostId(@PathVariable("postId")
                                                            @NotNull @Positive Long postId,
                                                            @RequestParam(name = "page", defaultValue = "0") int page) {
        BaseResponse response = publicPostService.findCommentByPostId(postId, page);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BaseResponse> createPost(@Valid
                                                   @ModelAttribute CreatePostRequestDto request,
                                                   @AuthenticationPrincipal UserPrincipal principal) {
        BaseResponse response = publicPostService.createPost(request, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/{postId}/hit")
    public ResponseEntity<BaseResponse> increaseViewCount(@PathVariable("postId")
                                                          @NotNull
                                                          @Positive Long postId) {
        BaseResponse response = publicPostService.increaseViewCount(postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<BaseResponse> likePost(@PathVariable("postId")
                                                 @NotNull
                                                 @Positive Long postId,
                                                 @AuthenticationPrincipal UserPrincipal principal) {
        BaseResponse response = publicPostService.likePost(postId, principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{postId}/like/cancel")
    public ResponseEntity<BaseResponse> cancelLikePost(@PathVariable("postId")
                                                       @NotNull
                                                       @Positive Long postId,
                                                       @AuthenticationPrincipal UserPrincipal principal) {
        BaseResponse response = publicPostService.cancelLikePost(postId, principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<BaseResponse> updatePost(@PathVariable("postId")
                                                   @NotNull
                                                   @Positive Long myPostId,
                                                   @Valid
                                                   @ModelAttribute UpdateMyPostRequestDto request,
                                                   @AuthenticationPrincipal UserPrincipal principal) {
        BaseResponse response = publicPostService.updateMyPost(myPostId, request, principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/{postId}")
    public ResponseEntity<BaseResponse> deletePostById(@PathVariable("postId")
                                                       @NotNull
                                                       @Positive Long postId,
                                                       @AuthenticationPrincipal UserPrincipal principal) {
        BaseResponse response = publicPostService.deletePostById(postId, principal);
        return ResponseEntity.noContent().build();
    }

    /**
     * COMMENT
     **/
    @PostMapping("/{postId}/comment")
    public ResponseEntity<BaseResponse> createComment(@PathVariable("postId")
                                                      @NotNull
                                                      @Positive Long postId,
                                                      @RequestBody
                                                      @Valid CreateCommentRequestDto request,
                                                      @AuthenticationPrincipal UserPrincipal principal) {
        BaseResponse response = publicPostService.createComment(postId, request, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{postId}/comment/{commentId}")
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


    @DeleteMapping("/{postId}/comment/{commentId}")
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
