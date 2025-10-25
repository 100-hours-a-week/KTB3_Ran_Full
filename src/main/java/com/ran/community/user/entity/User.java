package com.ran.community.user.entity;

//Entity라고 생각
public class User {
    private long userId;
    private String email;
    private String username;
    private String password;

    public User(long userId, String email, String username, String password) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public String getEmail(){
        return email;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

}
