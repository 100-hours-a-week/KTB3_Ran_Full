package com.ran.community.global;

import java.util.concurrent.atomic.AtomicLong;

public class CommentIdGenerator extends IdGenerator {
    private static final CommentIdGenerator instance = new CommentIdGenerator();
    private CommentIdGenerator() {}
    public static CommentIdGenerator getInstance() {
        return instance;
    }
}
