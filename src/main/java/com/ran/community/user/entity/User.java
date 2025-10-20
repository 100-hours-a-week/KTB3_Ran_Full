package com.ran.community.user.entity;

//Entity라고 생각
public class User {
    private long userId;
    private String email;
    private String username;
    private String password;

    public User() {}
    public User(long userId, String email, String username, String password) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId){
        this.userId = userId;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password=password;
    }

    public String toString(){
        return username + " " + userId + " " + email + " " + password;
    }
}
