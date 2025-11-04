package com.ran.community.post.dto.response;

import com.ran.community.post.entity.Post;
import lombok.Getter;

@Getter
public class PostLikeCountDto {
    private long postId;
    private int likeCount;

    public PostLikeCountDto(long postId, int likeCount) {
        this.postId = postId;
        this.likeCount = likeCount;
    }
}
