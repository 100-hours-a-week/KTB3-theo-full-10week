package com.example.KTB_10WEEK.post.service;

import com.example.KTB_10WEEK.app.aop.aspect.log.Loggable;
import com.example.KTB_10WEEK.security.principal.UserPrincipal;
import com.example.KTB_10WEEK.app.storage.ArticleImageStorage;
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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Loggable
    @PreAuthorize("hasAuthority('POST:CREATE')")
    public BaseResponse<CreatePostResponseDto> createPost(CreatePostRequestDto req, UserPrincipal principal) {
        User author = userRepository.getReferenceById(principal.getUserId());
        String articleImageUrl = articleImageStorage.saveArticleImage(req.getArticleImage());

        Post toSave = new Post.Builder()
                .author(author)
                .title(req.getTitle())
                .article(req.getArticle())
                .articleImage(articleImageUrl)
                .category(req.getCategory())
                .build();

        Post saved = postRepository.save(toSave);
        return new BaseResponse(ResponseMessage.POST_REGISTER_SUCCESS, CreatePostResponseDto.toDto(saved.getId()));
    }

    @Loggable
    public BaseResponse<FindPostsResponseDto> findPosts(int page, int size) {
        int contentSize = (size != PostPaginationPolicy.DEFAULT.size())
                ? size : PostPaginationPolicy.DEFAULT.size();
        Sort sort = PostPaginationPolicy.DEFAULT.sort();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        long totalElements = posts.getTotalElements();
        long currentPage = page;
        long totalPages = posts.getTotalPages();
        boolean hasNext = posts.hasNext();

        List<FindPostResponseDto> contents = posts.getContent()
                .stream()
                .map(FindPostResponseDto::toDto)
                .collect(Collectors.toCollection(ArrayList::new));

        return new BaseResponse(ResponseMessage.POSTS_LOAD_SUCCESS,
                FindPostsResponseDto.toDto(totalPages, totalElements, contentSize, currentPage, hasNext, contents));
    }

    @Loggable
    public BaseResponse<FindPostResponseDto> findPostById(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        return new BaseResponse(ResponseMessage.POST_INFO_LOAD_SUCCESS, FindPostResponseDto.toDto(post));
    }


    @Loggable
    @PreAuthorize("hasAuthority('POST:UPDATE')")
    public BaseResponse<UpdateMyPostResponseDto> updateMyPost(long postId, UpdateMyPostRequestDto req, UserPrincipal principal) {
        Long authorId = principal.getUserId();
        Post toUpdate = postRepository.findByIdAndAuthorId(postId, authorId).orElseThrow(PostNotFoundException::new);


        if (req.getTitle() != null && !req.getTitle().trim().isEmpty()) {
            toUpdate.updateTitle(req.getTitle());
        }

        if (req.getArticle() != null && !req.getArticle().trim().isEmpty()) {
            toUpdate.updateArticle(req.getArticle());
        }

        if (req.getArticleImage() != null && !req.getArticleImage().isEmpty()) {
            String oldFileName = req.getOldFileName();
            String newArticleImageUrl = articleImageStorage.updateArticleImage(req.getArticleImage(), oldFileName);
            toUpdate.updateArticleImage(newArticleImageUrl);
        }

        if (req.getCategory() != null) {
            toUpdate.updateCategory(req.getCategory());

        }
        toUpdate.updateNow();

        return new BaseResponse(ResponseMessage.MY_POST_UPDATE_SUCCESS, UpdateMyPostResponseDto.toDto(toUpdate));
    }


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

    @Loggable
    public BaseResponse<IncreaseHitResponseDto> increaseViewCount(Long postId) {
        postRepository.increaseViewCount(postId);
        Post updated = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException());

        return new BaseResponse(ResponseMessage.INCREASE_HIT_SUCCESS,
                IncreaseHitResponseDto.toDto(updated));
    }

    @Loggable
    public BaseResponse<LikePostResponseDto> likePost(Long postId, UserPrincipal principal) {
        Long userId = principal.getUserId();
        PostLikeId postLikeId = new PostLikeId(userId, postId);
        if (postLikeRepository.existsById(postLikeId)) {
            throw new AlreadyLikedPost();
        }

        User user = userRepository.getReferenceById(userId);
        Post post = postRepository.getReferenceById(postId);

        PostLike toSave = new PostLike(user, post);
        postLikeRepository.save(toSave);

        return new BaseResponse(ResponseMessage.LIKE_POST_SUCCESS,
                LikePostResponseDto.toDto(toSave));
    }

    public BaseResponse cancelLikePost(Long postId, UserPrincipal principal) {
        Long userId = principal.getUserId();
        PostLikeId postLikeId = new PostLikeId(userId, postId);
        postLikeRepository.deleteById(postLikeId);

        return new BaseResponse(ResponseMessage.CANCEL_LIKE_POST_SUCCESS,
                CancelLikePostResponseDto.toDto(postLikeId));
    }

    @Loggable
    @PreAuthorize("hasAuthority('COMMENT:CREATE')")
    public BaseResponse<CreateCommentResponseDto> createComment(long postId, CreateCommentRequestDto req, UserPrincipal principal) {
        Post findPost = postRepository.getReferenceById(postId);
        Long userId = principal.getUserId();
        User findUser = userRepository.getReferenceById(userId);

        Comment toSave = new Comment.Builder()
                .author(findUser)
                .post(findPost)
                .content(req.getContent())
                .build();

        Comment saved = commentRepository.save(toSave);

        return new BaseResponse(ResponseMessage.COMMENT_CREATE_SUCCESS,
                CreateCommentResponseDto.toDto(saved));
    }

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

    @Loggable
    public BaseResponse<FindCommentResponseDto> findCommentByCommentId(long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        return new BaseResponse(ResponseMessage.COMMENT_LOAD_SUCCESS, FindCommentResponseDto.toDto(comment));
    }

    @Loggable
    @PreAuthorize("hasAuthority('COMMENT:UPDATE')")
    public BaseResponse<UpdateCommentResponseDto> updateCommentById(long postId, long commentId, UpdateCommentRequestDto req, UserPrincipal principal) {
        Long authorId = principal.getUserId();
        Comment toUpdate = commentRepository.findByIdAndPostIdAndAuthorId(commentId, postId, authorId).orElseThrow(CommentNotFoundException::new);

        toUpdate.updateContent(req.getContent());
        toUpdate.updateNow();

        return new BaseResponse(ResponseMessage.COMMENT_UPDATE_SUCCESS,
                UpdateCommentResponseDto.toDto(toUpdate));
    }

    @Loggable
    @PreAuthorize("hasAuthority('COMMENT:DELETE')")
    public BaseResponse deleteCommentById(Long postId, Long commentId, UserPrincipal principal) {
        Long authorId = principal.getUserId();
        int row = commentRepository.deleteByIdAndPostIdAndAuthorId(commentId, postId, authorId);
        return new BaseResponse(ResponseMessage.COMMENT_DELETE_SUCCESS, new Comment());
    }

}
