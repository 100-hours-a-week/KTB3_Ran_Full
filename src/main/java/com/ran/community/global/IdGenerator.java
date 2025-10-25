package com.ran.community.global;

import java.util.concurrent.atomic.AtomicLong;

//인스턴스에서 공통으로 사용되는 로직
public abstract class IdGenerator {
    private final AtomicLong index = new AtomicLong(0);
    //증가 시킴
    public long nextId() {
        return index.getAndIncrement();
    }
}
