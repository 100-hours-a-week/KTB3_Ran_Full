package com.ran.community.like.repository;

import com.ran.community.global.LikeIdGenerator;
import com.ran.community.like.entity.Like;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryLikeRepository  implements LikeRepository {
    private final Map<Long, Like> Likes = new ConcurrentHashMap<>();

    @Override
    public Optional<Like> getLike(long likeId){
        return Optional.ofNullable(Likes.get(likeId));
    }

    @Override
    public Like addLike(long userId, long postId){
        Like like = new Like();

        like.setLikeId(LikeIdGenerator.getInstance().nextId());
        like.setUserId(userId);
        like.setPostId(postId);
        Likes.put(like.getLikeId(), like);
        return like;
    }

    @Override
    public Optional<List<Like>> getLikeListByPostId(long postId){
        return Optional.of(Likes.values().stream().filter(it -> it.getPostId() == postId).toList());
    }


    @Override
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
