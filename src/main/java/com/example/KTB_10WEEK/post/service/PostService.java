package com.example.KTB_10WEEK.post.service;

import com.example.KTB_10WEEK.app.aop.aspect.log.Loggable;
import com.example.KTB_10WEEK.app.security.principal.UserPrincipal;
import com.example.KTB_10WEEK.app.security.role.AdminRole;
import com.example.KTB_10WEEK.app.storage.ArticleImageStorage;
import com.example.KTB_10WEEK.post.dto.request.CancelLikePostRequestDto;
import com.example.KTB_10WEEK.post.dto.request.LikePostRequestDto;
import com.example.KTB_10WEEK.post.dto.request.UpdateMyPostRequestDto;
import com.example.KTB_10WEEK.post.dto.request.comment.CreateCommentRequestDto;
import com.example.KTB_10WEEK.post.dto.request.comment.UpdateCommentRequestDto;
import com.example.KTB_10WEEK.app.response.BaseResponse;
import com.example.KTB_10WEEK.app.response.ResponseMessage;
import com.example.KTB_10WEEK.post.dto.response.*;
import com.example.KTB_10WEEK.post.dto.response.comment.CreateCommentResponseDto;
import com.example.KTB_10WEEK.post.dto.response.comment.FindCommentResponseDto;
import com.example.KTB_10WEEK.post.dto.response.comment.FindCommentsResponseDto;
import com.example.KTB_10WEEK.post.dto.response.comment.UpdateCommentResponseDto;
import com.example.KTB_10WEEK.post.entity.Comment;
import com.example.KTB_10WEEK.post.entity.PostLike;
import com.example.KTB_10WEEK.post.entity.PostLikeId;
import com.example.KTB_10WEEK.post.exception.*;
import com.example.KTB_10WEEK.post.exception.comment.CommentNotFoundException;
import com.example.KTB_10WEEK.post.repository.CommentRepository;
import com.example.KTB_10WEEK.post.dto.request.CreatePostRequestDto;
import com.example.KTB_10WEEK.post.entity.Post;
import com.example.KTB_10WEEK.app.util.paginationPolicy.CommentPaginationPolicy;
import com.example.KTB_10WEEK.app.util.paginationPolicy.PostPaginationPolicy;
import com.example.KTB_10WEEK.post.repository.PostLikeRepository;
import com.example.KTB_10WEEK.post.repository.PostRepository;
import com.example.KTB_10WEEK.user.entity.User;
import com.example.KTB_10WEEK.user.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final ArticleImageStorage articleImageStorage;

    public PostService(UserRepository userRepository,
                       PostRepository postRepository,
                       PostLikeRepository postLikeRepository,
                       CommentRepository commentRepository,
                       ArticleImageStorage articleImageStorage) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
        this.commentRepository = commentRepository;
        this.articleImageStorage = articleImageStorage;
    }

    /**
     * Post Service Business Logic & Convert <BaseResponse> Method
     **/
    // 게시글 생성
    @Loggable
    @PreAuthorize("hasAuthority('POST:CREATE')")
    public BaseResponse<CreatePostResponseDto> createPost(CreatePostRequestDto req, UserPrincipal principal) {
        User author = userRepository.getReferenceById(principal.getUserId()); // User Proxy
        String articleImageUrl = articleImageStorage.saveArticleImage(req.getArticleImage());

        Post toSave = new Post.Builder() // Post for Save
                .author(author)
                .title(req.getTitle())
                .article(req.getArticle())
                .articleImage(articleImageUrl)
                .category(req.getCategory())
                .build();

        Post saved = postRepository.save(toSave); // DB Save Post
        return new BaseResponse(ResponseMessage.POST_REGISTER_SUCCESS, CreatePostResponseDto.toDto(saved.getId()));
    }

    // 게시글 목록 조회
    @Loggable
    public BaseResponse<FindPostsResponseDto> findPosts(int page, int size) {
        int contentSize = (size != PostPaginationPolicy.DEFAULT.size())
                ? size : PostPaginationPolicy.DEFAULT.size();  // 페이지 내 컨텐츠 수 DEFAULT = 10;
        Sort sort = PostPaginationPolicy.DEFAULT.sort(); // 최신순
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> posts = postRepository.findAll(pageable); // get Page

        long totalElements = posts.getTotalElements(); // 컨텐츠 총 개수
        long currentPage = page; // 현재 페이지
        long totalPages = posts.getTotalPages(); // 전체 페이지 개수
        boolean hasNext = posts.hasNext(); // 다음 페이지 여부

        List<FindPostResponseDto> contents = posts.getContent()
                .stream()
                .map((p) -> FindPostResponseDto.toDto(p))
                .collect(Collectors.toCollection(ArrayList::new));

        return new BaseResponse(ResponseMessage.POSTS_LOAD_SUCCESS,
                FindPostsResponseDto.toDto(totalPages, totalElements, contentSize, currentPage, hasNext, contents));
    }

    // 게시글 조회
    @Loggable
    public BaseResponse<FindPostResponseDto> findPostById(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        return new BaseResponse(ResponseMessage.POST_INFO_LOAD_SUCCESS, FindPostResponseDto.toDto(post));
    }

    // My Post 수정
    @Loggable
    @PreAuthorize("hasAuthority('POST:UPDATE')")
    public BaseResponse<UpdateMyPostResponseDto> updateMyPost(long postId, UpdateMyPostRequestDto req, UserPrincipal principal) {
        Long authorId = principal.getUserId();
        Post toUpdate = postRepository.findByIdAndAuthorId(postId, authorId).orElseThrow(PostNotFoundException::new);

        String oldFileName = req.getOldFileName();
        String newArticleImageUrl = articleImageStorage.updateArticleImage(req.getArticleImage(), oldFileName);
        // 변경 감지
        toUpdate.updateTitle(req.getTitle());
        toUpdate.updateArticle(req.getArticle());
        toUpdate.updateArticleImage(newArticleImageUrl);
        toUpdate.updateCategory(req.getCategory());
        toUpdate.updateNow(); // 업데이트 필드 최신화

        return new BaseResponse(ResponseMessage.MY_POST_UPDATE_SUCCESS,
                UpdateMyPostResponseDto.toDto(toUpdate));
    }

    // 게시글 삭제 By Id
    @Loggable
    @PreAuthorize("hasAuthority('POST:DELETE')")
    public BaseResponse deletePostById(long postId, UserPrincipal principal) {
        Long authorId = principal.getUserId();
        Post post = postRepository.findByIdAndAuthorId(postId, authorId).orElseThrow(PostNotFoundException::new);
        String articleImage = post.getArticleImage();

        boolean isDelete = articleImageStorage.deleteArticleImage(articleImage);
        if (!isDelete) {
            throw new DeleteArticleImageFailException();
        }

        postRepository.deleteById(postId);

        return new BaseResponse(ResponseMessage.POST_DELETE_SUCCESS, new Post());
    }

    // 게시글 조회 수 증가
    @Loggable
    public BaseResponse<IncreaseHitResponseDto> increaseViewCount(Long postId) {
        postRepository.increaseViewCount(postId); // 조회 수 증가
        Post updated = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException());

        return new BaseResponse(ResponseMessage.INCREASE_HIT_SUCCESS,
                IncreaseHitResponseDto.toDto(updated));
    }

    // 게시글 좋아요
    @Loggable
    public BaseResponse<LikePostResponseDto> likePost(Long postId, UserPrincipal principal) {
        Long userId = principal.getUserId();
        PostLikeId postLikeId = new PostLikeId(userId, postId); // 복합키 생성
        if (postLikeRepository.existsById(postLikeId)) { // 이미 회원이 좋아요 활성화 했는지
            throw new AlreadyLikedPost();
        }

        User user = userRepository.getReferenceById(userId); // User Proxy
        Post post = postRepository.getReferenceById(postId); // Post Proxy

        PostLike toSave = new PostLike(user, post); // 좋아요 Entity 생성
        postLikeRepository.save(toSave); // DB Save 좋아요 entity

        return new BaseResponse(ResponseMessage.LIKE_POST_SUCCESS,
                LikePostResponseDto.toDto(toSave));
    }

    // 게시글 좋아요 비활성화
    public BaseResponse cancelLikePost(Long postId, UserPrincipal principal) {
        Long userId = principal.getUserId();
        PostLikeId postLikeId = new PostLikeId(userId, postId); // 조회용 복합키 생성
        postLikeRepository.deleteById(postLikeId);

        return new BaseResponse(ResponseMessage.CANCEL_LIKE_POST_SUCCESS,
                CancelLikePostResponseDto.toDto(postLikeId));
    }

    // 댓글 등록
    @Loggable
    @PreAuthorize("hasAuthority('COMMENT:CREATE')")
    public BaseResponse<CreateCommentResponseDto> createComment(long postId, CreateCommentRequestDto req, UserPrincipal principal) {
        Post findPost = postRepository.getReferenceById(postId); // Post Proxy
        Long userId = principal.getUserId();
        User findUser = userRepository.getReferenceById(userId); // User Proxy

        Comment toSave = new Comment.Builder()
                .author(findUser)
                .post(findPost)
                .content(req.getContent())
                .build();

        Comment saved = commentRepository.save(toSave);

        return new BaseResponse(ResponseMessage.COMMENT_CREATE_SUCCESS,
                CreateCommentResponseDto.toDto(saved));
    }

    // 댓글 조회 By Post Id
    @Loggable
    public BaseResponse<FindCommentsResponseDto> findCommentByPostId(long postId, int page) {
        if (!postRepository.existsById(postId)) { // 게시글 존재하지 않을 때
            throw new PostNotFoundException();
        }

        int size = CommentPaginationPolicy.DEFAULT.size(); // 페이지 내 컨텐츠 수 DEFAULT = 10;
        Sort sort = CommentPaginationPolicy.DEFAULT.sort(); // DEFAULT : 최신 순
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Comment> comments = commentRepository.findAllByPostId(postId, pageable);

        long totalElements = comments.getTotalElements(); // 전체 컨텐츠 개수
        long currentPage = page; // 현재 페이지
        long totalPages = comments.getTotalPages(); // 총 페이지 개수
        boolean hasNext = comments.hasNext(); // 다음 페이지 존재 여부

        List<FindCommentResponseDto> contents = comments.getContent()
                .stream()
                .map((c) -> FindCommentResponseDto.toDto(c))
                .collect(Collectors.toCollection(ArrayList::new));

        return new BaseResponse(ResponseMessage.COMMENTS_LOAD_SUCCESS,
                FindCommentsResponseDto.toDto(postId, totalPages, totalElements, size, currentPage, hasNext,
                        contents));
    }

    // 댓글 조회 By Comment Id
    @Loggable
    public BaseResponse<FindCommentResponseDto> findCommentByCommentId(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException());
        return new BaseResponse(ResponseMessage.COMMENT_LOAD_SUCCESS, FindCommentResponseDto.toDto(comment));
    }

    // 댓글 수정 By Comment Id
    @Loggable
    @PreAuthorize("hasAuthority('COMMENT:UPDATE')")
    public BaseResponse<UpdateCommentResponseDto> updateCommentById(long postId, long commentId, UpdateCommentRequestDto req, UserPrincipal principal) {
        Long authorId = principal.getUserId();
        Comment toUpdate = commentRepository.findByIdAndPostIdAndAuthorId(commentId, postId, authorId)
                .orElseThrow(() -> new CommentNotFoundException());
        // 변경 감지
        toUpdate.updateContent(req.getContent()); // 댓글 내용 수정
        toUpdate.updateNow(); // 업데이트 시간 최신화

        return new BaseResponse(ResponseMessage.COMMENT_UPDATE_SUCCESS,
                UpdateCommentResponseDto.toDto(toUpdate));
    }

    // 댓글 삭제
    @Loggable
    @PreAuthorize("hasAuthority('COMMENT:DELETE')")
    public BaseResponse deleteCommentById(Long postId, Long commentId, UserPrincipal principal) {
        Long authorId = principal.getUserId();
        int row = commentRepository.deleteByIdAndPostIdAndAuthorId(commentId, postId, authorId);
        return new BaseResponse(ResponseMessage.COMMENT_DELETE_SUCCESS, new Comment());
    }

}
