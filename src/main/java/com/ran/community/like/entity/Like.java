package com.ran.community.like.entity;

import com.ran.community.post.entity.Post;
import com.ran.community.user.entity.User;

import java.time.LocalDateTime;

public class Like {
    private long likeId;
    private long userId;
    private long postId;
    private LocalDateTime likeTime;

    public Like(long likeId, long userId, long postId) {
        this.likeId = likeId;
        this.userId = userId;
        this.postId = postId;
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
