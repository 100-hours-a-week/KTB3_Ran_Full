package com.ran.community.post.entity;

import com.ran.community.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
