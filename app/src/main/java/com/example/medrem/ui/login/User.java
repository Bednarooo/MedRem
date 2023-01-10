package com.example.medrem.ui.login;

import com.example.medrem.data.model.LoggedInUser;

public class User extends LoggedInUser {
    protected String password;

    public User() {
        super();
    }

    public User(String userId, String displayName, String password) {
        super(userId, displayName);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
