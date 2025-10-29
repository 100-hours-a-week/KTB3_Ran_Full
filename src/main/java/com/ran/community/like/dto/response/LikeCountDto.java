package com.ran.community.like.dto.response;

public class LikeCountDto {
    private int count;
    private long postId;

    public LikeCountDto(long postId,int count) {
        this.count = count;
        this.postId = postId;
    }
    public int getCount() {
        return count;
    }
    public long getPostId() {
        return postId;
    }
}
