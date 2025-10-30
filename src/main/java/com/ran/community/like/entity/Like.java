package com.ran.community.like.entity;

import com.ran.community.post.entity.Post;
import com.ran.community.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public long getUserId() {
        return userId;
    }

    public long getPostId() {
        return postId;
    }

    public LocalDateTime getLikeTime() {
        return likeTime;
    }

}
