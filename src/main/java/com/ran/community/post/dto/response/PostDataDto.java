package com.ran.community.post.dto.response;


import com.ran.community.comment.dto.response.CommentDataDto;
import com.ran.community.post.entity.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class PostDataDto {
    private long id;
    private String title;
    private String content;
    private String postAuthor;
    private String imgUrl;
    private PostCountDto Count;//있는거 재활 가능하지루
    private List<CommentDataDto> comments ; //댓글 리스트

    public PostDataDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.postAuthor = post.getUser().getUsername();
        this.imgUrl = post.getImgUrl();
        this.Count = new PostCountDto(post.getCommentCount(), post.getLikeCount(), post.getViewCount());
        this.comments = post.getComments().stream().map(comment -> new CommentDataDto(comment)).toList();
    }
}
