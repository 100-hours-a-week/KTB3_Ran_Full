package com.ran.community.comment.service;

import com.ran.community.comment.dto.CommentDto;
import com.ran.community.comment.dto.CommentInputDto;
import com.ran.community.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    //식별자로 찾기
    public CommentDto getComment(long commentId){
        return commentRepository.getComment(commentId).orElseThrow(()->new IllegalArgumentException("댓글이 없습니다."));
    }

    //특정 게시글(postId)의 댓글 가져오기
    public List<CommentDto> commentListByPostId(Long postId) {
        return commentRepository.commentListByPostId(postId).orElseThrow(()->new IllegalArgumentException("댓글이 없습니다."));
    }

    //댓글 생성
    public CommentDto commentCreate(long userId, long postId, CommentInputDto commentInputDto) {
        return commentRepository.commentCreate(userId, postId, commentInputDto);
    }

    //댓글 수정
    public CommentDto commentUpdate(long userId,long postId,long commentId, CommentInputDto commentInputDto) {
        //내껏만 가능
        CommentDto comment = getComment(commentId);
        if(comment.getAuthorId()==userId && comment.getPostId()==postId){
            return commentRepository.commentUpdate(comment, commentInputDto).orElseThrow(()->new IllegalArgumentException("댓글이 존재하지 않습니다."));
        }else{
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }

    }

    //댓글 삭제
    public CommentDto commentDelete(long userId,long postId,long commentId) {
        CommentDto comment = getComment(commentId);
        if(comment.getAuthorId()==userId && comment.getPostId()==postId){
            return commentRepository.commentDelete(comment).orElseThrow(()->new IllegalArgumentException("댓글이 존재하지 않습니다."));
        }else{
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }
    }

}
