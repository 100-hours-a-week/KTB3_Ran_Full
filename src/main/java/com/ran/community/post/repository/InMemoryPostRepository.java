//package com.ran.community.post.repository;
//
//import com.ran.community.global.IdGeneratorFactory;
//import com.ran.community.global.PostIdGenerator;
//import com.ran.community.post.dto.response.PageDto;
//import com.ran.community.post.dto.response.PageMeta;
//import com.ran.community.post.dto.request.PostCreateFormDto;
//import com.ran.community.post.entity.Post;
//import com.ran.community.user.entity.User;
//import com.ran.community.user.repository.InMemoryUserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicLong;
//
//@Repository
//public abstract class InMemoryPostRepository implements PostRepository {
//    private final Map<Long, Post> Posts = new ConcurrentHashMap<>();
//    private final IdGeneratorFactory idGeneratorFactory;
//
//    @Autowired
//    public InMemoryPostRepository(IdGeneratorFactory idGeneratorFactory) {
//        this.idGeneratorFactory = idGeneratorFactory;
//    }
//
//
//    //게시글 생성
////    @Override
////    public Post postCreate(User user, PostCreateFormDto postCreateFormDto){
////        long id = idGeneratorFactory.nextId(Post.class);
////        Post post = new Post(id,postCreateFormDto.getTitle(),postCreateFormDto.getContent(),user,null);
////        Posts.put(post.getId(), post);
////        return post;
////    }
//
//    //특정 게시글 가져오기
//    @Override
//    public Optional<Post> getPost(long id){
//        return Optional.ofNullable(Posts.get(id));
//    }
//
//    //전체 리스트 생성
//    @Override
//    public Optional<List<Post>> totalPostList(){
//        return Optional.of(new ArrayList<>(Posts.values()));
//    }
//
//
//    //게시물 수정
////    @Override
////    public Optional<Post> updatePost(Post post, PostCreateFormDto postCreateFormDto){
////        deletePost(post.getId());//기존 객체 삭제
////        Post updatePost = new Post(post.getId(), postCreateFormDto.getTitle(), postCreateFormDto.getContent(),post.getUser(),postCreateFormDto.getImageUrl());
////        return Optional.of(updatePost);
////    }
//
//    //게시물 삭제
//    @Override
//    public Optional<Post> deletePost(long id){
//        return Optional.ofNullable(Posts.remove(id));
//    }
//
//}
