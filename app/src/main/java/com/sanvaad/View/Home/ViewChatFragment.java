package com.sanvaad.View.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sanvaad.Model.Entity.Conversation;
import com.sanvaad.Model.Entity.Message;
import com.sanvaad.R;
import com.sanvaad.View.Chat.ChatMessagesAdapter;
import com.sanvaad.View.Chat.ParticipantsAdapter;
import com.sanvaad.ViewModel.HomeActivityViewModel;

import java.util.List;

public class ViewChatFragment extends Fragment {

    HomeActivityViewModel viewModel;

    Conversation conversation;

    ViewChatFragment(Conversation conversation, HomeActivityViewModel homeActivityViewModel){
        this.conversation=conversation;
        viewModel=homeActivityViewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_chat_fragement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        List<List> lists= viewModel.getListDataForConversation(conversation);

        ParticipantsAdapter participantsAdapter = new ParticipantsAdapter(getContext(),viewModel);
        RecyclerView participants = view.findViewById(R.id.vcf_participants_rv);
        participants.setAdapter(participantsAdapter);
        participantsAdapter.setList(lists.get(1));

        participantsAdapter.notifyDataSetChanged();

        RecyclerView messages = view.findViewById(R.id.vcf_messages_rv);
        ChatMessageAdapter chatMessagesAdapter = new ChatMessageAdapter(viewModel);
        messages.setAdapter(chatMessagesAdapter);
        chatMessagesAdapter.setMessagesList(viewModel.getMessages(conversation));
        chatMessagesAdapter.notifyDataSetChanged();
        messages.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.w("MESSAGES", lists.get(0).toString());


    }
}