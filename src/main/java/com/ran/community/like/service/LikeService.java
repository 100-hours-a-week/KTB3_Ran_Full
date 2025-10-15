package com.ran.community.like.service;

import com.ran.community.like.dto.LikeDto;
import com.ran.community.like.repository.LikeRepository;
import com.ran.community.post.dto.PostDto;
import com.ran.community.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LikeService {
    private LikeRepository likeRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }
    //좋아요 Id로 찾기
    public LikeDto getLike(long likeId){
        return likeRepository.getLike(likeId).orElseThrow(()->new IllegalArgumentException("좋아요가 존재하지 않습니다."));
    }

    //특정 게시물에서 좋아요 생성
    //중복 안됨
    public LikeDto addLike(long userId,long postId){
        //리스트 안에 postId안에 userId가 있을 경우 생성안됨
        List<LikeDto> likeList = likeRepository.getLikeListByPostId(postId).orElseThrow(()->new IllegalArgumentException("좋아요가 없습니다."));
        for(LikeDto like:likeList){
            //중복 불가
            if(like.getUserId()==userId){
                //이미 있을 경우 좋아요 삭제
                throw new IllegalArgumentException("이미 좋아요를 누르셨습니다. ");
            };
        }
        //좋아요를 누르지 않았을 경우
        LikeDto like = likeRepository.addLike(userId,postId);
        return like;
    }

    //특정 게시물에서 좋아요 삭제
    public LikeDto removeLike(long userId,long postId){
        return likeRepository.deleteLike(userId,postId).orElseThrow(()->new IllegalArgumentException("좋아요가 없습니다."));
    }

    //특정 게시물에서 좋아요 갯수 조회
    public int getCountLike(long postId){
        return likeRepository.getLikeListByPostId(postId).orElseThrow(()->new IllegalArgumentException("좋아요가 없습니다.")).size();
    }


}
