package com.example.KTB_7WEEK.post.service;

import com.example.KTB_7WEEK.app.aop.aspect.log.Loggable;
import com.example.KTB_7WEEK.post.dto.request.UpdateMyPostRequestDto;
import com.example.KTB_7WEEK.post.dto.request.comment.CreateCommentRequestDto;
import com.example.KTB_7WEEK.post.dto.request.comment.UpdateCommentRequestDto;
import com.example.KTB_7WEEK.app.response.BaseResponse;
import com.example.KTB_7WEEK.app.response.ResponseMessage;
import com.example.KTB_7WEEK.post.dto.response.*;
import com.example.KTB_7WEEK.post.dto.response.comment.CreateCommentResponseDto;
import com.example.KTB_7WEEK.post.dto.response.comment.FindCommentResponseDto;
import com.example.KTB_7WEEK.post.dto.response.comment.FindCommentsResponseDto;
import com.example.KTB_7WEEK.post.dto.response.comment.UpdateCommentResponseDto;
import com.example.KTB_7WEEK.post.entity.Comment;
import com.example.KTB_7WEEK.post.exception.*;
import com.example.KTB_7WEEK.post.exception.comment.CommentNotFoundException;
import com.example.KTB_7WEEK.post.repository.CommentRepository;
import com.example.KTB_7WEEK.post.dto.request.CreatePostRequestDto;
import com.example.KTB_7WEEK.post.entity.Post;
import com.example.KTB_7WEEK.app.util.paginationPolicy.CommentPaginationPolicy;
import com.example.KTB_7WEEK.app.util.paginationPolicy.PostPaginationPolicy;
import com.example.KTB_7WEEK.post.repository.PostRepository;
import com.example.KTB_7WEEK.user.entity.User;
import com.example.KTB_7WEEK.user.exception.UserNotFoundException;
import com.example.KTB_7WEEK.user.repository.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private final CommentRepository commentRepository;

    public PostService(UserRepository userRepository,
                       PostRepository postRepository,
                       CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    /**
     * Post Service Business Logic & Convert <BaseResponse> Method
     **/
    // 게시글 생성
    @Loggable
    public BaseResponse<CreateCommentResponseDto> createPost(CreatePostRequestDto req) {
        User author = userRepository.findById(req.getAuthorId()).orElseThrow(() -> new UserNotFoundException());

        Post toSave = new Post.Builder()
                .author(author)
                .title(req.getTitle())
                .article(req.getArticle())
                .articleImage(req.getArticleImage())
                .category(req.getCategory())
                .build();

        toSave.postByAuthor(author);

        Post saved = postRepository.save(toSave);
        return new BaseResponse(ResponseMessage.POST_REGISTER_SUCCESS, CreatePostResponseDto.toDto(saved.getId()));
    }

    // 게시글 목록 조회
    @Loggable
    public BaseResponse<FindPostsResponseDto> findPosts(int page) {
        int size = PostPaginationPolicy.DEFAULT.size();
        Sort sort = PostPaginationPolicy.DEFAULT.sort();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        System.out.println(posts.getContent().size());
        long totalElements = posts.getTotalElements();
        long currentPage = page;
        long totalPages = posts.getTotalPages();
        boolean hasNext = posts.hasNext();


        List<FindPostResponseDto> contents = posts.getContent()
                .stream()
                .map((p) -> FindPostResponseDto.toDto(p))
                .collect(Collectors.toCollection(ArrayList::new));
        System.out.println(contents.size());
        return new BaseResponse(ResponseMessage.POSTS_LOAD_SUCCESS,
                FindPostsResponseDto.toDto(totalPages, totalElements, size, currentPage, hasNext, contents));
    }

    // 게시글 조회
    @Loggable
    public BaseResponse<FindPostResponseDto> findPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException());
        return new BaseResponse(ResponseMessage.POST_INFO_LOAD_SUCCESS, FindPostResponseDto.toDto(post));
    }

    // My Post 수정
    @Loggable
    public BaseResponse<UpdateMyPostResponseDto> updateMyPost(long id, UpdateMyPostRequestDto req) {
        Post toUpdate = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException());

        toUpdate.updateTitle(req.getTitle());
        toUpdate.updateArticle(req.getArticle());
        toUpdate.updateArticleImage(req.getArticleImage());
        toUpdate.updateCategory(req.getCategory());


        return new BaseResponse(ResponseMessage.MY_POST_UPDATE_SUCCESS,
                UpdateMyPostResponseDto.toDto(toUpdate));
    }

    // 게시글 삭제 By Id
    @Loggable
    public BaseResponse deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException());

        post.deleteByAuthor();
        postRepository.deleteById(id);

        return new BaseResponse(ResponseMessage.POST_DELETE_SUCCESS, new Post());
    }

    // 게시글 조회 수 증가
    @Loggable
    public BaseResponse<IncreaseHitResponseDto> increaseHit(long id) {
//        Post toUpdate = postRepository.findPostById(id).orElseThrow(() -> new PostNotFoundException());

        Post toUpdate = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException());
//        toUpdate.increaseHit();

        return new BaseResponse(ResponseMessage.INCREASE_HIT_SUCCESS,
                IncreaseHitResponseDto.toDto(toUpdate));
    }

    // 게시글 좋아요 수 증가
    @Loggable
    public BaseResponse<IncreaseLikeResponseDto> increaseLike(long id) {
        Post toUpdate = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException());
//        toUpdate.increaseLike();

        return new BaseResponse(ResponseMessage.INCREASE_LIKE_SUCCESS,
                IncreaseLikeResponseDto.toDto(toUpdate));
    }

    // 댓글 등록
    @Loggable
    public BaseResponse<CreateCommentResponseDto> createComment(long postId, CreateCommentRequestDto req) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }

        Post findPost = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException());
        Comment toSave = new Comment.Builder()
                .author(findPost.getAuthor())
                .post(findPost)
                .content(req.getContent())
                .build();

        toSave.onPost(findPost);
        toSave.writeBy(findPost.getAuthor());

        Comment saved = commentRepository.save(toSave);

        return new BaseResponse(ResponseMessage.COMMENT_CREATE_SUCCESS,
                CreateCommentResponseDto.toDto(saved));
    }

    // 댓글 조회 By Post Id
    @Loggable
    public BaseResponse<FindCommentsResponseDto> findCommentByPostId(long postId, int page) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }

        int size = CommentPaginationPolicy.DEFAULT.size();
        Sort sort = CommentPaginationPolicy.DEFAULT.sort();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Comment> comments = commentRepository.findAllByPostId(postId, pageable);

        long totalElements = comments.getTotalElements();
        long currentPage = page;
        long totalPages = comments.getTotalPages();
        boolean hasNext = comments.hasNext();
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
    public BaseResponse<FindCommentResponseDto> findCommentByCommentId(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException());
        return new BaseResponse(ResponseMessage.COMMENT_LOAD_SUCCESS, FindCommentResponseDto.toDto(comment));
    }

    // 댓글 수정 By Comment Id
    @Loggable
    public BaseResponse<UpdateCommentResponseDto> updateCommentById(long postId, long commentId, UpdateCommentRequestDto req) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }

        if (!commentRepository.existsById(commentId)) {
            throw new CommentNotFoundException();
        }

        Comment toUpdate = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException());
        toUpdate.updateContent(req.getContent());

        return new BaseResponse(ResponseMessage.COMMENT_UPDATE_SUCCESS,
                UpdateCommentResponseDto.toDto(toUpdate));
    }

    // 댓글 삭제
    @Loggable
    public BaseResponse deleteCommentById(long postId, long commentId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException());
        comment.removeInPost();
        comment.deleteByAuthor();
        commentRepository.deleteById(commentId);
        return new BaseResponse(ResponseMessage.COMMENT_DELETE_SUCCESS, new Comment());
    }

}
