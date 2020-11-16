package com.sanvaad.View.Login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanvaad.Model.Entity.User;
import com.sanvaad.R;

public class SignUpFragment extends Fragment {

    LoginListener loginListener;

    public SignUpFragment(LoginListener listener) {
        // Required empty public constructor
        loginListener= listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void onContinue(){
        User user = new User();
        //TODO get All data from EditText
        loginListener.registerUser(user);
    }
}