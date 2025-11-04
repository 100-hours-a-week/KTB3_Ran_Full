package com.ran.community.like.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class LikeStateDto {
    boolean liked;
    int likesCount;

    public LikeStateDto(boolean liked, int likesCount) {
        this.liked = liked;
        this.likesCount = likesCount;
    }

}
