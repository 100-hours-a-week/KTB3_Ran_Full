package com.ran.community.global.config;

import com.ran.community.comment.entity.Comment;
import com.ran.community.comment.repository.CommentRepository;
import com.ran.community.post.entity.Post;
import com.ran.community.post.repository.PostRepository;
import com.ran.community.user.entity.User;
import com.ran.community.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
                                   PostRepository postRepository,
                                   CommentRepository commentRepository) {
        return args -> initialize(userRepository, postRepository, commentRepository);
    }

    @Transactional
    public void initialize(UserRepository userRepository,
                           PostRepository postRepository,
                           CommentRepository commentRepository) {

        //유저 더미 생성
        User user1 = new User("test1@example.com", "홍길동", "Abcd1234!");
        User user2 = new User("test2@example.com", "이몽룡", "Qwer5678!");
        userRepository.save(user1);
        userRepository.save(user2);

        //게시글 10개 생성
        for (int i = 1; i <= 10; i++) {
            User author = (i % 2 == 0) ? user1 : user2;
            Post post = new Post("테스트 게시글 " + i, "이것은 테스트용 내용입니다. " + i, null, author);
            postRepository.save(post);

            // 댓글 100개씩 생성
            for (int c = 1; c <= 100; c++) {
                User commenter = (c % 2 == 0) ? user1 : user2;
                Comment comment = new Comment("테스트 댓글 " + c + " (게시글 " + i + ")", commenter, post);
                commentRepository.save(comment);
            }
        }

        System.out.println("초기 더미 데이터 삽입 완료 (게시글 10개, 댓글 1000개)");
    }
}
