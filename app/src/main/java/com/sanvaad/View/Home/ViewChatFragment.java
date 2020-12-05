package com.sanvaad.View.Home;

import android.media.Image;
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
import android.widget.ImageView;

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

    BackListener listener;
    ViewChatFragment(Conversation conversation, HomeActivityViewModel homeActivityViewModel,BackListener listener){
        this.conversation=conversation;
        viewModel=homeActivityViewModel;
        this.listener=listener;
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
        participants.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL, false));
        participantsAdapter.setList(viewModel.getParticipants(conversation));
        participantsAdapter.notifyDataSetChanged();

        RecyclerView messages = view.findViewById(R.id.vcf_messages_rv);
        ChatMessageAdapter chatMessagesAdapter = new ChatMessageAdapter(viewModel);
        messages.setAdapter(chatMessagesAdapter);
        chatMessagesAdapter.setMessagesList(viewModel.getMessages(conversation));
        chatMessagesAdapter.notifyDataSetChanged();
        messages.setLayoutManager(new LinearLayoutManager(getContext()));


        ImageView back = view.findViewById(R.id.vcf_back_iv);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBack();
            }
        });


    }
}