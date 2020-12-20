package com.sanvaad.View.Login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;
import com.sanvaad.Model.UserData.db.Entity.User;
import com.sanvaad.R;

public class SignUpFragment extends Fragment {

    LoginListener loginListener;

    EditText emailET, nameET, ageET,phoneET;
    Button button;
    FirebaseUser firebaseUser;

    public SignUpFragment(LoginListener listener, FirebaseUser firebaseUser) {
        loginListener= listener;
        this.firebaseUser = firebaseUser;
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
        emailET = view.findViewById(R.id.lf_et_email);
        nameET = view.findViewById(R.id.lf_et_name);
        ageET = view.findViewById(R.id.lf_et_age);
        phoneET = view.findViewById(R.id.lf_et_phone);
        button = view.findViewById(R.id.lf_btn_continue);

        // Attaching Listener to Button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContinue();
            }
        });

        /* Since we are using Sign in with google,
         * we shall get email from sign in, and display it to user.
         * The user will not be able to edit it.
         * */
        String email = firebaseUser.getEmail();
        emailET.setText(email);
        emailET.setFocusable(false);    // This will cause the user to not edit the email.

        /*For all other attributes, we allow the user to make changes*/
        nameET.setText(firebaseUser.getDisplayName());
        phoneET.setText(firebaseUser.getPhoneNumber());

    }

    public void onContinue(){
        User user = new User();
        user.setName(nameET.getText().toString());
        user.setEmail(emailET.getText().toString());
        user.setPhoneNo(phoneET.getText().toString());
        user.setAge(Integer.parseInt(ageET.getText().toString()));
        user.setFirebaseId(firebaseUser.getUid());
        loginListener.registerUser(user);
    }
}