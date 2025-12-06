package com.ran.community.dummy;

import com.ran.community.comment.entity.Comment;
import com.ran.community.comment.repository.CommentRepository;
import com.ran.community.like.entity.PostLike;
import com.ran.community.like.repository.LikeRepository;
import com.ran.community.post.entity.Post;
import com.ran.community.post.repository.PostRepository;
import com.ran.community.user.entity.User;
import com.ran.community.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DummyDataRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public void run(String... args) {



        System.out.println("===== DummyDataRunner: 게시글 생성 시작 =====");

        // 기본 유저 1명 생성
        User user = new User("dummy@test.com", "dummyUser", "1234");
        userRepository.save(user);

        // 게시글만 1000개 생성
        for (int i = 1; i <= 1000; i++) {
            Post post = new Post(
                    "더미 제목 " + i,
                    "더미 내용 " + i,
                    null,
                    user
            );
            postRepository.save(post);
        }

        System.out.println("===== 게시글 1000개 생성 완료 =====");
    }
}