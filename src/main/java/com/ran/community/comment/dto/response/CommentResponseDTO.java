package com.ran.community.comment.dto.response;

import com.ran.community.comment.entity.Comment;

import java.time.LocalDateTime;

public class CommentResponseDTO {
    private long commentId;
    private String content;
    private long authorId;
    private LocalDateTime postTime;

    public CommentResponseDTO(Comment comment) {}
    public CommentResponseDTO(long commentId, String content, long authorId, LocalDateTime postTime) {
        this.commentId = commentId;
        this.content = content;
        this.authorId = authorId;
        this.postTime = postTime;
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
    public LocalDateTime getPostTime() {
        return postTime;
    }
    public void setPostTime(LocalDateTime postTime) {
        this.postTime = postTime;
    }

}
