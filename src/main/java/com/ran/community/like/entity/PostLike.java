package com.ran.community.like.entity;

import com.ran.community.global.entity.AuditingEntity;
import com.ran.community.post.entity.Post;
import com.ran.community.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id","post_id"})
})
public class PostLike extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //단방향 : like -> post를 탐색하지 않기 때문
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;


    public PostLike(User user, Post post) {
        this.user = user;
        this.post =  post;
    }

}
