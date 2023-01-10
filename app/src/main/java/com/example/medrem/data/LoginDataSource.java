package com.example.medrem.data;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static java.lang.Thread.sleep;

import android.util.Log;
import com.example.medrem.data.model.LoggedInUser;
import com.example.medrem.ui.login.User;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class LoginDataSource {
    private FirebaseFirestore db;

    private static volatile AtomicReference<LoggedInUser> instance = new AtomicReference<>();
    private List<User> users;

    public LoginDataSource() {
        db = FirebaseFirestore.getInstance();
        users = new ArrayList<>();

        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            users.add(document.toObject(User.class));
                        }
                        Log.v(TAG, "Successfully retrieved documents");
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                });
    }

    public Result<LoggedInUser> login(String username, String password) {

        if (instance.get() != null) {
            return new Result.Success<LoggedInUser>(instance.get());
        }

        for (User user : users) {
            if (user.getUserId().equals(username)) {
                if (user.getPassword().equals(password)) {
                    instance.set(user);
                    return new Result.Success<LoggedInUser>(user);
                } else {
                    return new Result.Error(new IOException("Given password is invalid"));
                }
            }
        }

        // User does not exist, register
        User user = new User(username, username, password);
        users.add(user);
        db.collection("users")
                .add(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.v(TAG, "Successfully added user");
                    } else {
                        Log.d(TAG, "Error adding user: ", task.getException());
                    }
                });

        return new Result.Success<LoggedInUser>(user);
    }

    public void logout() {
        instance.set(null);
    }
}
