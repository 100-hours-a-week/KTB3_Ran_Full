package com.ran.community.global;

import java.util.concurrent.atomic.AtomicLong;

public class CommentIdGenerator {
    private static final CommentIdGenerator instance = new CommentIdGenerator();
    private final AtomicLong index = new AtomicLong();


    private CommentIdGenerator() {}

    public static CommentIdGenerator getInstance() {
        return instance;
    }

    public long nextId() {
        return index.getAndIncrement();
    }
}
