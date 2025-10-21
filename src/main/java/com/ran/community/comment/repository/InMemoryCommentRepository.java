package com.ran.community.comment.repository;

import com.ran.community.comment.entity.Comment;
import com.ran.community.comment.dto.request.CommentInputDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryCommentRepository  implements CommentRepository {
    private AtomicLong index;
    private final Map<Long, Comment> Comments = new ConcurrentHashMap<>();

    @Override
    public Optional<Comment> getComment(long commentId){
        return Optional.of(Comments.get(commentId));
    }

    @Override
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

    @Override
    public Optional<List<Comment>> commentListByPostId(long postId){
        return Optional.of(Comments.values().stream().filter(it -> it.getPostId().equals(postId)).toList());
    }

    @Override
    public Optional<Comment> commentUpdate(Comment comment, CommentInputDto commentInputDto) {
        comment.setContent(commentInputDto.getComment());
        return Optional.of(comment);
    }

    @Override
    public Optional<Comment> commentDelete(Comment comment) {
        return Optional.of(Comments.remove(comment.getCommentId()));
    }

}
