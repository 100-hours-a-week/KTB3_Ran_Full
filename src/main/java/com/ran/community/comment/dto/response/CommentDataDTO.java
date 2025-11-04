package com.ran.community.comment.dto.response;

import com.ran.community.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentDataDTO {
    private long commentId;
    private String content;
    private long authorId;
    private LocalDateTime created_at;

    public CommentDataDTO(long commentId, String content, long authorId) {
        this.commentId = commentId;
        this.content = content;
        this.authorId = authorId;
    }

    public CommentDataDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.authorId = comment.getUser().getId();
    }


}
