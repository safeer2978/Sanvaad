package com.sanvaad.View.Chat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.sanvaad.Model.UserData.db.Entity.Contact;
import com.sanvaad.Model.UserData.db.Entity.Message;
import com.sanvaad.Model.Util.TextData;
import com.sanvaad.R;
import com.sanvaad.View.Chat.Adapter.ChatMessagesAdapter;
import com.sanvaad.View.Chat.Adapter.CommonMessageAdapter;
import com.sanvaad.View.Chat.Adapter.ParticipantsAdapter;
import com.sanvaad.View.Home.Fragment.ContactsFragment;
import com.sanvaad.View.Home.HomeActivity;
import com.sanvaad.ViewModel.ChatActivityViewModel;
import com.sanvaad.View.Chat.Listener.messageListener;


import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class ChatActivity extends AppCompatActivity implements messageListener {

    private static final String TAG = "Chat_Activity";
    TextView textView;
    EditText editText;
    Button speakBtn, triggerBtn;
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    Toolbar toolbar;

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
        }

    }

    RecyclerView commonMessageRecyclerView, chatRecyclerView, participantsRecyclerViews;
    ConstraintLayout contactsFragmentContainer;
    ConstraintLayout mainChatContainer;
    ChatMessagesAdapter messagesAdapter;
    ChatActivityViewModel viewModel;
    ParticipantsAdapter participantsAdapter;
    Button addParticipantButton, userSendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        viewModel = new ViewModelProvider(this).get(ChatActivityViewModel.class);
        viewModel.setListener(this);


        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.icon_ionic_md_arrow_round_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        addParticipantButton = findViewById(R.id.ca_addparticipant_btn);
        commonMessageRecyclerView = findViewById(R.id.ca_common_message_rv);
        chatRecyclerView = findViewById(R.id.ca_chat_rv);
        participantsRecyclerViews = findViewById(R.id.ca_participants_rv);
        contactsFragmentContainer = findViewById(R.id.ca_contact_cl);
        mainChatContainer = findViewById(R.id.ca_mainchatcontainer_cl);


        commonMessageRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonMessageAdapter commonMessageAdapter= new CommonMessageAdapter(this,viewModel);
        commonMessageAdapter.setList(viewModel.getCommonMessageList());
        commonMessageRecyclerView.setAdapter(commonMessageAdapter);

/*

        contactsRecyclerViews.setLayoutManager(new LinearLayoutManager(this));
        ChooseContactAdapter contactAdapter =new ChooseContactAdapter(this,viewModel);
        contactAdapter.setList(viewModel.getContactList());
        contactsRecyclerViews.setAdapter(contactAdapter);
*/

        ContactsFragment contactsFragment = new ContactsFragment(viewModel);
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(contactsFragmentContainer.getId() ,contactsFragment);
        transaction.commit();


        chatRecyclerView = findViewById(R.id.ca_chat_rv);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesAdapter= new ChatMessagesAdapter(this, viewModel);

        chatRecyclerView.setAdapter(messagesAdapter);
        viewModel.getMessagesLiveData().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messagesAdapter.setMessageList(messages);
                messagesAdapter.notifyDataSetChanged();
                chatRecyclerView.setAdapter(messagesAdapter);
            }
        });

        chatRecyclerView.setItemAnimator(new SlideInUpAnimator());


        participantsRecyclerViews.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        participantsAdapter = new ParticipantsAdapter(this,viewModel);
        participantsRecyclerViews.setAdapter(participantsAdapter);
        viewModel.getParticipantsLiveData().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                participantsAdapter.setList(contacts);
                participantsAdapter.notifyDataSetChanged();
                participantsRecyclerViews.setAdapter(participantsAdapter);

                messagesAdapter.setContactList(contacts);
                messagesAdapter.notifyDataSetChanged();

            }
        });

        addParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleContactRecyclerView();
            }
        });

        commonMessageRecyclerView.setVisibility(View.GONE);
        textView=findViewById(R.id.toggle_rv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(commonMessageRecyclerView.getVisibility()==View.GONE)
                    commonMessageRecyclerView.setVisibility(View.VISIBLE);
                else
                    commonMessageRecyclerView.setVisibility(View.GONE);
            }
        });

        EditText userEditText = findViewById(R.id.editTextTextPersonName);
        userSendButton = findViewById(R.id.ca_send_btn);
        userSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userEditText.getText().toString().equals(""))
                {
                    viewModel.onUserText(userEditText.getText().toString());
                    messagesAdapter.notifyDataSetChanged();
                    userEditText.setText("");
                    chatRecyclerView.scrollToPosition(messagesAdapter.getItemCount());
                }

            }
        });


        viewModel.getTextLiveData().observe(this, new Observer<TextData>() {
            @Override
            public void onChanged(TextData textData) {
                viewModel.handleSpeakerMessages(textData);
                messagesAdapter.notifyDataSetChanged();
            }
        });


        toolbar.setTitle("New Conversation");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        toolbar.setSubtitle("On "+ Calendar.getInstance().getTime().toString());
        toolbar.setSubtitleTextColor(Color.parseColor("#FFFFFF"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_activity_toolbar_menu,menu);
        MenuItem item = (MenuItem) menu.findItem(R.id.toggle_switch);
        item.setActionView(R.layout.show_protected_switch);
        SwitchCompat switchCompat = item.getActionView().findViewById(R.id.switch_show_protected);
        switchCompat.setChecked(viewModel.getTextToSpeechToggle());
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setTextToSpeechToggle();
                switchCompat.setChecked(viewModel.getTextToSpeechToggle());
            }
        });


        MenuItem btnItem = (MenuItem) menu.findItem(R.id.show_toggle_speech);
        btnItem.setActionView(R.layout.show_chat_start_button);
        Button button = btnItem.getActionView().findViewById(R.id.speech_button);
        TextView recordingTextView = btnItem.getActionView().findViewById(R.id.speech_record_tv);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.triggerListening();
                viewModel.getListeningStateLiveData().observe(ChatActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        Log.d(TAG, "onChanged:"+aBoolean);
                        if(aBoolean){
                            recordingTextView.setText("Recording!");
                            button.setBackground(ContextCompat.getDrawable(ChatActivity.this,R.drawable.group_57));
                            Animation anim = new AlphaAnimation(0.0f, 1.0f);
                            anim.setDuration(850); //You can manage the blinking time with this parameter
                            anim.setStartOffset(20);
                            anim.setRepeatMode(Animation.REVERSE);
                            anim.setRepeatCount(Animation.INFINITE);
                            button.startAnimation(anim);
                        }else{
                            recordingTextView.setText("Stopped");
                            button.setBackground(ContextCompat.getDrawable(ChatActivity.this,R.drawable.rec_start_button));
                            button.clearAnimation();
                        }
                    }
                });
            }
        });




        //button.setBackground(ContextCompat.getDrawable(ChatActivity.this,R.drawable.rec_start_button));
        return true;
    }

    public void toggleContactRecyclerView(){
        if(contactsFragmentContainer.getVisibility()==View.GONE){
            contactsFragmentContainer.setVisibility(View.VISIBLE);
            mainChatContainer.setVisibility(View.GONE);
        }else{
            contactsFragmentContainer.setVisibility(View.GONE);
            mainChatContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        viewModel.stopApi();
        super.onPause();
    }

    @Override
    public void onBackPressed() {

        //Building Dialog for ending the conversation
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are You sure you want to Save This Conversation?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.saveConversation();
                        viewModel.endConversation();
                        startActivity(new Intent(ChatActivity.this, HomeActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(ChatActivity.this, HomeActivity.class));
                        finish();
                        viewModel.endConversation();
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();

        if(mainChatContainer.getVisibility()==View.VISIBLE){
            dialog.show();
        }else{
            toggleContactRecyclerView();
            participantsAdapter.notifyDataSetChanged();
            refreshMessage();
        }
    }

    @Override
    public void refreshMessage() {
        messagesAdapter.notifyDataSetChanged();
        chatRecyclerView.smoothScrollToPosition(messagesAdapter.getItemCount());
    }

    @Override
    public void hideContactScreen() {
        toggleContactRecyclerView();
        participantsAdapter.notifyDataSetChanged();
        refreshMessage();
    }

    @Override
    public void hideCommonMessageScreen() {
        commonMessageRecyclerView.setVisibility(View.GONE);
        refreshMessage();
    }
}