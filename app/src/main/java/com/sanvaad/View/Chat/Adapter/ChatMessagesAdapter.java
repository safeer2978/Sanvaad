package com.sanvaad.View.Chat.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sanvaad.Model.Util.Constants;
import com.sanvaad.Model.UserData.db.Entity.Contact;
import com.sanvaad.Model.UserData.db.Entity.Message;
import com.sanvaad.R;
import com.sanvaad.ViewModel.ChatActivityViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ViewHolder> {

    Context context;

    ChatActivityViewModel viewModel;

    public ChatMessagesAdapter(Context context, ChatActivityViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
        messageList = new ArrayList<>();

    }

    public void setContactList(List<Contact> contacts) {
        this.contactList = contacts;
    }
    public void setMessageList(List<Message> messages){
        this.messageList = messages;
    }

    List<Message> messageList;
    List<Contact> contactList;


    @NonNull
    @Override
    public ChatMessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatMessagesAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_messagebubble, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messageList.get(position);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) holder.cardView.getLayoutParams();

        layoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        layoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;


        if(message.getContactID() == Constants.USER_ID){
            holder.speakerLinerLayout.setVisibility(View.GONE);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.speakText(message.getMessage());
                }
            });
            layoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET;
        }
        else {
            holder.speakerLinerLayout.setVisibility(View.VISIBLE);
            List<Contact> contacts = viewModel.getParticipantsList();
            for (Contact c : contacts) {
                if (message.getContactID() == c.getId()) {
                    holder.textViewSpeaker.setText(c.getName());
                    holder.textViewSpeaker.setTextColor(viewModel.getColorInteger(c));
                    holder.recyclerView.setVisibility(View.GONE);
                    break;
                }
                else{
                    holder.textViewSpeaker.setText("Speaker");
                    holder.recyclerView.setLayoutManager(new LinearLayoutManager(context,RecyclerView.HORIZONTAL,true));
                    AssignParticipantAdapter adapter = new AssignParticipantAdapter(context,viewModel);
                    adapter.setMessagePosition(position);
                    holder.recyclerView.setAdapter(adapter);
                    adapter.setList(contactList);
                    adapter.notifyDataSetChanged();
                    holder.recyclerView.setVisibility(View.VISIBLE);
                }
            }
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET;
        }
        holder.cardView.setLayoutParams(layoutParams);
        holder.textViewMessage.setText(message.getMessage());
        Date date = new Date(message.getMessageDate());
        holder.textViewTime.setText(date.getHours()+":"+date.getMinutes());

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewMessage;
        TextView textViewSpeaker;
        TextView textViewTime;
        LinearLayout linearLayout;
        Button button;
        ConstraintLayout constraintLayout;
        ImageView imageView;
        ConstraintLayout speakerLinerLayout;
        RecyclerView recyclerView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.messagebubble_card_view);
            speakerLinerLayout = itemView.findViewById(R.id.item_speakername_ll);
            constraintLayout=itemView.findViewById(R.id.messagebubble_contstraint_layout);
            linearLayout = itemView.findViewById(R.id.messagebubble_linearLayout);
            textViewTime = itemView.findViewById(R.id.messagebubble_time_textview);
            textViewSpeaker = itemView.findViewById(R.id.messagebubble_speaker_textview);
            textViewMessage = itemView.findViewById(R.id.messagebubble_message_textview);
            button = itemView.findViewById(R.id.messagebubble_speak_btn);
            imageView = itemView.findViewById(R.id.imageView_chooseContact);
            recyclerView = itemView.findViewById(R.id.messagebubble_speaker_recyclerview);
        }
    }
}