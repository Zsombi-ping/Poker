package com.example.planningpoker;

public class User {
    String userId;
    String userName;
    String userEmail;
    String userPassword;
    Integer userType;

    public User()
    {

    }

    public User(String userId, String userName, String userEmail, String userPassword,Integer userType) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public Integer getUserType() {
        return userType;
    }
}
