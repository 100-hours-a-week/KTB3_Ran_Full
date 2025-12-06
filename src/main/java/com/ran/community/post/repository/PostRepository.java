package com.ran.community.post.repository;

import com.ran.community.post.dto.request.PostCreateFormDto;
import com.ran.community.post.dto.response.PostCountDto;
import com.ran.community.post.entity.Post;
import com.ran.community.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    //좋아요 갯수
    @Query("SELECT p.likeCount FROM Post p WHERE p.id = :postId")
    int findLikeCountByPostId(@Param("postId") long postId);

    //댓글 갯수
    @Query("SELECT p.commentCount FROM Post p WHERE p.id = :postId")
    int findCommentCountByPostId(@Param("postId") long postId);

    //조회수 갯수
    @Query("SELECT p.viewCount FROM Post p WHERE p.id = :postId")
    int findViewCountByPostId(@Param("postId") long postId);

    //좋아요 댓글 조회수 갯수 모두 한꺼번에 가져오기 //DTO projection 사용하기
    @Query("SELECT new com.ran.community.post.dto.response.PostCountDto(p.viewCount, p.commentCount, p.likeCount) FROM Post p WHERE p.id = :postId")
    PostCountDto findCountByPostId(@Param("postId") long postId);

    //postId에 대해 댓글 조회하기
    //post 테이블 데이터에서 댓글을 가지고 올건데, postId값인 id에서 p(전체)를 가져올것임.
    //fetchjoin
//    @Query("SELECT p FROM Post p JOIN FETCH p.comments WHERE p.id = :postId")
//    Post findByPostIdWithComments(@Param("postId") long postId);

    //post 작성자 가져오기
    @Query("SELECT p FROM Post p JOIN FETCH p.user WHERE p.id = :postId")
    Optional<Post> findByPostIdWithAuthor(@Param("postId") long postId);

    //post + user + comment
    @Query("""
    SELECT DISTINCT p
    FROM Post p
    LEFT JOIN FETCH p.user
    LEFT JOIN FETCH p.comments c
    WHERE p.id = :postId
    ORDER BY c.created_at DESC
""")
    Optional<Post> findByPostIdWithCommentsAuthor(@Param("postId") long postId);


    //전체 post + user + comment
    @Query("SELECT p FROM Post p JOIN FETCH p.user")
    List<Post> findWithCommentsAuthor();


    @Query("SELECT p FROM Post p JOIN FETCH p.user ORDER BY p.created_at DESC")
    List<Post> findAllWithAuthorOrderByCreatedAtDesc();


}
