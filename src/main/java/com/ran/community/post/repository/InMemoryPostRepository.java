package com.ran.community.post.repository;

import com.ran.community.post.dto.response.PageDto;
import com.ran.community.post.dto.response.PageMeta;
import com.ran.community.post.dto.request.PostCreateFormDto;
import com.ran.community.post.entity.Post;
import com.ran.community.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryPostRepository implements PostRepository {
    private AtomicLong index;
    private Map<Long, Post> Posts = new ConcurrentHashMap<>();


    //게시글 생성
    @Override
    public Post postCreate(User user, PostCreateFormDto postCreateFormDto){
        Post post = new Post();

        post.setPostId(index.getAndIncrement());
        post.setPostTitle(postCreateFormDto.getTitle());
        post.setPostContent(postCreateFormDto.getContent());
        post.setPostAuthor(user.getUserId());//userid 가져와야됨.
        post.setPostDate();
        Posts.put(post.getPostId(), post);
        return post;
    }

    //특정 게시글 가져오기
    @Override
    public Optional<Post> getPost(long postId){
        return Optional.ofNullable(Posts.get(postId));
    }
//
//    //컨텐츠
//    public Optional<Map<Long, PostDto>> getPosts(){
//        //총 컨텐츠 수 구하기
//        return Optional.of(Posts);
//    }

    //전체 리스트 생성
    @Override
    public Optional<List<Post>> totalPostList(){
        return Optional.of(new ArrayList<>(Posts.values()));
    }


    //게시물 수정
    @Override
    public Optional<Post> updatePost(Post post, PostCreateFormDto postCreateFormDto){
        post.setPostTitle(postCreateFormDto.getTitle());
        post.setPostContent(postCreateFormDto.getContent());
        post.setPostImageUrl(postCreateFormDto.getImageUrl());
        return Optional.of(post);
    }

    //게시물 삭제
    @Override
    public Optional<Post> deletePost(long postId){
        return Optional.ofNullable(Posts.remove(postId));
    }

}
