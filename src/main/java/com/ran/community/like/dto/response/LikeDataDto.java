package com.ran.community.like.dto.response;

import com.ran.community.like.entity.Like;

public class LikeDataDto {
    private long likeId;
    private long userId;
    private long postId;

    public LikeDataDto(Like like) {
        this.likeId = like.getLikeId();
        this.userId = like.getUserId();
        this.postId = like.getPostId();

    }

}
