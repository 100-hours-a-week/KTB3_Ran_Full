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
//아 내가 애초에 IdGenerator를 구현해서 넣을 때 싱글톤으로 Id값을 최신 상태로 만드는 로직만 따로 구현해둔거구나.