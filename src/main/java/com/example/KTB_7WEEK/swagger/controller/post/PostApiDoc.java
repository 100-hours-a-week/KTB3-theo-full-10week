package com.example.KTB_7WEEK.swagger.controller.post;

import com.example.KTB_7WEEK.post.dto.request.CreatePostRequestDto;
import com.example.KTB_7WEEK.post.dto.request.LikePostReqeustDto;
import com.example.KTB_7WEEK.post.dto.request.UpdateMyPostRequestDto;
import com.example.KTB_7WEEK.post.dto.request.comment.CreateCommentRequestDto;
import com.example.KTB_7WEEK.post.dto.request.comment.UpdateCommentRequestDto;
import com.example.KTB_7WEEK.app.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Public Post", description = "게시글 API")
public interface PostApiDoc {
    @Operation(summary = "전체 게시글 목록 조회", description = "페이지 단위 전체 게시글 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "게시글 목록 조회 성공(다음 페이지 존재)", value = """
                            {
                                "message": "Posts Load Success",
                                "data": {
                                    "totalPages": 2,
                                    "totalCount": 20,
                                    "postPerPage": 10,
                                    "hasNext": true,
                                    "posts": [
                                        {
                                            "id": 2,
                                            "authorId": 0,
                                            "title": "제목",
                                            "article": "본문 내용",
                                            "articleImage": null,
                                            "category": "COMMUNITY",
                                            "hit": 0,
                                            "like": 0,
                                            "createdAt": "2025-10-12 20:50:18",
                                            "updatedAt": "2025-10-12 20:50:18",
                                            "deleted": false
                                        }
                                    ],
                                    "pageIndex": 1
                                },
                                "timestamp": "2025-10-12 20:55:22"
                            }
                            """),
                    @ExampleObject(name = "게시글 목록 조회 성공(다음 페이지 존재하지 않는 경우)", value = """
                            {
                                "message": "Posts Load Success",
                                "data": {
                                    "totalPages": 1,
                                    "totalCount": 1,
                                    "postPerPage": 10,
                                    "hasNext": false,
                                    "posts": [],
                                    "pageIndex": 100
                                },
                                "timestamp": "2025-10-12 22:11:16"
                            }
                                    """)
            })),
            @ApiResponse(responseCode = "401", description = "로그인 인증이 필요합니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "로그인 인증 필요", value = """
                            {
                                "code": 401,
                                "status": "UNAUTHORIZED",
                                "message": "로그인 인증이 필요합니다.",
                                "path": "/post"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> findPublicPosts(@RequestParam(name = "page", defaultValue = "0") int page);

    @Operation(summary = "게시글 조회", description = "게시글 PK를 통해 특정 게시글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "게시글 상세 조회 성공", value = """
                            {
                                "message": "Post Info Load Success",
                                "data": {
                                    "id": 2,
                                    "authorId": 0,
                                    "title": "제목",
                                    "article": "본문 내용",
                                    "articleImage": null,
                                    "category": "COMMUNITY",
                                    "hit": 0,
                                    "like": 0,
                                    "createdAt": "2025-10-12 20:50:18",
                                    "updatedAt": "2025-10-12 20:50:18",
                                    "deleted": false
                                },
                                "timestamp": "2025-10-12 21:01:05"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없습니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "게시글을 찾을 수 없음", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "게시글을 찾을 수 없습니다.",
                                "path": "/post/1"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> findPublicPostById(@PathVariable("postId")
                                                           @NotNull
                                                           @Positive Long postId);

    @Operation(summary = "게시글에 대한 전체 댓글 조회", description = "페이지 단위 게시글에 대한 전체 댓글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "전체 댓글 조회 성공(다음 페이지 존재)", value = """
                            {
                                "message": "Comments Load Success",
                                "data": {
                                    "postId": 2,
                                    "totalPages": 2,
                                    "total_count": 20,
                                    "pageSize": 10,
                                    "pageIndex": 1,
                                    "hasNext": true,
                                    "comments": [
                                        {
                                            "id": 2,
                                            "userId": 0,
                                            "content": "댓글",
                                            "created_at": "2025-10-12 22:08:00",
                                            "updated_at": "2025-10-12 22:08:00"
                                        },
                                        {
                                            "id": 3,
                                            "userId": 0,
                                            "content": "댓글",
                                            "created_at": "2025-10-12 22:08:01",
                                            "updated_at": "2025-10-12 22:08:01"
                                        },
                                                        
                                        (...)
                                                        
                                        {
                                            "id": 11,
                                            "userId": 0,
                                            "content": "댓글",
                                            "created_at": "2025-10-12 22:08:04",
                                            "updated_at": "2025-10-12 22:08:04"
                                        }
                                    ]
                                },
                                "timestamp": "2025-10-12 22:08:55"
                            }
                            """),
                    @ExampleObject(name = "전체 댓글 조회 성공(다음 페이지 없는 경우)", value = """
                            {
                                "message": "Comments Load Success",
                                "data": {
                                    "postId": 2,
                                    "totalPages": 2,
                                    "total_count": 20,
                                    "pageSize": 10,
                                    "pageIndex": 100,
                                    "hasNext": false,
                                    "comments": []
                                },
                                "timestamp": "2025-10-12 22:10:11"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없습니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "게시글을 찾을 수 없음", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "게시글을 찾을 수 없습니다.",
                                "path": "/post/100/comment"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> findCommentByPostId(@PathVariable("postId")
                                                            @NotNull
                                                            @Positive Long postId,
                                                            @RequestParam(name = "page", defaultValue = "0") int page);

    @Operation(summary = "게시글 생성", description = "새로운 게시글을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 생성 성공"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "게시글 생성 성공", value = """
                            {
                                "message": "Post Register Success",
                                "data": {
                                    "id": 1
                                },
                                "timestamp": "2025-10-12 20:52:23"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "게시글 제목 길이 제한", value = """
                            {
                                "code": 400,
                                "status": "BAD_REQUEST",
                                "message": "게시글 제목 최대 26자 입니다.",
                                "path": "/post"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> createPublicPost(@RequestBody
                                                         @Valid CreatePostRequestDto request);

    @Operation(summary = "조회 수 증가", description = "게시글 PK를 통해 조회 수를 1증가 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 수 증가 성공"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "조회수 증가 성공", value = """
                            {
                                "message": "Increase Hit Success",
                                "data": {
                                    "id": 1,
                                    "hit": 1,
                                    "updateAt": "2025-10-12 22:23:00"
                                },
                                "timestamp": "2025-10-12 22:23:00"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없습니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "게시글을 찾을 수 없음", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "게시글을 찾을 수 없습니다.",
                                "path": "/post/10/hit"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> increaseViewCount(@PathVariable("postId")
                                                    @NotNull
                                                    @Positive Long postId);

    @Operation(summary = "게시글 좋아요 활성", description = "회원 PK와 게시글 PK를 통해 종아요 기능을 활성화 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요 성공"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "좋아요 성공", value = """
                            {
                                "message": "Like Post Success",
                                "data": {
                                    "userId": 1,
                                    "postId": 1,
                                    "createdAt": "2025-10-12 22:26:17"
                                },
                                "timestamp": "2025-10-12 22:26:17"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없습니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "게시글을 찾을 수 없음", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "게시글을 찾을 수 없습니다.",
                                "path": "/post/100/like"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> likePost(@PathVariable("postId")
                                                 @NotNull
                                                 @Positive Long postId,
                                                 @RequestBody
                                                 @Valid LikePostReqeustDto request);

    @Operation(summary = "댓글 등록", description = "게시글 PK를 통해 새로운 댓글을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "댓글 생성 성공"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "댓글 등록 성공", value = """
                            {
                                "message": "Comment Create Success",
                                "data": {
                                    "id": 2,
                                    "content": "댓글 내용"
                                },
                                "timestamp": "2025-10-12 21:57:11"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "댓글 길이 제한", value = """
                            {
                                "code": 400,
                                "status": "BAD_REQUEST",
                                "message": "댓글은 1자 이상 입니다.",
                                "path": "/post/2/comment"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없습니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "게시글을 찾을 수 없음", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "게시글을 찾을 수 없습니다.",
                                "path": "/post/100/comment"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> createComment(@PathVariable("postId")
                                                      @NotNull
                                                      @Positive Long postId,
                                                      @RequestBody
                                                      @Valid CreateCommentRequestDto request);

    @Operation(summary = "댓글 수정", description = "게시글 PK와 댓글의 PK를 조회하여 특정 댓글의 내용을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "댓글 수정 성공", value = """
                            {
                                "message": "Comment Update Success",
                                "data": {
                                    "id": 2,
                                    "postId": 2,
                                    "userId": 0,
                                    "content": "수정된 댓글",
                                    "createAt": "2025-10-12 22:08:00",
                                    "updateAt": "2025-10-12 22:14:17"
                                },
                                "timestamp": "2025-10-12 22:14:17"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "댓글 길이 제한", value = """
                            {
                                "code": 400,
                                "status": "BAD_REQUEST",
                                "message": "댓글은 1자 이상 입니다.",
                                "path": "/post/100/comment/2"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없습니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "게시글을 찾을 수 없음", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "게시글을 찾을 수 없습니다.",
                                "path": "/post/100/comment/2"
                            }
                            """),
                    @ExampleObject(name = "댓글을 찾을 수 없음", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "댓글을 찾을 수 없습니다.",
                                "path": "/post/1/comment/100"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> updateComment(@PathVariable("postId")
                                                      @NotNull
                                                      @Positive Long postId,
                                                      @PathVariable("commentId")
                                                      @NotNull
                                                      @Positive Long commentId,
                                                      @RequestBody @Valid UpdateCommentRequestDto request);

    @Operation(summary = "게시글 수정", description = "게시글 PK를 통해 게시글의 내용을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 완료"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "게시글 수정 성공", value = """
                            {
                                "message": "My Post Update Success",
                                "data": {
                                    "id": 5,
                                    "title": "변경된",
                                    "article": "변경된 본문 내용",
                                    "articleImage": null,
                                    "category": "COUNSELING",
                                    "updateAt": "2025-10-12 21:05:11"
                                },
                                "timestamp": "2025-10-12 21:05:11"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "400", description = "유효성 검증 실패"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "게시글 제목 길이 제한", value = """
                            {
                                "code": 400,
                                "status": "BAD_REQUEST",
                                "message": "게시글 제목 최대 26자 입니다.",
                                "path": "/post/5"
                            }
                            """)
            })),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없습니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "게시글을 찾을 수 없음", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "게시글을 찾을 수 없습니다.",
                                "path": "/post/100"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> updatePublicPost(@PathVariable("postId")
                                                         @NotNull
                                                         @Positive Long myPostId,
                                                         @RequestBody
                                                         @Valid UpdateMyPostRequestDto request);


    @Operation(summary = "게시글 삭제", description = "게시글 PK를 통해 특정 게시글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "게시글 삭제 완료"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "게시글 삭제 성공", value = """
                            {}
                            """)
            })),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없습니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "게시글을 찾을 수 없음", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "게시글을 찾을 수 없습니다.",
                                "path": "/post/100"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> deletePostById(@PathVariable("postId")
                                                       @NotNull
                                                       @Positive Long postId);

    @Operation(summary = "댓글 삭제", description = "게시글 PK와 댓글 PK를 통해 특정 댓글을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "댓글 삭제 완료"
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "댓글 삭제 성공", value = """
                            {}
                            """)
            })),
            @ApiResponse(responseCode = "404", description = "리소스를 찾을 수 없습니다."
                    , content = @Content(mediaType = "application/json", examples = {
                    @ExampleObject(name = "게시글을 찾을 수 없음", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "게시글을 찾을 수 없습니다.",
                                "path": "/post/100/comment/1"
                            }
                            """),
                    @ExampleObject(name = "댓글을 찾을 수 없음", value = """
                            {
                                "code": 404,
                                "status": "NOT_FOUND",
                                "message": "댓글을 찾을 수 없습니다.",
                                "path": "/post/2/comment/100"
                            }
                            """)
            })),
    })
    public ResponseEntity<BaseResponse> deleteCommentById(@PathVariable("postId")
                                                          @Positive
                                                          @NotNull Long postId,
                                                          @PathVariable("commentId")
                                                          @NotNull
                                                          @Positive Long commentId);
}
