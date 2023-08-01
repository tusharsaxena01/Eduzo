package com.bitAndroid.eduzo.classes;

public class UserData {

    String uuid;
    String name;
    String mobileNo;
    String email;
    String password;
    String role;

    // No argument constructor
    public UserData() {
    }

    // 3 argument constructor
    // save user from google

    public UserData(String uuid, String name, String mobileNo, String email, String role) {
        this.uuid = uuid;
        this.name = name;
        this.mobileNo = mobileNo;
        this.email = email;
        this.role = role;
    }


    // all parameter constructor

    public UserData(String uuid, String name, String mobileNo, String email, String password, String role) {
        this.uuid = uuid;
        this.name = name;
        this.mobileNo = mobileNo;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
