package com.ran.community.like.repository;

import com.ran.community.like.entity.PostLike;
import com.ran.community.post.entity.Post;
import com.ran.community.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<PostLike, Long> {
    void deleteByPostAndUser(Post post, User user);
    int countByPost_Id(long postId);
    boolean existsByUserAndPost(User user,Post post);
    boolean existsByPostIdAndUserId(Long postId, Long userId);

}
