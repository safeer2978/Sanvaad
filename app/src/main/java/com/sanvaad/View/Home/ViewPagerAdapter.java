package com.sanvaad.View.Home;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.sanvaad.Model.Constants;
import com.sanvaad.ViewModel.HomeActivityViewModel;

import org.apache.http.client.methods.AbstractExecutionAwareRequest;

public class ViewPagerAdapter extends FragmentStateAdapter {

    int tabCount=3;

    public void setInstance(HomeActivity instance) {
        this.instance = instance;
    }

    public void setViewModel(HomeActivityViewModel viewModel) {
        this.viewModel = viewModel;
    }

    HomeActivity instance;
    HomeActivityViewModel viewModel;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new Fragment();
        switch (position){
            case Constants.TAB_CHAT:
                fragment = new BrowseChatsFragment(instance,viewModel);
                break;
            case Constants.TAB_CONTACTS:
                fragment = new ContactsFragment(viewModel);
                break;
            case Constants.TAB_PROFILE:
                fragment = new ProfileFragment(viewModel);
                break;

        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return tabCount;
    }
}
