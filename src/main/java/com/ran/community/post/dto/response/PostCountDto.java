package com.ran.community.post.dto.response;

import com.ran.community.comment.entity.Comment;
import com.ran.community.post.entity.Post;
import lombok.Getter;

@Getter
public class PostCountDto {
    private int CommentCount;
    private int LikeCount;
    private int ViewCount;

    public PostCountDto(int CommentCount, int LikeCount, int ViewCount) {
        this.CommentCount = CommentCount;
        this.LikeCount = LikeCount;
        this.ViewCount = ViewCount;
    }
}
