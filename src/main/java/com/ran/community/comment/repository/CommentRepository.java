package com.ran.community.comment.repository;

import com.ran.community.comment.dto.request.CommentInputDto;
import com.ran.community.comment.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    //식별자로 찾기
    Optional<Comment> getComment(long commentId);

    //댓글 생성
    Comment commentCreate(long userId, long postId, CommentInputDto commentInputDto);

    //특정 게시물의 댓글 리스트 가져오기
    Optional<List<Comment>> commentListByPostId(long postId);

    //댓글 수정
    Optional<Comment> commentUpdate(Comment comment, CommentInputDto commentInputDto);

    //댓글 삭제
    Optional<Comment> commentDelete(Comment comment);
}
