package com.ran.community.post.controller;

import com.ran.community.like.dto.LikeDto;
import com.ran.community.like.service.LikeService;
import com.ran.community.post.dto.PageDto;
import com.ran.community.post.dto.PostCreateFormDto;
import com.ran.community.post.dto.PostDto;
import com.ran.community.post.service.PostService;
import com.ran.community.user.entity.User;
import com.ran.community.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/posts")
@RestController
public class PostController {
    private final UserService userService;
    private final PostService postService;
    private final LikeService likeService;

    @Autowired
    public PostController(PostService postService, UserService userService, LikeService likeService) {
        this.postService = postService;
        this.userService = userService;
        this.likeService = likeService;
    }

    //게시물 생성
    @PostMapping()
    public ResponseEntity<?> postCreate(@Valid @RequestBody PostCreateFormDto postCreateFormDto, HttpSession httpSession) {
        //userId의 userDto를 가져오기
        userService.getUser((long) httpSession.getAttribute("userId"));
        //로그인된 user의 객체도 함께 사용
        postService.postCreate(user,postCreateFormDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","posting_success"));
    }

    //특정 게시물 조회
    @GetMapping("/{postId}")
    public ResponseEntity<?> postRead(@PathVariable Long postId){
        PostDto postDto = postService.postRead(postId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","posting_read_success","data",postDto));
    }

    //전체 게시물 조회
    //이거 반환값 확인 바람.
    @GetMapping()
    public ResponseEntity<?> postsRead(HttpSession httpSession,@RequestParam int page, @RequestParam int limit){
        //userId가 있을 경우에만 확인
        userService.getUser((long) httpSession.getAttribute("userId"));
        PageDto pageData = postService.postsRead(page,limit);//페이지 네이션 되어있음.
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","posts_read_success","data",pageData.getOffsetPosts(),"meta",pageData.getPageMeta()));

    }

    //게시물 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<?> updatePost(@Valid @RequestBody PostCreateFormDto postCreateFormDto, @PathVariable Long postId){
        PostDto postDto = postService.updatePost(postId,postCreateFormDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","posts_patch_success","data",postDto));
    }

    //게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId){
        PostDto postDto = postService.deletePost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","posts_delete_success","data",postDto));
    }

    //특정 게시물의 좋아요 생성 및 삭제
    @PostMapping("/{postId}/likes")
    public ResponseEntity<?> like(@PathVariable Long postId, HttpSession session) {
        long userId = (long) session.getAttribute("userId");
        LikeDto like = likeService.addLike(userId,postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","like_create_success","data",like));
    }

    //특정 게시물의 좋아요 삭제
    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<?> unlike(@PathVariable Long postId, HttpSession session) {
        long userId = (long) session.getAttribute("userId");
        LikeDto like = likeService.removeLike(userId,postId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","unlike_success","data",like));

    }

    //특정 게시물의 좋아요 조회
    @GetMapping("/{postId}/likes")
    public ResponseEntity<?> getLikes(@PathVariable Long postId) {
        int likeCount = likeService.getCountLike(postId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","like_read_success","data",likeCount));
    }

}
