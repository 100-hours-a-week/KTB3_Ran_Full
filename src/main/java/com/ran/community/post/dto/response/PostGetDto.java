package com.ran.community.post.dto.response;

import com.ran.community.post.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//전체보기 DTO
@Getter
public class PostGetDto {
    private long id;
    private String title;
    private String author;
    private String imgUrl;
    private String content;

    private int commentCount;
    private int likeCount;
    private int viewCount;

    private LocalDateTime  createdAt;



    public PostGetDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getUser().getUsername();
        this.imgUrl = post.getImgUrl();
        this.content = post.getContent();
        this.commentCount = post.getCommentCount();
        this.likeCount = post.getLikeCount();
        this.viewCount = post.getViewCount();
        this.createdAt = post.getCreated_at();
    }

}
