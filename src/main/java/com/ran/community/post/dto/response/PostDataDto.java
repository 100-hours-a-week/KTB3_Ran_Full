package com.ran.community.post.dto.response;


import com.ran.community.user.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;

public class PostDataDto {
    private long postId;
    private String postTitle;
    private String postContent;
    private long postAuthor;//User에게서 가져오기 FK //게시물 만든이
    private Optional<String> postImageUrl;
    private LocalDateTime postDate;

    public PostDataDto(long postId, String postTitle, String postContent, long postAuthor, LocalDateTime postDate, Optional<String> postImageUrl) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postAuthor = postAuthor;
        this.postDate = postDate;
        this.postImageUrl = postImageUrl;
    }

    public long getPostId() {
        return postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public long getPostAuthor() {
        return postAuthor;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public Optional<String> getPostImageUrl() {
        return postImageUrl;
    }
}
