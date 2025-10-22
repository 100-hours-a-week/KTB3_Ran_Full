package com.ran.community.comment.controller;

import com.ran.community.comment.dto.response.CommentDataDTO;
import com.ran.community.comment.dto.request.CommentInputDto;
import com.ran.community.comment.service.CommentService;
import com.ran.community.global.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<CommentDataDTO>> commentCreate(HttpSession session, @PathVariable Long postId, @RequestBody CommentInputDto commentInputDto) {
        long userId = (long) session.getAttribute("userId");
        CommentDataDTO commentDataDTO = commentService.commentCreate(userId,postId,commentInputDto);
        return ApiResponse.created(commentDataDTO,"comment_created_success");
    }

    //특정 게시글에서 댓글 조회
    @GetMapping()
    public ResponseEntity<ApiResponse<List<CommentDataDTO>>> commentList(@PathVariable Long postId) {
        List<CommentDataDTO> commentList = commentService.commentListByPostId(postId);
        return ApiResponse.success(commentList, "List of comments_success");
    }

    //댓글 수정
    @PatchMapping("/{commentId}")
    public ResponseEntity<?> commentUpdate(HttpSession session, @PathVariable Long postId,@PathVariable Long commentId, @RequestBody CommentInputDto commentInputDto) {
        long userId = (long) session.getAttribute("userId");
        CommentDataDTO commentDataDTO = commentService.commentUpdate(userId,postId,commentId,commentInputDto);
        return ApiResponse.success(commentDataDTO,"comment_updated_success");
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> commentDelete(HttpSession session, @PathVariable Long postId,@PathVariable Long commentId) {
        long userId = (long) session.getAttribute("userId");
        CommentDataDTO commentDataDTO = commentService.commentDelete(userId,postId,commentId);
        return ApiResponse.success(commentDataDTO,"comment_deleted_success");
    }
}
