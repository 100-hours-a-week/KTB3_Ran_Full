package com.ran.community.like.controller;

import com.ran.community.like.service.LikeService;
import com.ran.community.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
//    public ResponseEntity<?> like(@PathVariable Long id, HttpSession session) {
//        long id = (long) session.getAttribute("id");
//        LikeDto like = likeService.addLike(id,id);
//        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","like_create_success","data",like));
//    }
//
//    //특정 게시물의 좋아요 삭제
//    @DeleteMapping()
//    public ResponseEntity<?> unlike(@PathVariable Long id, HttpSession session) {
//        long id = (long) session.getAttribute("id");
//        LikeDto like = likeService.removeLike(id,id);
//        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","unlike_success","data",like));
//
//    }
//
//    //특정 게시물의 좋아요 조회
//    @GetMapping()
//    public ResponseEntity<?> getLikes(@PathVariable Long id) {
//        int likeCount = likeService.getCountLike(id);
//        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","like_read_success","data",likeCount));
//    }


}
