package com.ran.community.dummy;
import com.ran.community.post.entity.Post;
import com.ran.community.post.repository.PostRepository;
import com.ran.community.user.entity.User;
import com.ran.community.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@RequiredArgsConstructor
class DummyDataRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public void run(String... args) {

        // 🚫 이미 한 번 실행되어 데이터가 있으면 종료
        if (postRepository.count() > 0 || userRepository.count() > 0) {
            System.out.println("===== DummyData already exists. Skip. =====");
            return;
        }

        System.out.println("===== DummyData 삭제 시작 =====");

        postRepository.deleteAll();

        System.out.println("===== DummyData 삭제 완료 =====");


        System.out.println("===== DummyDataRunner: 게시글 생성 시작 =====");

        // 사용자 이미 존재하면 기존 사용자 가져오기
        User user = userRepository.findByEmail("dummy@test.com")
                .orElseGet(() -> userRepository.save(
                        new User("dummy@test.com", "tester", "password123")
                ));

        // 게시글 1000개 생성
        for (int i = 1; i <= 1000; i++) {
            Post post = new Post("더미 제목 " + i, "더미 내용 " + i, null, user);
            postRepository.save(post);
        }

        System.out.println("===== DummyDataRunner: 게시글 생성 완료 =====");
    }

}
