package com.ran.community.global;

import com.ran.community.user.repository.UserRepository;

import java.util.concurrent.atomic.AtomicLong;

//싱글톤으로 구현
//싱글톤 : 전체 어플리케이션에서 하나의 인스턴스로 유지됨.
public class UserIdGenerator extends IdGenerator {
    private static final UserIdGenerator INSTANCE = new UserIdGenerator();
    private UserIdGenerator() {}
    //private : 싱글톤은 전체 앱에서 동일한 객체로 유짇되어아하므로 다른 클래스나 외부에서 객체를 생성하지 못하도록 private으로 구현됨.
    //static : 때문에 Instance를 가져올경우 static으로 구현해야됨.
    public static UserIdGenerator getInstance() {
        return INSTANCE;
    }


}
