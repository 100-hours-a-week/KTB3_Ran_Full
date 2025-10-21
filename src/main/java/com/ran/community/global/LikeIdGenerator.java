package com.ran.community.global;

import java.util.concurrent.atomic.AtomicLong;

public class LikeIdGenerator {
    private static final LikeIdGenerator Instance = new LikeIdGenerator();
    private final AtomicLong index = new AtomicLong();

    private LikeIdGenerator() {}
    public static LikeIdGenerator getInstance() {
        return Instance;
    }
    public long nextId() {
        return index.getAndIncrement();
    }
}
