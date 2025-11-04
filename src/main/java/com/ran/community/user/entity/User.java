package com.ran.community.user.entity;

import com.ran.community.comment.entity.Comment;
import com.ran.community.global.entity.AuditingEntity;
import com.ran.community.post.entity.Post;
import com.ran.community.user.dto.request.UserUpdatedDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(length = 50, name = "email", nullable = false)
    private String email;

    @Column(length = 10, name = "username", nullable = false)
    private String username;

    @Column(length = 255, name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public void updatedUser(UserUpdatedDto userUpdatedDto){
        this.email = userUpdatedDto.getEmail();
        this.username = userUpdatedDto.getUsername();
        this.password = userUpdatedDto.getPassword();
    }

}
