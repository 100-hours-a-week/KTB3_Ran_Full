package com.ran.community.post.controller;

import com.ran.community.comment.dto.response.CommentCountDto;
import com.ran.community.global.ApiResponse;
import com.ran.community.like.dto.response.LikeCountDto;
import com.ran.community.like.dto.response.LikeStateDto;
import com.ran.community.like.service.LikeService;
import com.ran.community.post.dto.request.PostUpdatedFormDto;
import com.ran.community.post.dto.response.*;
import com.ran.community.post.dto.request.PostCreateFormDto;
import com.ran.community.post.service.PostService;
import com.ran.community.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //특정 게시물 조회 // fetch join O
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDataDto>> postRead(@PathVariable long postId){
        PostDataDto post = postService.findByPost(postId);
        return ApiResponse.success(post,"read_post");
    }

//    //특정 게시물 조회 /// 부하테스트 : fetch join X
//    @GetMapping("/{postId}/test")
//    public ResponseEntity<ApiResponse<PostDataDto>> postReadTest(@PathVariable Long postId){
//        PostDataDto post = postService.findByPostOrigin(postId);
//        return ApiResponse.success(post,"read_post");
//    }

    //전체 게시물 조회 //
    @GetMapping()
    public ResponseEntity<ApiResponse<List<PostGetDto>>> postsRead(){
        //userId가 있을 경우에만 확인
//        long userId = ((long) httpSession.getAttribute("id"));
        List<PostGetDto> postList = postService.findAllPosts();
        return ApiResponse.success(postList,"read_all_post");
    }

//    //전체 게시물 조회 // fetch join 없이 부하 테스트 용
//    @GetMapping("/test")
//    public ResponseEntity<ApiResponse<List<PostDto>>> postsReadTest(){
//        //userId가 있을 경우에만 확인
////        long userId = ((long) httpSession.getAttribute("id"));
//        List<PostDto> postList = postService.findAllPostsTest();
//        return ApiResponse.success(postList,"read_all_post");
//    }

    //게시물 수정 //
    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDataDto>> updatePost(HttpSession httpSession, @Valid @RequestBody PostUpdatedFormDto postUpdatedFormDto, @PathVariable long postId){
        long userId = ((long) httpSession.getAttribute("id"));
        PostDataDto postDataDto = postService.updatePost(postId,userId,postUpdatedFormDto);
        return ApiResponse.success(postDataDto,"update_post");
    }

    //게시물 삭제 //
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostDataDto>> deletePost(HttpSession httpSession,@PathVariable long postId){
        long userId = ((long) httpSession.getAttribute("id"));
        PostDataDto postDataDto = postService.deletePost(postId,userId);
        return ApiResponse.success(postDataDto,"delete_post");
    }


    //좋아요를 분리해야되나?  ㅇㅇ 분리함.
    //특정 게시물의 좋아요 생성
    @PostMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<LikeStateDto>> like(@PathVariable long postId, HttpSession session) {
        long userId = (long) session.getAttribute("id");
        LikeStateDto likeStateDto = likeService.saveLike(userId,postId);
        return ApiResponse.created(likeStateDto,"like");
    }

    //특정 게시물의 좋아요 삭제
    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<ApiResponse<LikeStateDto>> unlike(@PathVariable long postId, HttpSession session) {
        long userId = (long) session.getAttribute("id");
        LikeStateDto likeStateDto = likeService.deleteByLike(postId,userId);
        return ApiResponse.success(likeStateDto,"unlike");

    }

    //count 도메인 분리 해야하나?

    //특정 게시물의 좋아요 갯수, 댓글 갯수, 조회수 조회
    @GetMapping("/{postId}/counts")
    public ResponseEntity<ApiResponse<PostCountDto>> getCount(@PathVariable long postId) {
        PostCountDto Dto = postService.getCount(postId);
        return ApiResponse.success(Dto,"get_count");
    }

    //특정 게시물의 좋아요 갯수 조회
    @GetMapping("/{postId}/counts/likes")
    public ResponseEntity<ApiResponse<LikeCountDto>> getLikeCount(@PathVariable long postId) {
        LikeCountDto Dto = postService.getLikeCount(postId);
        return ApiResponse.success(Dto,"get_likeCount");
    }

    //특정 게시물의 댓글 갯수 조회
    @GetMapping("/{postId}/counts/comments")
    public ResponseEntity<ApiResponse<CommentCountDto>> getCommentCount(@PathVariable long postId) {
        CommentCountDto Dto = postService.getCommentCount(postId);
        return ApiResponse.success(Dto,"get_commentCount");
    }

    //특정 게시물의 조회수 갯수 조회
    @GetMapping("/{postId}/counts/views")
    public ResponseEntity<ApiResponse<ViewCountDto>> getViewCount(@PathVariable long postId) {
        ViewCountDto Dto = postService.getViewCount(postId);
        return ApiResponse.success(Dto,"get_viewCount");
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
