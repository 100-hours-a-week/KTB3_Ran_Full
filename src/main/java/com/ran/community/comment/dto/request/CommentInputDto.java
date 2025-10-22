package com.ran.community.comment.dto.request;

public class CommentInputDto {
    private String content;

    public CommentInputDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
