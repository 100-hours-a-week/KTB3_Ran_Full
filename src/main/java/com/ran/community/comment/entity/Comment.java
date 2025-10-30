package com.ran.community.comment.entity;

import com.ran.community.post.entity.Post;
import com.ran.community.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;
    private String content;
    private long authorId;
    private long postId;
    private LocalDateTime postTime;

    public Comment(long commentId, String content, long authorId, long postId) {
        this.commentId = commentId;
        this.content = content;
        this.authorId = authorId;
        this.postId = postId;
        this.postTime = LocalDateTime.now();
    }
}
