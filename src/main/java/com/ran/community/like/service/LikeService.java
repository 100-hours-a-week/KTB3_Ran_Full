package com.ran.community.like.service;

import com.ran.community.global.LikeIdGenerator;
import com.ran.community.like.dto.response.LikeCountDto;
import com.ran.community.like.dto.response.LikeDataDto;
import com.ran.community.like.dto.response.LikeStateDto;
import com.ran.community.like.entity.PostLike;
//import com.ran.community.like.repository.InMemoryLikeRepository;
import com.ran.community.like.repository.LikeRepository;
import com.ran.community.post.entity.Post;
import com.ran.community.post.repository.PostRepository;
import com.ran.community.post.service.PostService;
import com.ran.community.user.entity.User;
import com.ran.community.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(LikeService.class);

    @Autowired
    public LikeService(LikeRepository likeRepository, PostRepository postRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    
    //좋아요 Id로 찾기
    private PostLike findById(long id){
        return likeRepository.findById(id).orElseThrow(()->new IllegalArgumentException("좋아요가 존재하지 않습니다."));
    }
    //식별자로 찾기
    private Post findByPostId(long postId) {
        return postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("게시물을 찾을 수 없습니다."));
    }

    //식별자로 유저 찾기
    private User findByUserId(long userId){
        return userRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("유저를 찾을 수 없습니다."));
    }

    //특정 게시물에서 좋아요 삭제
    @Transactional
    public LikeStateDto deleteByLike(long postId, long userId){
        Post post = findByPostId(postId);
        User user = findByUserId(userId);

        boolean exist = likeRepository.existsByUserAndPost(user,post);

        //좋아요 조회
        if(exist){//존재할 경우 like취소
            likeRepository.deleteByPostAndUser(post, user);//좋아요 로그 삭제
            post.decreaseLikeCount();//좋아요 감소
        }else{
            throw new IllegalArgumentException("좋아요를 누르지 않았습니다.");
        }

        return new LikeStateDto(false,post.getLikeCount());
    }


    //좋아요 생성
    @Transactional
    public LikeStateDto saveLike(long userId, long postId) {
        Post post = findByPostId(postId);//게시물의 객체 가져오기
        User user = findByUserId(userId);

        boolean exist = likeRepository.existsByUserAndPost(user,post);

        if(!exist){//존재하지 않을 경우
            //likeCount를 저장하기
            likeRepository.save(new PostLike(user, post));//좋아요 로그 생성
            post.increaseLikeCount();//좋아요 갯수 증가
        }else{//true
            throw new IllegalArgumentException("이미 좋아요를 눌렀습니다.");
        }

        return new LikeStateDto(true,post.getLikeCount());
    }

    //해당 postId에 유저가 좋아요를 눌렀는지 확인
    private boolean hasUserLikedPost(Long userId, Long postId) {
        Post post = findByPostId(postId);
        User user = findByUserId(userId);
        return likeRepository.existsByUserAndPost(user, post);
    }



}
