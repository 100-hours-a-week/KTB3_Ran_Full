package com.ran.community.like.controller;

import com.ran.community.like.dto.LikeDto;
import com.ran.community.like.service.LikeService;
import com.ran.community.user.dto.UserDto;
import com.ran.community.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping()
@RestController
public class LikeController {
    private final UserService userService;
    private LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService, UserService userService) {
        this.likeService = likeService;
        this.userService = userService;
    }
//
//    //특정 게시물의 좋아요 생성 및 삭제
//    @PostMapping()
//    public ResponseEntity<?> like(@PathVariable Long postId, HttpSession session) {
//        long userId = (long) session.getAttribute("userId");
//        LikeDto like = likeService.addLike(userId,postId);
//        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","like_create_success","data",like));
//    }
//
//    //특정 게시물의 좋아요 삭제
//    @DeleteMapping()
//    public ResponseEntity<?> unlike(@PathVariable Long postId, HttpSession session) {
//        long userId = (long) session.getAttribute("userId");
//        LikeDto like = likeService.removeLike(userId,postId);
//        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","unlike_success","data",like));
//
//    }
//
//    //특정 게시물의 좋아요 조회
//    @GetMapping()
//    public ResponseEntity<?> getLikes(@PathVariable Long postId) {
//        int likeCount = likeService.getCountLike(postId);
//        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","like_read_success","data",likeCount));
//    }


}
