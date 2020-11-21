package com.sanvaad.View.Home;
// nachiket
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sanvaad.Model.Constants;
import com.sanvaad.Model.Entity.CommonMessage;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Entity.Feedback;
import com.sanvaad.Model.Entity.Message;
import com.sanvaad.Model.Entity.User;
import com.sanvaad.Model.Repository;
import com.sanvaad.Model.UserDataStore;
import com.sanvaad.R;
import com.sanvaad.View.Chat.ChatActivity;
import com.sanvaad.View.Login.LoginActivity;
import com.sanvaad.View.Login.LoginFragment;
import com.sanvaad.ViewModel.HomeActivityViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.TooManyListenersException;

public class HomeActivity extends AppCompatActivity {
    UserDataStore userDataStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDataStore = new UserDataStore(getApplication());

/*        User user= new User();
        user.setEmail("334");
        user.setName("Safeer");
        user.setAge(4);
        user.setStatus("M");
        user.setUserID(9999);
        user.setPhoneNo(323232);
        userDataStore.createUser(user,"as");*/
/*        User user = new User();
        user.setName("Admin");*/
/*        userDataStore.createUser(user,"");*/
        //userDataStore.createCommonmessage(new CommonMessage(user.getUserID(),0,"Hello", Calendar.getInstance().getTimeInMillis()));
        //userDataStore.createFeedback(new Feedback(0,"Hi", Calendar.getInstance().getTimeInMillis()));

        //userDataStore.createConversation(new Conversation(Calendar.getInstance().getTimeInMillis(),1));
        //userDataStore.createMessage(new Message("Message1"
        //,1,1));


        HomeActivityViewModel viewModel = new ViewModelProvider(this).get(HomeActivityViewModel.class);
        viewModel.init(getApplication());
        FragmentManager fragmentManager=this.getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();

        /*Setting Login Fragment as initial View*/
        Fragment fragment = new ContactsFragment(viewModel);
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();


    }

    public void onClick(View view){
        startActivity(new Intent(HomeActivity.this, ChatActivity.class));
    }

    public void logout(View view){

        //TODO: FIND A BETTER WAY TO DO THIS!!!
        Repository repository = Repository.getInstance(getApplication());
        GoogleSignInClient client=repository.getGoogleSignInClient();
        client.signOut().addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseAuth.getInstance().signOut();
                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
                        sharedPreferences.edit().putBoolean(Constants.LOGIN_STATUS,false).apply();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
        );


    }
}