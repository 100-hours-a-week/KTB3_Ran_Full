package com.ran.community.comment.service;

import com.ran.community.comment.dto.response.CommentResponseDTO;
import com.ran.community.comment.entity.Comment;
import com.ran.community.comment.dto.request.CommentInputDto;
import com.ran.community.comment.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    //식별자로 찾기
    public Comment getComment(long commentId){
        return commentRepository.getComment(commentId).orElseThrow(()->new IllegalArgumentException("댓글이 없습니다."));
    }

    //특정 게시글(postId)의 댓글 가져오기
    public List<CommentResponseDTO> commentListByPostId(Long postId) {
        List<Comment> comments = commentRepository.commentListByPostId(postId).orElseThrow(()->new IllegalArgumentException("댓글이 없습니다."));

        List<CommentResponseDTO> cmtResponseDTO = comments.stream().map(comment -> new CommentResponseDTO(comment.getCommentId(),comment.getContent(),comment.getAuthorId(),comment.getPostTime())).collect(Collectors.toList());
        logger.info(cmtResponseDTO.toString());
        return cmtResponseDTO;
    }

    //댓글 생성
    public Comment commentCreate(long userId, long postId, CommentInputDto commentInputDto) {
        Comment cmt = commentRepository.commentCreate(userId, postId, commentInputDto);
        logger.info(cmt.toString());
        return cmt;
    }

    //댓글 수정
    public Comment commentUpdate(long userId, long postId, long commentId, CommentInputDto commentInputDto) {
        //내껏만 가능
        Comment comment = getComment(commentId);
        if(comment.getAuthorId()==userId && comment.getPostId()==postId){
            Comment cmt = commentRepository.commentUpdate(comment, commentInputDto).orElseThrow(()->new IllegalArgumentException("댓글이 존재하지 않습니다."));
            logger.info(cmt.toString());
            return cmt;
        }else{
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }

    }

    //댓글 삭제
    public Comment commentDelete(long userId, long postId, long commentId) {
        Comment comment = getComment(commentId);
        if(comment.getAuthorId()==userId && comment.getPostId()==postId){
            return commentRepository.commentDelete(comment).orElseThrow(()->new IllegalArgumentException("댓글이 존재하지 않습니다."));
        }else{
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }
    }

}
