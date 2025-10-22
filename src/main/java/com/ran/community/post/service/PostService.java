package com.ran.community.post.service;

import com.ran.community.post.dto.response.PageDto;
import com.ran.community.post.dto.request.PostCreateFormDto;
import com.ran.community.post.dto.response.PageMeta;
import com.ran.community.post.dto.response.PostDataDto;
import com.ran.community.post.entity.Post;
import com.ran.community.post.repository.PostRepository;
import com.ran.community.user.entity.User;
import com.ran.community.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //식별자로 찾기
    public Post getPost(Long postId) {
        return postRepository.getPost(postId).orElseThrow(()->new IllegalArgumentException("게시물을 찾을 수 없습니다."));
    }


    //생성하기
    public Post postCreate(User user, PostCreateFormDto postCreateFormDto) {
        return postRepository.postCreate(user,postCreateFormDto);
    }

    //특정 게시물 조회
    public Post postRead(Long postId) {
        return postRepository.getPost(postId).orElseThrow(()->new IllegalArgumentException("게시물을 찾을 수 없습니다."));
    }

    //특정 게시물을 조회해서 data json으로 변형
    public PostDataDto postReadData(Long postId) {
        Post post = postRead(postId);
        return new PostDataDto(post.getPostId(),post.getPostTitle(),post.getPostContent(),post.getPostAuthor(),post.getPostDate(),post.getPostImageUrl());
    }

    //페이지 offset 데이터
    public PageDto pageOffset(int page, int limit, int numOfContents, int numOfPages, List<Post> offsetNextList){
        PageMeta pageMeta = new PageMeta(numOfContents,limit,numOfPages,page);
        PageDto pageData = new PageDto(pageMeta,offsetNextList);
        logger.info(pageData.toString());
        return pageData;
    }


    //page : 현재 페이지 번호
    public PageDto postsRead(int page, int limit){
        List<Post> list = totalPostList();
        int numOfContents = totalPostList().size(); //전체 컨텐츠 수
        int numOfPages = (int)Math.ceil((double)numOfContents/limit); //전체 필요한 페이지 수
        int offset = (page-1)*limit; //앞에 있는 컨텐츠 수
        //offset 이후 limit 만큼 반환
        List<Post> offsetNextList = new ArrayList<>();

        //배열의 넘버가 offset+1부터
        //범위 초과 방지로 Math.min 사용
        for(int idx = offset;idx<Math.min(offset+limit,numOfContents);idx++){
            offsetNextList.add(list.get(idx));
        }

        return pageOffset(page,limit,numOfContents,numOfPages,offsetNextList);
    }

    //전체 게시글 읽기
    public List<Post> totalPostList(){
        return postRepository.totalPostList().orElseThrow(()->new IllegalArgumentException("게시물이 없습니다."));
    }

    //게시물 수정
    public Post updatePost(Long postId, PostCreateFormDto postCreateFormDto) {
        Post post = postRepository.updatePost(getPost(postId),postCreateFormDto).orElseThrow(()->new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        logger.info(post.toString());
        return post;
    }

    //게시물 삭제
    public Post deletePost(Long postId) {
        Post post = postRepository.deletePost(postId).orElseThrow(()->new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        logger.info(post.toString());
        return post;
    }
}
