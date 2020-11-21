package com.sanvaad.View.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.sanvaad.Model.Constants;
import com.sanvaad.Model.Entity.User;
import com.sanvaad.R;
import com.sanvaad.View.Home.HomeActivity;
import com.sanvaad.ViewModel.LoginActivityViewModel;

public class LoginActivity extends AppCompatActivity implements LoginListener{

    ConstraintLayout constraintLayout;
    FragmentTransaction transaction;
    FragmentManager fragmentManager;
    GoogleSignInClient mGoogleSignInClient;
    LoginActivityViewModel viewModel;
    int RC_SIGN_IN;
    final String TAG = "LoginActivity";
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        constraintLayout = findViewById(R.id.la_cl);

        /*Initializing FragmentTransaction to enable viewing Fragments*/
        fragmentManager=this.getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();

        /*Setting Login Fragment as initial View*/
        Fragment fragment = new LoginFragment(this);
        transaction.replace(R.id.la_cl,fragment);
        transaction.commit();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        /*Initializing ViewModel and passing Listener instance*/
        viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
        viewModel.init(getApplication());
        viewModel.setListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean(Constants.LOGIN_STATUS,false))
            updateUI();
    }

    public void updateUI(){
        viewModel.saveGoogleClient(mGoogleSignInClient);
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void registerUser(User user) {
        viewModel.registerUser(user);
    }

    @Override
    public void showRegistrationForm(FirebaseUser user) {
        Fragment fragment = new SignUpFragment(this, user);

        transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.la_cl,fragment);
        transaction.commit();
    }

    @Override
    public void loginFailed() {

    }

    @Override
    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
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