package com.ran.community.post.entity;

import com.ran.community.global.entity.AuditingEntity;
import com.ran.community.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Post extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private long id;

    @Column(length = 30,name = "title", nullable = false)
    private String title;

    @Lob //TEXT 타입
    @Column(name = "content",nullable = false)
    private String content;

    @Column(length = 255, name = "img_url", nullable = true)
    private String img;

    @Column(name = "comment_count", nullable = false)
    private int commentCount = 0;//초기값이 0이기 때문
    
    @Column(name = "like_count", nullable = false)
    private int likeCount = 0;

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post(String title, String content, String img, User user) {
        this.title = title;
        this.content = content;
        this.img = img;
        this.user = user;
    }
}
