package com.ran.community.comment.repository;

import com.ran.community.comment.dto.request.CommentInputDto;
import com.ran.community.comment.entity.Comment;
import com.ran.community.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //특정 postId의 댓글 가져오기
    List<Comment> findByPost_Id(long postId);
//    //식별자로 찾기
//    Optional<Comment> getComment(long commentId);
//
//    //댓글 생성
//    Comment commentCreate(long id, long id, CommentInputDto commentInputDto);
//
//    //특정 게시물의 댓글 리스트 가져오기
//    Optional<List<Comment>> commentListByPostId(long id);
//
//    //댓글 수정
//    Optional<Comment> commentUpdate(Comment comment, CommentInputDto commentInputDto);
//
//    //댓글 삭제
//    Optional<Comment> commentDelete(Comment comment);
}
