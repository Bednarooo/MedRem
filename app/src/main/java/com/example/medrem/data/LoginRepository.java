package com.example.medrem.data;

import android.widget.Toast;

import com.example.medrem.AddingMedicineActivity;
import com.example.medrem.data.model.LoggedInUser;
import com.example.medrem.ui.login.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    private User user;

    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
        user = retrieveUser();
    }

    private User retrieveUser() {
        return dataSource.load();
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        System.out.println("Is logged in");
        System.out.println(user != null);
        return user != null;
    }

    public String getUsername() {
        if (isLoggedIn()) {
            return user.getUserId();
        }
        return null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(User user) {
        this.user = user;
        dataSource.save(user);
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<User>) result).getData());
        }
        return result;
    }
}