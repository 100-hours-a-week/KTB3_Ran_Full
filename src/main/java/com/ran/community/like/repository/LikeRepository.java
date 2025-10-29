package com.ran.community.like.repository;

import com.ran.community.like.entity.Like;

import java.util.List;
import java.util.Optional;

public interface LikeRepository {

    //likeId로 좋아요 객체 가져오기
    Optional<Like> getLike(long likeId);

    //특정 게시물의 좋아요 생성
    Like addLike(long userId, long postId);

    //특정 게시물의 좋아요 리스트 가져오기
    Optional<List<Like>> getLikeListByPostId(long postId);

    //특정 게시물의 좋아요 삭제하기
    Optional<Like> deleteLike(long userId, long postId);
}
