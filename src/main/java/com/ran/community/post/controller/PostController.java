package com.ran.community.post.controller;

import com.ran.community.global.ApiResponse;
import com.ran.community.like.dto.response.LikeCountDto;
import com.ran.community.like.dto.response.LikeDataDto;
import com.ran.community.like.dto.response.LikeStateDto;
import com.ran.community.like.entity.PostLike;
import com.ran.community.like.service.LikeService;
import com.ran.community.post.dto.request.PostUpdatedFormDto;
import com.ran.community.post.dto.response.PageDto;
import com.ran.community.post.dto.request.PostCreateFormDto;
import com.ran.community.post.dto.response.PostDataDto;
import com.ran.community.post.dto.response.PostLikeCountDto;
import com.ran.community.post.entity.Post;
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

    //게시물 생성 //
    @PostMapping()
    public ResponseEntity<ApiResponse<PostDataDto>> postCreate(@Valid @RequestBody PostCreateFormDto postCreateFormDto, HttpSession httpSession) {
        //userId의 userDto를 가져오기
        long userId = (long) httpSession.getAttribute("id");
        //로그인된 user의 객체도 함께 사용
        PostDataDto postDataDto= postService.save(userId,postCreateFormDto);
        return ApiResponse.created(postDataDto,"created_post");
    }

    //특정 게시물 조회 //
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDataDto>> postRead(@PathVariable Long postId){
        PostDataDto post = postService.findByPost(postId);
        return ApiResponse.success(post,"read_post");
    }

    //전체 게시물 조회 //
    @GetMapping()
    public ResponseEntity<ApiResponse<PageDto>> postsRead(HttpSession httpSession,@RequestParam int page, @RequestParam int limit){
        //userId가 있을 경우에만 확인
        long userId = ((long) httpSession.getAttribute("id"));
        PageDto pageData = postService.postsRead(page,limit);//페이지 네이션 되어있음.
        return ApiResponse.success(pageData,"read_all_post");
    }

    //게시물 수정 //
    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDataDto>> updatePost(HttpSession httpSession, @Valid @RequestBody PostUpdatedFormDto postUpdatedFormDto, @PathVariable Long postId){
        long userId = ((long) httpSession.getAttribute("id"));
        PostDataDto postDataDto = postService.updatePost(postId,userId,postUpdatedFormDto);
        return ApiResponse.success(postDataDto,"update_post");
    }

    //게시물 삭제 //
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDataDto>> deletePost(HttpSession httpSession,@PathVariable Long postId){
        long userId = ((long) httpSession.getAttribute("id"));
        PostDataDto postDataDto = postService.deletePost(postId,userId);
        return ApiResponse.success(postDataDto,"delete_post");
    }


    //좋아요를 분리해야되나?  ㅇㅇ 분리함.
    //특정 게시물의 좋아요 생성
    @PostMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<LikeStateDto>> like(@PathVariable Long postId, HttpSession session) {
        long userId = (long) session.getAttribute("id");
        LikeStateDto likeStateDto = likeService.saveLike(userId,postId);
        return ApiResponse.created(likeStateDto,"like");
    }

    //특정 게시물의 좋아요 삭제
    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<LikeStateDto>> unlike(@PathVariable Long postId, HttpSession session) {
        long userId = (long) session.getAttribute("id");
        LikeStateDto likeStateDto = likeService.deleteByLike(postId,userId);
        return ApiResponse.success(likeStateDto,"unlike");

    }

    //특정 게시물의 좋아요 조회
    @GetMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<PostLikeCountDto>> getLikes(@PathVariable Long postId) {
        PostLikeCountDto likeCountDto = postService.getLikeCount(postId);
        return ApiResponse.success(likeCountDto,"get_like");
    }
//    /// ///부하테스트
//    //1
//    @GetMapping("/{postId}/findByIdTest")
//    public ResponseEntity<ApiResponse<PostLikeCountDto>> findByIdTest(@PathVariable Long postId) {
//        PostLikeCountDto likeCountDto = postService.findByIdLike(postId);
//        return ApiResponse.success(likeCountDto,"get_like");
//    }
//    //2
//    @GetMapping("/{postId}/likeCountTest")
//    public ResponseEntity<ApiResponse<PostLikeCountDto>> likeCount(@PathVariable Long postId) {
//        PostLikeCountDto likeCountDto = postService.LikeCount(postId);
//        return ApiResponse.success(likeCountDto,"get_like");
//    }

}
