package com.sanvaad.View.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sanvaad.CircleTransform;
import com.sanvaad.Model.Entity.User;
import com.sanvaad.R;
import com.sanvaad.ViewModel.HomeActivityViewModel;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    HomeActivityViewModel viewModel;

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
}
