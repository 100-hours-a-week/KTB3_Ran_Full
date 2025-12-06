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

    public CommentDataDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.authorId = comment.getUser().getId(); // ← 빠진 부분 추가
        this.author = comment.getUser().getUsername();
        this.created_at = comment.getCreated_at();
    }


}
