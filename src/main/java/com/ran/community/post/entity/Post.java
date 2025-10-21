package com.ran.community.post.entity;

import com.ran.community.user.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;

public class Post {
    private long postId;
    private String postTitle;
    private String postContent;
    private long postAuthor;//User에게서 가져오기 FK
    private Optional<String> postImageUrl;
    private LocalDateTime postDate;

    public Post(long postId, String postTitle, String postContent, long postAuthor, String postImageUrl) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postAuthor = postAuthor; //이게 맞나?
        this.postDate = LocalDateTime.now();
        this.postImageUrl = Optional.ofNullable(postImageUrl);
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
