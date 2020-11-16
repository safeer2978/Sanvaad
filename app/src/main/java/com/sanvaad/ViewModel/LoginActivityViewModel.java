package com.sanvaad.ViewModel;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sanvaad.Model.Entity.User;
import com.sanvaad.Model.Repository;
import com.sanvaad.View.Login.LoginListener;

public class LoginActivityViewModel {
    public static FirebaseAuth mAuth;
    public static FirebaseUser firebaseUser;
    private GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN;
    final String TAG = "LoginActivityViewModel";

    LoginListener loginListener;
    Repository repository;

    LoginActivityViewModel(LoginListener listener, Application application){
        firebaseUser = mAuth.getCurrentUser();
        loginListener = listener;
        repository = Repository.getInstance(application);
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
                            //loginListener.updateUI(firebaseUser);
                            if(repository.isUserRegistered(firebaseUser)){
                                loginListener.updateUI();
                            }else{
                                loginListener.showRegistrationForm();
                            }
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
    public Intent getSignInIntent(){return mGoogleSignInClient.getSignInIntent();}

    public void registerUser(User user){
        repository.registerNewUser(user);
        loginListener.updateUI();
    }
}
