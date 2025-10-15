package com.ran.community.like.repository;

import com.ran.community.like.dto.LikeDto;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class LikeRepository {
    private long index;
    private Map<Long, LikeDto> Likes = new LinkedHashMap<>();

    public LikeRepository(){
        this.index = 0;
    }

    //likeId로 좋아요 객체 가져오기
    public Optional<LikeDto> getLike(long likeId){
        return Optional.ofNullable(Likes.get(likeId));
    }

    //특정 게시물의 좋아요 생성
    public LikeDto addLike(long userId, long postId){
        LikeDto likeDto = new LikeDto();

        likeDto.setLikeId(index);
        likeDto.setUserId(userId);
        likeDto.setPostId(postId);
        Likes.put(likeDto.getLikeId(), likeDto);
        index++;
        return likeDto;
    }
    //특정 게시물의 좋아요 리스트 가져오기
    public Optional<List<LikeDto>> getLikeListByPostId(long postId){
        return Optional.of(Likes.values().stream().filter(it -> it.getPostId() == postId).toList());
    }


    //특정 게시물의 좋아요 삭제하기
    public Optional<LikeDto> deleteLike(long userId, long postId) {
        LikeDto like = Likes.values().stream().filter(it->it.getUserId()==userId && it.getPostId()==postId).findFirst().orElse(null);
        // 삭제
        if (like.getUserId() != userId) {
            throw new IllegalArgumentException("본인이 누른 좋아요만 취소할 수 있습니다.");
        }

        Likes.remove(like.getLikeId());
        return Optional.of(like);
    }

}
