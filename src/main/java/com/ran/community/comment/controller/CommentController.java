package com.ran.community.comment.controller;

import com.ran.community.comment.dto.CommentDto;
import com.ran.community.comment.dto.CommentInputDto;
import com.ran.community.comment.service.CommentService;
import com.ran.community.post.dto.PostDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/posts/{postId}/comments")
@RestController
public class CommentController {
    private CommentService commentService;
    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //댓글 생성
    @PostMapping()
    public ResponseEntity<?> commentCreate(HttpSession session, @PathVariable Long postId, @RequestBody CommentInputDto commentInputDto) {
        long userId = (long) session.getAttribute("userId");
        CommentDto comment = commentService.commentCreate(userId,postId,commentInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","comment_success","data",comment));
    }

    //특정 게시글에서 댓글 조회
    @GetMapping()
    public ResponseEntity<?> commentList(@PathVariable Long postId) {
        List<CommentDto> commentList = commentService.commentListByPostId(postId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","comment_list_success","data",commentList));
    }

    //댓글 수정
    @PatchMapping("/{commentId}")
    public ResponseEntity<?> commentUpdate(HttpSession session, @PathVariable Long postId,@PathVariable Long commentId, @RequestBody CommentInputDto commentInputDto) {
        long userId = (long) session.getAttribute("userId");
        CommentDto comment = commentService.commentUpdate(userId,postId,commentId,commentInputDto);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","comment_update_success","data",comment));
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> commentDelete(HttpSession session, @PathVariable Long postId,@PathVariable Long commentId) {
        long userId = (long) session.getAttribute("userId");
        CommentDto comment = commentService.commentDelete(userId,postId,commentId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","comment_delete_success","data",comment));
    }
}
