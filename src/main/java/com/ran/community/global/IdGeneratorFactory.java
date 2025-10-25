package com.ran.community.global;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class IdGeneratorFactory {
    //해당곳에 index뿐만 아니라 Map 도 함께 정함.
    private final Map<Class<?>, AtomicLong> DB = new ConcurrentHashMap<>();
    //번호 표를 뽑는 메서드라고 생각해야됨.
    //가장 최신의 번호를 기억해 두는 Map


    public synchronized long nextId(Class<?> anyClass){
        return DB.computeIfAbsent(anyClass, k -> new AtomicLong(0)).incrementAndGet();
    }


}
