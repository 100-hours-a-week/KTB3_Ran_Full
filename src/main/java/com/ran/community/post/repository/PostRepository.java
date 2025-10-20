package com.ran.community.post.repository;

import com.ran.community.post.dto.response.PageDto;
import com.ran.community.post.dto.PageMeta;
import com.ran.community.post.dto.PostCreateFormDto;
import com.ran.community.post.dto.PostDto;
import com.ran.community.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private AtomicLong index;
    private Map<Long, PostDto> Posts = new ConcurrentHashMap<>();


    //게시글 생성
    public PostDto postCreate(User user, PostCreateFormDto postCreateFormDto){
        PostDto postDto = new PostDto();

        postDto.setPostId(index.getAndIncrement());
        postDto.setPostTitle(postCreateFormDto.getTitle());
        postDto.setPostContent(postCreateFormDto.getContent());
        postDto.setPostAuthor(user.getUserId());//userid 가져와야됨.
        postDto.setPostDate();
        Posts.put(postDto.getPostId(),postDto);
        return postDto;
    }

    //특정 게시글 가져오기
    public Optional<PostDto> getPost(long postId){
        return Optional.ofNullable(Posts.get(postId));
    }
//
//    //컨텐츠
//    public Optional<Map<Long, PostDto>> getPosts(){
//        //총 컨텐츠 수 구하기
//        return Optional.of(Posts);
//    }

    //전체 리스트 생성
    public Optional<List<PostDto>> totalPostList(){
        return Optional.of(new ArrayList<>(Posts.values()));
    }

    //페이지 offset 데이터
    public Optional<PageDto> pageOffset(int page, int limit,int numOfContents,int numOfPages,List<PostDto> offsetNextList){
        PageDto pageData = new PageDto();
        PageMeta pageMeta = new PageMeta();

        pageMeta.setPage(page);
        pageMeta.setLimit(limit);
        pageMeta.setNumOfContents(numOfContents);
        pageMeta.setNumOfPages(numOfPages);

        pageData.setOffsetPosts(offsetNextList);
        pageData.setPageMeta(pageMeta);
        return Optional.of(pageData);
    }


    //게시물 수정
    public Optional<PostDto> updatePost(PostDto postDto,PostCreateFormDto postCreateFormDto){
        postDto.setPostTitle(postCreateFormDto.getTitle());
        postDto.setPostContent(postCreateFormDto.getContent());
        postDto.setPostImageUrl(postCreateFormDto.getImageUrl());
        return Optional.of(postDto);
    }

    //게시물 삭제
    public Optional<PostDto> deletePost(long postId){
        return Optional.ofNullable(Posts.remove(postId));
    }

}
