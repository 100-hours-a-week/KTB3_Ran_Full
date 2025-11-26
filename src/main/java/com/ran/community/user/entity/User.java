package com.ran.community.user.entity;

import com.ran.community.comment.entity.Comment;
import com.ran.community.global.entity.AuditingEntity;
import com.ran.community.post.entity.Post;
import com.ran.community.user.dto.request.UserInfoUpdatedDto;
import com.ran.community.user.dto.request.UserPWUpdateDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
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

    @Column(length = 50, name = "email", nullable = false, unique = true)
    private String email;

    @Column(length = 10, name = "username", nullable = false)
    private String username;

    @Column(length = 255, name = "password", nullable = false)
    private String password;

    @Column(length = 255, name="refreshToken")
    private String refreshToken;

    //유저의 post
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    //유저의 user
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public void updatedUserInfo(UserInfoUpdatedDto userInfoUpdatedDto){
        this.username = userInfoUpdatedDto.getUsername();
    }

    public void updatedUserPassword(UserPWUpdateDto userPWUpdateDto){
        this.password = userPWUpdateDto.getPassword();
    }

    public void toRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
