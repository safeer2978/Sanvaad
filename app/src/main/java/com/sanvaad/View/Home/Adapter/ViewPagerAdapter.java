package com.sanvaad.View.Home.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.sanvaad.Model.Util.Constants;
import com.sanvaad.View.Home.Fragment.BrowseChatsFragment;
import com.sanvaad.View.Home.Fragment.ContactsFragment;
import com.sanvaad.View.Home.HomeActivity;
import com.sanvaad.View.Home.Fragment.ProfileFragment;
import com.sanvaad.ViewModel.HomeActivityViewModel;

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
