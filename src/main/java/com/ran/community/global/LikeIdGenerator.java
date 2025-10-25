package com.ran.community.global;

import java.util.concurrent.atomic.AtomicLong;

public class LikeIdGenerator extends IdGenerator {
    private static final LikeIdGenerator Instance = new LikeIdGenerator();
    private LikeIdGenerator() {}
    public static LikeIdGenerator getInstance() {
        return Instance;
    }
}
