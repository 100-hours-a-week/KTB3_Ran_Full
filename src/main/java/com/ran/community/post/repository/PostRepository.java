package com.ran.community.post.repository;

import com.ran.community.post.dto.request.PostCreateFormDto;
import com.ran.community.post.entity.Post;
import com.ran.community.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p.likeCount FROM Post p WHERE p.id = :postId")
    int findLikeCountByPostId(@Param("postId") long postId);


//    Post findById(Post post);
    //postId가 여러개일수도있는거아님?
    //얘를 postId로 해야할지 Post로 받아야할 지 모르겠네
//    //특정 게시글 가져오기
//    Optional<Post> getPost(long id);
//
//    //전체 리스트 생성
//    Optional<List<Post>> totalPostList();
//
//    //게시물 수정
//    Optional<Post> updatePost(Post post, PostCreateFormDto postCreateFormDto);
//
//    //게시물 삭제
//    Optional<Post> deletePost(long id);
}
