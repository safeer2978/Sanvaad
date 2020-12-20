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
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.sanvaad.Model.Util.Constants;
import com.sanvaad.Model.UserData.db.Entity.Conversation;
import com.sanvaad.Model.Repository;
import com.sanvaad.Model.UserData.UserDataStore;
import com.sanvaad.R;
import com.sanvaad.View.Chat.ChatActivity;
import com.sanvaad.View.Home.Adapter.ViewPagerAdapter;
import com.sanvaad.View.Home.Fragment.FeedBackFragment;
import com.sanvaad.View.Home.Fragment.ViewChatFragment;
import com.sanvaad.View.Home.Listener.BackListener;
import com.sanvaad.View.Home.Listener.BrowseChatsListener;
import com.sanvaad.View.Login.LoginActivity;
import com.sanvaad.ViewModel.HomeActivityViewModel;

public class HomeActivity extends AppCompatActivity implements BrowseChatsListener, BackListener {
    UserDataStore userDataStore;

    Button button;
    ConstraintLayout constraintLayout;
    HomeActivityViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(HomeActivityViewModel.class);
        viewModel.init(getApplication());
        constraintLayout= findViewById(R.id.fragment_container);
        button = findViewById(R.id.button);

        Toolbar toolbar;
        toolbar=findViewById(R.id.ha_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ViewPager2 viewPager = findViewById(R.id.ha_viewpager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        adapter.setInstance(this);
        adapter.setViewModel(viewModel);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.ha_tablayout);
        new TabLayoutMediator(tabLayout,viewPager,(tab,position)->tab.setText(getTabTitle(position))).attach();


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
        onPause();
    }

    public void logout(){
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
        FragmentManager fragmentManager=this.getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        /*Setting Login Fragment as initial View*/
        Fragment fragment = new ViewChatFragment(conversation,viewModel, this);
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
        constraintLayout.setVisibility(View.VISIBLE);
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
            case R.id.ha_toolbar_feedback:
                switchToFeedback();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void switchToFeedback() {
        FragmentManager fragmentManager=this.getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        /*Setting Login Fragment as initial View*/
        Fragment fragment = new FeedBackFragment(viewModel);
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
        constraintLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if(constraintLayout.getVisibility()==View.VISIBLE)
            constraintLayout.setVisibility(View.GONE);
    }

    public void onClickBack(View v){
        onBackPressed();
    }

    @Override
    public void onBack() {
        onBackPressed();
    }
}

