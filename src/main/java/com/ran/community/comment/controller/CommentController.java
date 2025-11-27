package com.ran.community.comment.controller;

import com.ran.community.comment.dto.response.CommentDataDto;
 import com.ran.community.comment.dto.request.CommentInputDto;
import com.ran.community.comment.service.CommentService;
import com.ran.community.global.ApiResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponse<CommentDataDto>> commentCreate(@PathVariable Long postId, @RequestBody CommentInputDto commentInputDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        CommentDataDto commentDataDTO = commentService.commentCreate(email,postId,commentInputDto);
        return ApiResponse.created(commentDataDTO,"comment_created_success");
    }

    //특정 게시글에서 댓글 조회
    @GetMapping()
    public ResponseEntity<ApiResponse<List<CommentDataDto>>> commentList(@PathVariable Long postId) {
        List<CommentDataDto> commentList = commentService.commentListByPostId(postId);
        return ApiResponse.success(commentList, "List of comments_success");
    }

    //댓글 수정
    @PatchMapping("/{commentId}")
    public ResponseEntity<?> commentUpdate(@PathVariable Long postId,@PathVariable Long commentId, @RequestBody CommentInputDto commentInputDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        CommentDataDto commentDataDTO = commentService.commentUpdate(email,postId,commentId,commentInputDto);
        return ApiResponse.success(commentDataDTO,"comment_updated_success");
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> commentDelete(@PathVariable Long postId,@PathVariable Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        CommentDataDto commentDataDTO = commentService.commentDelete(email,postId,commentId);
        return ApiResponse.success(commentDataDTO,"comment_deleted_success");
    }
}
