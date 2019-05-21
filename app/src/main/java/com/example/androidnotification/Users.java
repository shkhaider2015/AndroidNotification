package com.example.androidnotification;

import java.io.Serializable;

public class Users implements Serializable {

    public String email;
    public String token;

    public Users(String email, String token)
    {
        this.email = email;
        this.token = token;
    }
}
