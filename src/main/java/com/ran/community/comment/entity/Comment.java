package com.ran.community.comment.entity;

import com.ran.community.post.entity.Post;
import com.ran.community.user.entity.User;

import java.time.LocalDateTime;

public class Comment {
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

    public long getCommentId() {
        return commentId;
    }
    public String getContent() {
        return content;
    }
    public long getAuthorId() {
        return authorId;
    }
    public Long getPostId() {
        return postId;
    }
    public LocalDateTime getPostTime() {
        return postTime;
    }
}
