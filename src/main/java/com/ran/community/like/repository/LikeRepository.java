package com.ran.community.like.repository;

import com.ran.community.like.entity.Like;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class LikeRepository {
    private AtomicLong index;
    private Map<Long, Like> Likes = new ConcurrentHashMap<>();

    //likeId로 좋아요 객체 가져오기
    public Optional<Like> getLike(long likeId){
        return Optional.ofNullable(Likes.get(likeId));
    }

    //특정 게시물의 좋아요 생성
    public Like addLike(long userId, long postId){
        Like like = new Like();

        like.setLikeId(index.getAndIncrement());
        like.setUserId(userId);
        like.setPostId(postId);
        Likes.put(like.getLikeId(), like);
        return like;
    }
    //특정 게시물의 좋아요 리스트 가져오기
    public Optional<List<Like>> getLikeListByPostId(long postId){
        return Optional.of(Likes.values().stream().filter(it -> it.getPostId() == postId).toList());
    }


    //특정 게시물의 좋아요 삭제하기
    public Optional<Like> deleteLike(long userId, long postId) {
        Like like = Likes.values().stream().filter(it->it.getUserId()==userId && it.getPostId()==postId).findFirst().orElse(null);
        // 삭제
        if (like.getUserId() != userId) {
            throw new IllegalArgumentException("본인이 누른 좋아요만 취소할 수 있습니다.");
        }

        Likes.remove(like.getLikeId());
        return Optional.of(like);
    }

}
