package com.ran.community.like.dto.response;

import lombok.Getter;

//조회할 때
@Getter
public class LikeCountDto {
    private int likeCount;

    public LikeCountDto(int count) {
        this.likeCount = count;
    }
}
