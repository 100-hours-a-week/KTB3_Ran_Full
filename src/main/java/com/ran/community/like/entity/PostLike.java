package com.ran.community.like.entity;

import com.ran.community.post.entity.Post;
import com.ran.community.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long like_Id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private LocalDateTime created_at;

    public PostLike(long like_Id, User user, Post post) {
        this.like_Id = like_Id;
        this.user = user;
        this.post =  post;
        this.created_at = LocalDateTime.now();
    }

}
