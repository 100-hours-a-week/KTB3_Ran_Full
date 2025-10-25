package com.ran.community.global;

import java.util.concurrent.atomic.AtomicLong;

public class PostIdGenerator extends IdGenerator {
    private static final PostIdGenerator INSTANCE = new PostIdGenerator();

    //생성자
    private PostIdGenerator() {}

    //반환값만 해당 메서드에서 관리
    public static PostIdGenerator getInstance() {
        return INSTANCE;
    }
}
