package com.sanvaad.View.Chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sanvaad.R;
import com.sanvaad.ViewModel.ChatActivityViewModel;

public class ChatActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button speakBtn, triggerBtn;
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    ChatActivityViewModel viewModel;

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        viewModel = new ViewModelProvider(this).get(ChatActivityViewModel.class);
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editTextTextPersonName);
        speakBtn = findViewById(R.id.speak);
        triggerBtn = findViewById(R.id.triggerBtn);



        viewModel.getTextData().observe(this,textData -> {
            textView.setText(textData.getText());
        });
        triggerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.triggerListening();
                triggerBtn.setText(viewModel.gettriggerListeningState()?"Listening":"not Listening");
            }
        });

        speakBtn.setText("Speak");
        speakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.speakText(editText.getText().toString());
            }
        });




    }
}