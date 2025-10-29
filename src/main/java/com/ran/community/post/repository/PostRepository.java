package com.ran.community.post.repository;

import com.ran.community.post.dto.request.PostCreateFormDto;
import com.ran.community.post.entity.Post;
import com.ran.community.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    //게시글 생성
    Post postCreate(User user, PostCreateFormDto postCreateFormDto);

    //특정 게시글 가져오기
    Optional<Post> getPost(long postId);

    //전체 리스트 생성
    Optional<List<Post>> totalPostList();

    //게시물 수정
    Optional<Post> updatePost(Post post, PostCreateFormDto postCreateFormDto);

    //게시물 삭제
    Optional<Post> deletePost(long postId);
}
