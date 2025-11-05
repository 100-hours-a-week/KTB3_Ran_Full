package com.ran.community.post.dto.response;

import com.ran.community.post.entity.Post;
import lombok.Getter;

@Getter
public class PostLikeCountDto {
    private int likeCount;

    public PostLikeCountDto(long postId) {
        this.likeCount = likeCount;
    }
}
