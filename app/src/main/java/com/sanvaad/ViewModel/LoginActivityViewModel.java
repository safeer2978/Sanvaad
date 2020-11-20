package com.sanvaad.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sanvaad.Model.Constants;
import com.sanvaad.Model.Entity.User;
import com.sanvaad.Model.Repository;
import com.sanvaad.View.Login.LoginListener;

public class LoginActivityViewModel extends ViewModel {
    public static FirebaseAuth mAuth;
    public static FirebaseUser firebaseUser;

    final String TAG = "LoginActivityViewModel";

    LoginListener loginListener;
    Repository repository;

    Application application;

    public LoginActivityViewModel(){}

    public void init(Application application){

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        this.application=application;
        repository = Repository.getInstance(application);
    }

    public void setListener(LoginListener listener){
        loginListener = listener;
    }


    public FirebaseUser getFireBaseUser(){
        return firebaseUser;
    }


    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            SharedPreferences sharedPreferences = application.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
                            sharedPreferences.edit().putBoolean(Constants.LOGIN_STATUS,true).apply();
                            //loginListener.updateUI(firebaseUser);
                            repository.handleLoginSuccess(loginListener, firebaseUser);
                            /*if(repository.isUserRegistered(firebaseUser)){
                                loginListener.updateUI();
                            }else{
                                loginListener.showRegistrationForm(firebaseUser);
                            }*/
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            loginListener.loginFailed();
                        }

                        // ...
                    }
                });
    }


    public void registerUser(User user){
        repository.registerNewUser(user);
        loginListener.updateUI();
    }

    public void saveGoogleClient(GoogleSignInClient mGoogleSignInClient) {
        repository.saveGoogleClient(mGoogleSignInClient);
    }
}
