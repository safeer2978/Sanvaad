package com.sanvaad.View.Login;

import com.google.firebase.auth.FirebaseUser;
import com.sanvaad.Model.UserData.db.Entity.User;

public interface LoginListener {

    void signIn();
    void updateUI();

    void registerUser(User user);

    void showRegistrationForm(FirebaseUser firebaseUser);

    void loginFailed();
}
