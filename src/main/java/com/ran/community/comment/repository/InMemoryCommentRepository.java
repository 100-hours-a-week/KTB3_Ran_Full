package com.ran.community.comment.repository;

import com.ran.community.comment.entity.Comment;
import com.ran.community.comment.dto.request.CommentInputDto;
import com.ran.community.global.CommentIdGenerator;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryCommentRepository  implements CommentRepository {
    private final Map<Long, Comment> Comments = new ConcurrentHashMap<>();

    @Override
    public Optional<Comment> getComment(long commentId){
        return Optional.of(Comments.get(commentId));
    }

    @Override
    public Comment commentCreate(long userId, long postId, CommentInputDto commentInputDto){
        Comment comment = new Comment(CommentIdGenerator.getInstance().nextId(),commentInputDto.getComment(),userId,postId);
        Comments.put(comment.getCommentId(), comment);
        return comment;
    }

    @Override
    public Optional<List<Comment>> commentListByPostId(long postId){
        return Optional.of(Comments.values().stream().filter(it -> it.getPostId().equals(postId)).toList());
    }

    @Override
    public Optional<Comment> commentUpdate(Comment comment, CommentInputDto commentInputDto) {
        Comment commentUpdate = new Comment(comment.getCommentId(),commentInputDto.getComment(),comment.getAuthorId(),comment.getPostId());
        return Optional.of(commentUpdate);
    }

    @Override
    public Optional<Comment> commentDelete(Comment comment) {
        return Optional.of(Comments.remove(comment.getCommentId()));
    }

}
