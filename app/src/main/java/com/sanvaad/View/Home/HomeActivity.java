package com.sanvaad.View.Home;
// nachiket
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.sanvaad.Model.Constants;
import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Repository;
import com.sanvaad.Model.UserDataStore;
import com.sanvaad.R;
import com.sanvaad.View.Chat.ChatActivity;
import com.sanvaad.View.Login.LoginActivity;
import com.sanvaad.ViewModel.HomeActivityViewModel;

public class HomeActivity extends AppCompatActivity implements BrowseChatsListener{
    UserDataStore userDataStore;

    HomeActivityViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDataStore = new UserDataStore(getApplication());
        Toolbar toolbar;
        toolbar=findViewById(R.id.ha_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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


/*        viewModel = new ViewModelProvider(this).get(HomeActivityViewModel.class);
        viewModel.init(getApplication());
        FragmentManager fragmentManager=this.getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();

        *//*Setting Login Fragment as initial View*//*
        Fragment fragment = new BrowseChatsFragment(this, viewModel);
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();*/
        viewModel = new ViewModelProvider(this).get(HomeActivityViewModel.class);
        viewModel.init(getApplication());
        ViewPager2 viewPager = findViewById(R.id.ha_viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        adapter.setInstance(this);
        adapter.setViewModel(viewModel);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.ha_tablayout);

        new TabLayoutMediator(tabLayout,viewPager,(tab,position)->tab.setText(getTabTitle(position))).attach();
        //new TabLayoutMediator(tabLayout,viewPager,true,true,(tab, position)->tab.setText(tab.getText())).attach();


    }

    private String getTabTitle(int position) {
        String string="";
        switch (position){
            case Constants.TAB_CHAT:
                string="Chats";
                break;
            case Constants.TAB_CONTACTS:
                string="Contacts";
                break;
            case Constants.TAB_PROFILE:
                string="Profile";
                break;

        }
        return string;
    }

    public void onClick(View view){
        startActivity(new Intent(HomeActivity.this, ChatActivity.class));
    }

    public void logout(){
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

    @Override
    public void openChat(Conversation conversation) {
        ConstraintLayout constraintLayout = findViewById(R.id.fragment_container);
        constraintLayout.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager=this.getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        /*Setting Login Fragment as initial View*/
        Fragment fragment = new ViewChatFragment(conversation,viewModel);
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_activity_toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ha_toolbar_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

interface BrowseChatsListener{

    void openChat(Conversation conversation);
}