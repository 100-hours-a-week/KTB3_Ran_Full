package com.ran.community.like.dto;

import com.ran.community.post.dto.PostDto;
import com.ran.community.user.entity.User;

import java.time.LocalDateTime;

public class LikeDto {
    private long likeId;
    private long userId;
    private long postId;
    private LocalDateTime likeTime;

    public LikeDto() {}
    public LikeDto(long likeId, User user, PostDto post) {
        this.likeId = likeId;
        this.userId = user.getUserId();
        this.postId = post.getPostId();
        this.likeTime = LocalDateTime.now();
    }

    public long getLikeId() {
        return likeId;
    }

    public void setLikeId(long likeId) {
        this.likeId = likeId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public LocalDateTime getLikeTime() {
        return likeTime;
    }

}
