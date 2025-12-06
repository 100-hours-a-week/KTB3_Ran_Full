package com.ran.community;

import com.ran.community.post.entity.Post;
import com.ran.community.post.repository.PostRepository;
import com.ran.community.comment.entity.Comment;
import com.ran.community.comment.repository.CommentRepository;
import com.ran.community.user.entity.User;
import com.ran.community.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Configuration
@RequiredArgsConstructor
class DummyDataConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public void run(String... args) {
        User user = new User("test@test.com", "tester", "password123");
        userRepository.save(user);

        for (int p = 1; p <= 10; p++) {
            Post post = new Post("테스트 제목 " + p, "테스트 내용 " + p, null, user);
            postRepository.save(post);

            for (int c = 1; c <= 50; c++) {
                Comment comment = new Comment("댓글 " + c, user, post);
                commentRepository.save(comment);
            }
        }
    }
}
