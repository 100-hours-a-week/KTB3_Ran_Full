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

    public Comment() {}
    public Comment(long commentId, String content, User user, Post post, LocalDateTime postTime) {
        this.commentId = commentId;
        this.content = content;
        this.authorId = user.getUserId();
        this.postId = post.getPostId();
        this.postTime = LocalDateTime.now();
    }

    public long getCommentId() {
        return commentId;
    }
    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public long getAuthorId() {
        return authorId;
    }
    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }
    public Long getPostId() {
        return postId;
    }
    public void setPostId(long postId) {
        this.postId = postId;
    }
    public LocalDateTime getPostTime() {
        return postTime;
    }
    public void setPostTime() {
        this.postTime = LocalDateTime.now();
    }
}
