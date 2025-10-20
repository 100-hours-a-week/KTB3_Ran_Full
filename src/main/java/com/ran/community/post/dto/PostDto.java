package com.ran.community.post.dto;

import com.ran.community.user.entity.User;

import java.time.LocalDateTime;

public class PostDto {
    private long postId;
    private String postTitle;
    private String postContent;
    private long postAuthor;//User에게서 가져오기 FK
    private String postImageUrl;
    private LocalDateTime postDate;

    public PostDto() {}
    public PostDto(long postId, String postTitle, String postContent, User user, LocalDateTime postDate, String postImageUrl) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postAuthor = user.getUserId(); //이게 맞나?
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

    public String getPostImageUrl() {
        return postImageUrl;
    }
    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }
}
