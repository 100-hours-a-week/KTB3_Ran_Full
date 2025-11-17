package com.ran.community.comment.dto.response;

import com.ran.community.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentDataDto {
    private long commentId;
    private String content;
    private long authorId;
    private String author;
    private LocalDateTime created_at;

    public CommentDataDto(long commentId, String content, long authorId) {
        this.commentId = commentId;
        this.content = content;
        this.authorId = authorId;
    }

    public CommentDataDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.author = comment.getUser().getUsername();
        this.created_at = comment.getCreated_at();
    }


}
