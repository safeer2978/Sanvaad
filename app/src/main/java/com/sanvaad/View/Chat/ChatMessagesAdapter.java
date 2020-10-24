package com.sanvaad.View.Chat;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.sanvaad.Model.Constants;
import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Message;
import com.sanvaad.R;
import com.sanvaad.ViewModel.ChatActivityViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatMessagesAdapter extends RecyclerView.Adapter<com.sanvaad.View.Chat.ChatMessagesAdapter.ViewHolder> {

    Context context;

    ChatActivityViewModel viewModel;

    public ChatMessagesAdapter(Context context, ChatActivityViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    public void setUserList(LiveData<List<Contact>> contacts) {
        this.contactList = contacts;
    }
    public void setMessageList(List<Message> messages){
        this.messageList = messages;
    }

    List<Message> messageList;
    LiveData<List<Contact>> contactList;


    @NonNull
    @Override
    public com.sanvaad.View.Chat.ChatMessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_messagebubble, parent, false);
        return new com.sanvaad.View.Chat.ChatMessagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messageList.get(position);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) holder.linearLayout.getLayoutParams();

        if(message.getContactID() == Constants.USER_ID){
            holder.speakerLinerLayout.setVisibility(View.GONE);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.speakText(message.getMessage());
                }
            });
            layoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET;
        }else{
            holder.textViewSpeaker.setText("Speaker");
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET;
        }
        holder.linearLayout.setLayoutParams(layoutParams);
        holder.textViewMessage.setText(message.getMessage());
        Date date = new Date(message.getMessageDate());
        holder.textViewTime.setText(date.getHours()+":"+date.getMinutes());

    }

/*
    @Override
    public void onBindViewHolder(@NonNull com.sanvaad.View.Chat.ChatMessagesAdapter.ViewHolder holder, int messagePosition, int contactPosition) {
        //Message messsage= messageList.get(messagePosition);
        Contact contact = contactList.get(contactPosition);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,string,Toast.LENGTH_SHORT).show();
            }
        });
*/
/*        holder.textView.setText(messsage.getMessage());
        holder.textViewSpeaker.setText(contact.getName());
        holder.textViewTime.setText(messsage.getMessageDate());*//*


    }
*/

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
        LinearLayout speakerLinerLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            speakerLinerLayout = itemView.findViewById(R.id.item_speakername_ll);
            constraintLayout=itemView.findViewById(R.id.messagebubble_contstraint_layout);
            linearLayout = itemView.findViewById(R.id.messagebubble_linearLayout);
            textViewTime = itemView.findViewById(R.id.messagebubble_time_textview);
            textViewSpeaker = itemView.findViewById(R.id.messagebubble_speaker_textview);
            textViewMessage = itemView.findViewById(R.id.messagebubble_message_textview);
            button = itemView.findViewById(R.id.messagebubble_speak_btn);
            imageView = itemView.findViewById(R.id.imageView_chooseContact);
        }
    }
}