package com.ran.community.post.entity;

import com.ran.community.comment.entity.Comment;
import com.ran.community.global.entity.AuditingEntity;
import com.ran.community.like.entity.PostLike;
import com.ran.community.post.dto.request.PostUpdatedFormDto;
import com.ran.community.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;


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
    private String imgUrl;

    @Column(name = "comment_count", nullable = false)
    private int commentCount = 0;//초기값이 0이기 때문
    
    @Column(name = "like_count", nullable = false)
    private int likeCount = 0;

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    //양방향
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //단방향
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @BatchSize(size = 50)
    private List<Comment> comments = new ArrayList<>();



    public Post(String title, String content, String imgUrl, User user) {
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.user = user;
    }

    //보통 수정할땐 다같이 없데이트를 하니까 한꺼번에 올리는게 낫지 않나?
    public void updatePost(PostUpdatedFormDto post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.imgUrl = post.getImgUrl();
    }


    //좋아요 증가
    public void increaseLikeCount() {
        this.likeCount++;
    }

    //좋아요 감소
    public void decreaseLikeCount() {
        this.likeCount--;
    }

    //댓글 수 증가
    public void increaseCommentCount() {
        this.commentCount++;
    }
    //댓글 수 감소
    public void decreaseCommentCount() {
        this.commentCount--;
    }

    //조회수 증가
    public void increaseViewCount() {
        this.viewCount++;
    }




}
