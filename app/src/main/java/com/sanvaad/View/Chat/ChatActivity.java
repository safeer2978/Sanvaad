package com.sanvaad.View.Chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

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
Toolbar toolbar;
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

    RecyclerView recyclerView;
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        toolbar=findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.message_rv);
        textView=findViewById(R.id.toggle_rv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(recyclerView.getVisibility()==View.GONE)
                    recyclerView.setVisibility(View.VISIBLE);
                else
                    recyclerView.setVisibility(View.GONE);
            }
        });

    }
}