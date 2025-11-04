package com.ran.community.post.service;

import com.ran.community.comment.entity.Comment;
import com.ran.community.like.entity.PostLike;
import com.ran.community.like.repository.LikeRepository;
import com.ran.community.like.service.LikeService;
import com.ran.community.post.dto.request.PostUpdatedFormDto;
import com.ran.community.post.dto.response.PageDto;
import com.ran.community.post.dto.request.PostCreateFormDto;
import com.ran.community.post.dto.response.PageMeta;
import com.ran.community.post.dto.response.PostDataDto;
import com.ran.community.post.dto.response.PostLikeCountDto;
import com.ran.community.post.entity.Post;
import com.ran.community.post.repository.PostRepository;
import com.ran.community.user.entity.User;
import com.ran.community.user.repository.UserRepository;
import com.ran.community.user.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    //생성하기 Creat
    @Transactional
    public PostDataDto save(long userId, PostCreateFormDto postCreateFormDto) {
        User user = userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다."));;
        Post post = new Post(postCreateFormDto.getTitle(),postCreateFormDto.getContent(),postCreateFormDto.getImgUrl(),user);
        return new PostDataDto(postRepository.save(post));
    }

    //특정 게시물 조회
    public PostDataDto findByPost(Long postId) {
        return new PostDataDto(findByPostId(postId));
    }

    @Transactional
    protected void validationUser(long userId, Post post) {
        if(post.getUser().getId()!=userId){
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }
    }

    //특정 게시물을 조회해서 data json으로 변형 //이게 무슨 코드지?
//    public PostDataDto postReadData(Long id) {
//        Post post = postRead(id);
//        return new PostDataDto(post.getPostId(),post.getPostTitle(),post.getPostContent(),post.getPostAuthor(),post.getPostDate(),post.getPostImageUrl());
//    }

    //페이지 offset 데이터
    public PageDto pageOffset(int page, int limit, int numOfContents, int numOfPages, List<Post> offsetNextList){
        PageMeta pageMeta = new PageMeta(numOfContents,limit,numOfPages,page);
        PageDto pageData = new PageDto(pageMeta,offsetNextList);
        logger.info(pageData.toString());
        return pageData;
    }


    //page : 현재 페이지 번호
    public PageDto postsRead(int page, int limit){
        List<Post> list = totalPostList();
        int numOfContents = totalPostList().size(); //전체 컨텐츠 수
        int numOfPages = (int)Math.ceil((double)numOfContents/limit); //전체 필요한 페이지 수
        int offset = (page-1)*limit; //앞에 있는 컨텐츠 수
        //offset 이후 limit 만큼 반환
        List<Post> offsetNextList = new ArrayList<>();

        //배열의 넘버가 offset+1부터
        //범위 초과 방지로 Math.min 사용
        for(int idx = offset;idx<Math.min(offset+limit,numOfContents);idx++){
            offsetNextList.add(list.get(idx));
        }

        return pageOffset(page,limit,numOfContents,numOfPages,offsetNextList);
    }


//    //특정 게시물의 좋아요 갯수 count하여 저장
//    @Transactional
//    private void countLike(long postId){
//        Post post  = findByPostId(postId);
//        int countLike = likeRepository.countByPost_Id(postId);
//        post.updatePostLike(countLike);//엔티티 안에 넣음 -> DB 안에 넣음
//    }



    //좋아요 갯수 조회
    @Transactional
    public PostLikeCountDto getLikeCount(long postId) {
        int countOfLike = postRepository.findLikeCountByPostId(postId);
        //좋아요 DTO 꺼냄
        return new PostLikeCountDto(postId,countOfLike); //좋아요 갯수 반환
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

    //전체 게시글 읽기
    public List<Post> totalPostList(){
        List<Post> postList = postRepository.findAll();
        if(postList.isEmpty()){
            throw new IllegalArgumentException("게시물을 찾을 수 없습니다.");
        }
        return postList;
    }

    //게시물 수정
    @Transactional
    public PostDataDto updatePost(long postId, long userId, PostUpdatedFormDto postUpdatedFormDto) {
        Post post = findByPostId(postId);
        validationUser(userId,post);

        post.updatePost(postUpdatedFormDto);
        return new PostDataDto(post);
    }

    //게시물 삭제
    //본인확인 먼저
    @Transactional
    public PostDataDto deletePost(long postId, long userId) {
        Post post = findByPostId(postId);
        validationUser(userId,post);

        postRepository.deleteById(postId);
        return new PostDataDto(post);
    }
}
