package com.realdolmen.eindwerk.data;

public class User {
    private String userName, phoneNumber;

    public User() {
    }

    public User(String name, String phone) {
        this.userName = name;
        this.phoneNumber = phone;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
