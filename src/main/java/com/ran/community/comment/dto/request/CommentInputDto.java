package com.ran.community.comment.dto.request;

public class CommentInputDto {
    private String content;

    public CommentInputDto() {}
    public CommentInputDto(String content) {

        this.content = content;
    }

    public String getComment() {
        return content;

    }
    public void setComment(String comment) {

        this.content = comment;
    }

}
