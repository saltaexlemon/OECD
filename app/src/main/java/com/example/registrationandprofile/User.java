package com.example.registrationandprofile;

public class User {
    public String fullName, add1, add2, state, postal, email;

    public User() {}


    public User(String email, String fullname, String add1, String add2, String state, String postal) {
        this.fullName = fullname;
        this.add1 = add1;
        this.add2 = add2;
        this.state = state;
        this.postal = postal;
        this.email = email;
    }
}
