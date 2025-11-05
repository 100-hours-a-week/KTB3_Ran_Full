package com.ran.community.comment.dto.response;

import lombok.Getter;

@Getter
public class CommentCountDto {
    private int commentCount;
    public CommentCountDto(int count) {
        this.commentCount = count;
    }

}
