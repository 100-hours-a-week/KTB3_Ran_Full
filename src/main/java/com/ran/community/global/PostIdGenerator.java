package com.ran.community.global;

import java.util.concurrent.atomic.AtomicLong;

public class PostIdGenerator {
    private static final PostIdGenerator INSTANCE = new PostIdGenerator();
    private final AtomicLong index = new AtomicLong(0);

    private PostIdGenerator() {}

    //증가 시킴
    public long nextId() {
        return index.getAndIncrement();
    }

    public static PostIdGenerator getInstance() {
        return INSTANCE;
    }
}
