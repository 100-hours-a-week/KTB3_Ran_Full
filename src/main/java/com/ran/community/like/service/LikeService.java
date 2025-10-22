package com.ran.community.like.service;

import com.ran.community.like.dto.response.LikeCountDto;
import com.ran.community.like.dto.response.LikeDataDto;
import com.ran.community.like.entity.Like;
import com.ran.community.like.repository.InMemoryLikeRepository;
import com.ran.community.like.repository.LikeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LikeService {
    private final LikeRepository likeRepository;
    private static final Logger logger = LoggerFactory.getLogger(LikeService.class);

    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }
    //좋아요 Id로 찾기
    public Like getLike(long likeId){
        return likeRepository.getLike(likeId).orElseThrow(()->new IllegalArgumentException("좋아요가 존재하지 않습니다."));
    }

    //특정 게시물에서 좋아요 생성
    //중복 안됨
    public LikeDataDto addLike(long userId, long postId){
        //리스트 안에 postId안에 userId가 있을 경우 생성안됨
        List<Like> likeList = likeRepository.getLikeListByPostId(postId).orElseThrow(()->new IllegalArgumentException("좋아요가 없습니다."));
        for(Like like:likeList){
            //중복 불가
            if(like.getUserId()==userId){
                //이미 있을 경우 좋아요 삭제
                throw new IllegalArgumentException("이미 좋아요를 누르셨습니다. ");
            };
        }
        //좋아요를 누르지 않았을 경우
        Like like = likeRepository.addLike(userId,postId);
        logger.info(like.toString());
        return new LikeDataDto(like);
    }

    //특정 게시물에서 좋아요 삭제
    public LikeDataDto removeLike(long userId, long postId){
        Like like = likeRepository.deleteLike(userId,postId).orElseThrow(()->new IllegalArgumentException("좋아요가 없습니다."));
        return new LikeDataDto(like);
    }

    //특정 게시물에서 좋아요 갯수 조회
    public int getCountLike(long postId){
        int likeCount = likeRepository.getLikeListByPostId(postId).orElseThrow(()->new IllegalArgumentException("좋아요가 없습니다.")).size();
        logger.info("좋아요 갯수:{}",likeCount);
        return likeCount;
    }

    //특정 게시물에서 좋아요 갯수 조회 dto
    public LikeCountDto getLikeCountDto(long postId){
        return new LikeCountDto(postId,getCountLike(postId));
    }


}
