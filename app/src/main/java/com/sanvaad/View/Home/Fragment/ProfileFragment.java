package com.sanvaad.View.Home.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sanvaad.Model.Util.CircleTransform;
import com.sanvaad.Model.Util.Constants;
import com.sanvaad.Model.UserData.db.Entity.User;
import com.sanvaad.R;
import com.sanvaad.ViewModel.HomeActivityViewModel;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    HomeActivityViewModel viewModel;

    RadioButton male, female;

    public ProfileFragment(HomeActivityViewModel viewModel){
        this.viewModel=viewModel;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView name = view.findViewById(R.id.ha_pf_name);
        TextView phone = view.findViewById(R.id.ha_pf_phone);
        TextView email = view.findViewById(R.id.ha_pf_email);
        ImageView imageView = view.findViewById(R.id.ha_pf_progileimg);


        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        boolean gender = sharedPreferences.getBoolean(Constants.GENDER,true);
        male=view.findViewById(R.id.radioButton_male);
        female=view.findViewById(R.id.radioButton_female);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRadioButtonClicked(v);
            }
        });


        if(gender) {
            male.setChecked(true);
            female.setChecked(false);
        }
        else{
            male.setChecked(false);
            female.setChecked(true);
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        User modelUser = viewModel.getUser();

        name.setText(modelUser.getName());
        email.setText(modelUser.getEmail());
        phone.setText(modelUser.getPhoneNo());

        assert user != null;
        Picasso.get()
                .load(user.getPhotoUrl())
                .transform(new CircleTransform())
                .into(imageView);


    }

    private void changeGenderPref(boolean gender){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(Constants.GENDER,gender).apply();
        viewModel.updateGenderPref();

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        if (view.getId() == R.id.radioButton_male) {
            female.setChecked(false);
            male.setChecked(true);
            changeGenderPref(true);
            // Pirates are the best
        } else {
            female.setChecked(true);
            male.setChecked(false);
            changeGenderPref(false);
        }
    }
}
