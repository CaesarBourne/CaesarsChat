package com.caesar.ken.caesarschat.models;

/**
 * Created by e on 2/21/2018.
 */

public class User {
    public String uid;
    public String email;
    public String token;

    public User() {
    }

    public User(String uid, String email, String token) {
        this.uid = uid;
        this.email = email;
        this.token = token;
    }
}
