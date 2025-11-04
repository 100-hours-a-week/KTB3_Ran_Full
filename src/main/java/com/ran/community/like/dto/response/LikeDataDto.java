package com.ran.community.like.dto.response;

import com.ran.community.like.entity.PostLike;
import lombok.Getter;

@Getter
public class LikeDataDto {
    private long likeId;
    private long userId;
    private long postId;

    public LikeDataDto(PostLike like) {
        this.likeId = like.getId();
        this.userId = like.getUser().getId();
        this.postId = like.getPost().getId();

    }
}
