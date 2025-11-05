package com.ran.community.post.dto.response;

import com.ran.community.post.entity.Post;
import lombok.Getter;

//전체보기 DTO
@Getter
public class PostGetDto {
    private String title;
    private String content;
    private String author;
    private String imgUrl;

    private int commentCount;
    private int likeCount;
    private int viewCount;



    public PostGetDto(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getUser().getUsername();
        this.imgUrl = post.getImgUrl();
        this.commentCount = post.getCommentCount();
        this.likeCount = post.getLikeCount();
        this.viewCount = post.getViewCount();
    }
}
