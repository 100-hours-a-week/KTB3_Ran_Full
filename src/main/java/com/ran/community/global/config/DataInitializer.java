package com.ran.community.global.config;

import com.ran.community.post.entity.Post;
import com.ran.community.post.repository.PostRepository;
import com.ran.community.user.entity.User;
import com.ran.community.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PostRepository postRepository) {
        return args -> {
            // 더미 유저
            User user1 = new User("test1@example.com", "홍길동", "Abcd1234!");
            User user2 = new User("test2@example.com", "이몽룡", "Qwer5678!");
            userRepository.save(user1);
            userRepository.save(user2);

            // 더미 게시글
            postRepository.save(new Post("첫 번째 게시글", "테스트 내용입니다.", null, user1));
            postRepository.save(new Post("두 번째 게시글", "이건 샘플 게시물이에요.", null, user2));

            System.out.println("초기 데이터 삽입 완료");
        };
    }
}