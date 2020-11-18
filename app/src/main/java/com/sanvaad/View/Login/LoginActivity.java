package com.sanvaad.View.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.sanvaad.Model.Entity.User;
import com.sanvaad.R;
import com.sanvaad.View.Home.HomeActivity;
import com.sanvaad.ViewModel.LoginActivityViewModel;

public class LoginActivity extends AppCompatActivity implements LoginListener{

    LoginActivityViewModel viewModel;
    int RC_SIGN_IN;
    final String TAG = "LoginActivity";
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void updateUI(){
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        onDestroy();
    }

    @Override
    public void registerUser(User user) {
        viewModel.registerUser(user);
    }

    @Override
    public void showRegistrationForm() {
        //TODO switch to signup fragment
    }

    @Override
    public void loginFailed() {

    }

    @Override
    public void signIn() {
        Intent signInIntent = viewModel.getSignInIntent(); //mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                viewModel.firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }
}