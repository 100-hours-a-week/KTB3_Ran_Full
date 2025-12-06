package com.ran.community.post.service;

import com.ran.community.comment.dto.response.CommentCountDto;
import com.ran.community.comment.entity.Comment;
import com.ran.community.like.dto.response.LikeCountDto;
import com.ran.community.like.repository.LikeRepository;
import com.ran.community.post.dto.request.PostUpdatedFormDto;
import com.ran.community.post.dto.response.*;
import com.ran.community.post.dto.request.PostCreateFormDto;
import com.ran.community.post.entity.Post;
import com.ran.community.post.repository.PostRepository;
import com.ran.community.user.entity.User;
import com.ran.community.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final UserRepository userRepository;
    private PostRepository postRepository;
    private LikeRepository likeRepository;
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }


    //식별자로 찾기
    private Post findByPostId(Long id) {
        return postRepository.findById(id).orElseThrow(()->new IllegalArgumentException("게시물을 찾을 수 없습니다."));
    }

    //식별자로 유저 찾기
    private User findByUserId(long id){
        return userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }

    private User findByUserEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }


    //생성하기 Creat
    @Transactional
    public PostDataDto save(String email, PostCreateFormDto postCreateFormDto) {
        User user = findByUserEmail(email);
        Post post = new Post(postCreateFormDto.getTitle(),postCreateFormDto.getContent(),postCreateFormDto.getImgUrl(),user);
        return new PostDataDto(postRepository.save(post));
    }

//    //optional 예외처리
//    private Post findByPostIdWithComments(long postId) {
//        return postRepository.findByPostIdWithComments(postId);
//    }
    
    //게시물 가져올 때 getUser 하기 
    private Post findByPostIdWithAuthor(long postId) {
        return postRepository.findByPostIdWithAuthor(postId).orElseThrow(()->new IllegalArgumentException("게시글이 없습니다."));
    }

    //특정 게시물 상세 조회 + user + 댓글
    private Post findByPostIdWithCommentsAuthor(long postId) {
        return postRepository.findByPostIdWithCommentsAuthor(postId).orElseThrow(()->new IllegalArgumentException("게시글이 없습니다."));
    }

    //전체 게시물 조회
    private List<Post> findPostAll(){
        return postRepository.findAllWithAuthorOrderByCreatedAtDesc();
    }

    //특정 게시물 상세 조회 + 댓글 조회까지 //fetch join 개선
    @Transactional
    public PostDataDto findByPost(String email, Long postId) {
        Post post = findByPostIdWithCommentsAuthor(postId);
        User user = findByUserEmail(email);
        post.increaseViewCount(); //조회수 증가
        PostDataDto dto = new PostDataDto(post);
        // 로그인 한 유저가 있을 때만 좋아요 여부 체크
        if (email != null) {
            boolean liked = likeRepository.existsByUserAndPost(user, post);
            dto.doLiked(liked);
        }

        return dto;
    }

//    /// 부하테스트 오리진 : fetch join 없이
//    public PostDataDto findByPostOrigin(Long postId) {
//        Post postIdWithCommentsAuthor = postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("게시글이 없습니다."));
//        return new PostDataDto(postIdWithCommentsAuthor);
//    }

    @Transactional
    protected void validationUser(String email, Post post) {
        if(!Objects.equals(post.getUser().getEmail(), email)){
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }
    }

//    //전체 게시물 조회 fetch join 없이
//    @Transactional
//    public List<PostDto> findAllPostsTest() {
//        List<Post> postList= postRepository.findAll();
//        return postList.stream().map(post -> new PostDto(post)).collect(Collectors.toList());
//    }

    @Transactional
    public List<PostGetDto> findAllPosts(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        List<Post> posts = postRepository.findAllWithAuthorOrderByCreatedAtDesc();

        return posts.stream().map(post -> {
            PostGetDto dto = new PostGetDto(post);
            boolean liked = likeRepository.existsByPostIdAndUserId(post.getId(), user.getId());
            dto.doLiked(liked);
            return dto;
        }).collect(Collectors.toList());
    }





    //좋아요, 조회, 댓글 갯수 조회
    @Transactional
    public PostCountDto getCount(long postId) {
        return postRepository.findCountByPostId(postId);
    }

    //좋아요 갯수 조회
    @Transactional
    public LikeCountDto getLikeCount(long postId){
        return new LikeCountDto(postRepository.findLikeCountByPostId(postId));
    }

    //댓글 갯수 조회
    @Transactional
    public CommentCountDto getCommentCount(long postId){
        return new CommentCountDto(postRepository.findCommentCountByPostId(postId));
    }

    //조회수 갯수 조회
    @Transactional
    public ViewCountDto getViewCount(long postId){
        return new ViewCountDto(postRepository.findViewCountByPostId(postId));
    }


//    /// ///부하테스트
//    //좋아요 갯수 조회
//    @Transactional
//    public PostLikeCountDto findByIdLike(long postId) {
//        int countOfLike = postRepository.findById(postId).get().getLikeCount();//DB에서 좋아요 갯수 꺼냄
//        //좋아요 DTO 꺼냄
//        return new PostLikeCountDto(postId,countOfLike); //좋아요 갯수 반환
//    }
//
//    @Transactional
//    public PostLikeCountDto LikeCount(long postId) {
//        int countOfLike = postRepository.findLikeCountByPostId(postId);
//        //좋아요 DTO 꺼냄
//        return new PostLikeCountDto(postId,countOfLike); //좋아요 갯수 반환
//    }

    /// ///


    //게시물 수정
    @Transactional
    public PostDataDto updatePost(long postId, String email, PostUpdatedFormDto postUpdatedFormDto) {
        Post post = findByPostId(postId);
        validationUser(email,post);

        post.updatePost(postUpdatedFormDto);
        return new PostDataDto(post);
    }

    //게시물 삭제
    //본인확인 먼저
    @Transactional
    public PostDataDto deletePost(long postId, String email) {
        Post post = findByPostId(postId);
        validationUser(email,post);

        postRepository.deleteById(postId);
        return new PostDataDto(post);
    }
}
