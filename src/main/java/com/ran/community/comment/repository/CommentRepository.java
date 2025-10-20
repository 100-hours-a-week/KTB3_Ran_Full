package com.ran.community.comment.repository;

import com.ran.community.comment.entity.Comment;
import com.ran.community.comment.dto.request.CommentInputDto;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class CommentRepository {
    private AtomicLong index;
    private Map<Long, Comment> Comments = new ConcurrentHashMap<>();


    //식별자로 찾기
    public Optional<Comment> getComment(long commentId){
        return Optional.of(Comments.get(commentId));
    }

    //댓글 생성
    public Comment commentCreate(long userId, long postId, CommentInputDto commentInputDto){
        Comment comment = new Comment();
        comment.setCommentId(index.getAndIncrement());
        comment.setAuthorId(userId);
        comment.setPostId(postId);
        comment.setPostTime();
        comment.setContent(commentInputDto.getComment());
        Comments.put(comment.getCommentId(), comment);
        return comment;
    }

    //특정 게시물의 댓글 리스트 가져오기
    public Optional<List<Comment>> commentListByPostId(long postId){
        return Optional.of(Comments.values().stream().filter(it -> it.getPostId().equals(postId)).toList());
    }

    //댓글 수정
    public Optional<Comment> commentUpdate(Comment comment, CommentInputDto commentInputDto) {
        comment.setContent(commentInputDto.getComment());
        return Optional.of(comment);
    }

    //댓글 삭제
    public Optional<Comment> commentDelete(Comment comment) {
        return Optional.of(Comments.remove(comment.getCommentId()));
    }

}
