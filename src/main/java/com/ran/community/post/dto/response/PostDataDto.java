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

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public long getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(long postAuthor) {
        this.postAuthor = postAuthor;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate() {
        this.postDate = LocalDateTime.now();
    }

    public Optional<String> getPostImageUrl() {
        return postImageUrl;
    }
    public void setPostImageUrl(Optional<String> postImageUrl) {
        this.postImageUrl = postImageUrl;
    }
}
