package com.ran.community.comment.service;

import com.ran.community.comment.dto.response.CommentDataDTO;
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

    //private
    //본인이 작성한 댓글만 상태를 변경할 수 있음.(본인확인)
    //해당 클래스 안에서만 유의미한 메서드이기 때문에 다른 클래스에서 검증로직을 무분별하게 사용할 수 있음.
    private void validationUser(long userId, long postId, Comment comment){
        if(comment.getAuthorId()!=userId || comment.getPostId()!=postId){
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }
    }

    //식별자로 찾기
    public Comment getComment(long commentId){
        return commentRepository.getComment(commentId).orElseThrow(()->new IllegalArgumentException("댓글이 없습니다."));
    }

    //특정 게시글(postId)의 댓글 가져오기
    public List<CommentDataDTO> commentListByPostId(Long postId) {
        List<Comment> comments = commentRepository.commentListByPostId(postId).orElseThrow(()->new IllegalArgumentException("댓글이 없습니다."));

        List<CommentDataDTO> cmtResponseDTO = comments.stream().map(comment -> new CommentDataDTO(comment.getCommentId(),comment.getContent(),comment.getAuthorId(),comment.getPostTime())).collect(Collectors.toList());
        logger.info(cmtResponseDTO.toString());
        return cmtResponseDTO;
    }

    //댓글 생성
    public CommentDataDTO commentCreate(long userId, long postId, CommentInputDto commentInputDto) {
        Comment cmt = commentRepository.commentCreate(userId, postId, commentInputDto);
        logger.info(cmt.toString());
        return new CommentDataDTO(cmt);
    }

    //댓글 수정
    public CommentDataDTO commentUpdate(long userId, long postId, long commentId, CommentInputDto commentInputDto) {
        //내껏만 가능
        Comment comment = getComment(commentId);
        validationUser(userId,postId,comment);//수정권한 확인

        Comment cmt = commentRepository.commentUpdate(comment, commentInputDto).orElseThrow(()->new IllegalArgumentException("댓글이 존재하지 않습니다."));
        logger.info(cmt.toString());
        return new CommentDataDTO(cmt);

    }

    //댓글 삭제
    public CommentDataDTO commentDelete(long userId, long postId, long commentId) {
        Comment comment = getComment(commentId);
        validationUser(userId,postId,comment); //수정권한 확인

        Comment cmt = commentRepository.commentDelete(comment).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        logger.info(cmt.toString());
        return new CommentDataDTO(cmt);
    }


}
