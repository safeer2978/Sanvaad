package com.sanvaad.View.Home.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.sanvaad.Model.UserData.db.Entity.Feedback;
import com.sanvaad.R;
import com.sanvaad.ViewModel.HomeActivityViewModel;

import java.util.Calendar;

public class FeedBackFragment extends Fragment {

    public FeedBackFragment(HomeActivityViewModel viewModel){
        this.viewModel=viewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed_back, container, false);
    }

    HomeActivityViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText feedbackEditText=view.findViewById(R.id.ha_fb_et);
        Button send =view.findViewById(R.id.ha_fb_send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Feedback feedback = new Feedback(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                        feedbackEditText.getText().toString(),
                        Calendar.getInstance().getTimeInMillis());
                        viewModel.sendFeedBack(feedback);
                Toast.makeText(getContext(),"Feedback Sent! Thank you!", Toast.LENGTH_LONG).show();
                send.setVisibility(View.INVISIBLE);
            }
        });
    }
}