package com.sanvaad.View.Home.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.sanvaad.Model.UserData.db.Entity.Conversation;
import com.sanvaad.R;
import com.sanvaad.View.Home.Adapter.BrowseChatAdapter;
import com.sanvaad.View.Home.HomeActivity;
import com.sanvaad.View.Home.Listener.BrowseChatsListener;
import com.sanvaad.ViewModel.HomeActivityViewModel;

import java.util.List;

public class BrowseChatsFragment extends Fragment {

    @NotNull
    HomeActivityViewModel viewModel;

    public BrowseChatsFragment(){}

    public BrowseChatsFragment(BrowseChatsListener listener, HomeActivityViewModel viewModel) {
        this.viewModel=viewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_browse_chats, container, false);
       }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.bcf_rv);

        BrowseChatAdapter adapter = new BrowseChatAdapter((HomeActivity) getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,true));
        recyclerView.setAdapter(adapter);

        if(viewModel!=null)
            viewModel.getConversations(FirebaseAuth.getInstance().getCurrentUser().getUid()).observe(getViewLifecycleOwner(), new Observer<List<Conversation>>() {
            @Override
            public void onChanged(List<Conversation> conversations) {
                adapter.setConversationList(conversations);
                adapter.notifyDataSetChanged();
            }
        });

    }
}