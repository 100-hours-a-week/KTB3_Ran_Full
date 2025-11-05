package com.ran.community;

import com.ran.community.post.entity.Post;
import com.ran.community.post.repository.PostRepository;
import com.ran.community.comment.entity.Comment;
import com.ran.community.comment.repository.CommentRepository;
import com.ran.community.user.entity.User;
import com.ran.community.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class DummyDataTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void insertDummyData() {
        // 유저 한 명 생성
        User user = new User("test@test.com", "tester", "password123");
        userRepository.save(user);

        // 게시글 10개 + 각 게시글당 댓글 100개
        for (int p = 1; p <= 10; p++) {
            Post post = new Post("테스트 제목 " + p, "테스트 내용 " + p, null, user);
            postRepository.save(post);

            for (int c = 1; c <= 100; c++) {
                Comment comment = new Comment("댓글 내용 " + c, user, post);
                commentRepository.save(comment);
            }
        }
    }
}
