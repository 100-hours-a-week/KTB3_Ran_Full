package com.ran.community.comment.dto.response;

import com.ran.community.comment.entity.Comment;

import java.time.LocalDateTime;

public class CommentDataDTO {
    private long commentId;
    private String content;
    private long authorId;
    private LocalDateTime postTime;

    public CommentDataDTO(long commentId, String content, long authorId, LocalDateTime postTime) {
        this.commentId = commentId;
        this.content = content;
        this.authorId = authorId;
        this.postTime = postTime;
    }

    public CommentDataDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.authorId = comment.getAuthorId();
        this.postTime = comment.getPostTime();
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
    public LocalDateTime getPostTime() {
        return postTime;
    }

}
