package com.ran.community.comment.repository;

import com.ran.community.comment.dto.request.CommentInputDto;
import com.ran.community.comment.entity.Comment;
import com.ran.community.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //특정 postId의 댓글 + user
    @Query("""
    SELECT DISTINCT c 
    FROM Comment c 
    JOIN FETCH c.user 
    WHERE c.post.id = :postId 
    ORDER BY c.created_at DESC 
""")
    List<Comment> findByPost_Id(@Param("postId") long postId);

}
