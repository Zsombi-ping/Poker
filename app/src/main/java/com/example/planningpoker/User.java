package com.example.planningpoker;

public class User {

    String userName;
    String userEmail;
    String userPassword;
    Integer userType;

    public User()
    {

    }

    public User(String userName, String userEmail, String userPassword,Integer userType) {

        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userType = userType;
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
