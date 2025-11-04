package com.ran.community.post.dto.response;


import com.ran.community.post.entity.Post;
import com.ran.community.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
public class PostDataDto {
    private long id;
    private String title;
    private String content;
    private String postAuthor;//User에게서 가져오기 FK //게시물 만든이
    private String imgUrl;

    public PostDataDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postAuthor = post.getUser().getUsername();
        this.imgUrl = post.getImgUrl();
    }
}
