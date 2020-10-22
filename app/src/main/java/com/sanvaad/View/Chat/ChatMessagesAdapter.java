package com.sanvaad.View.Chat;
import android.content.Context;
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
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Message;
import com.sanvaad.R;

import java.util.ArrayList;
import java.util.List;

public class ChatMessagesAdapter extends RecyclerView.Adapter<com.sanvaad.View.Chat.ChatMessagesAdapter.ViewHolder> {

    Context context;

    public ChatMessagesAdapter(Context context) {
        this.messageList = new ArrayList<>();
        this.contactList = new ArrayList<>();
        this.context = context;
    }

    public void setUserList(List<Contact> contacts) {
        this.contactList = contacts;
    }
    public void setMessageList(LiveData<List<Message>> messages){
        this.messageList = messages;
    }

    LiveData<List<Message>> messageList;
    List<Contact> contactList;


    @NonNull
    @Override
    public com.sanvaad.View.Chat.ChatMessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_messagebubble, parent, false);
        return new com.sanvaad.View.Chat.ChatMessagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.sanvaad.View.Chat.ChatMessagesAdapter.ViewHolder holder, int messagePosition, int contactPosition) {
        Message messsage= messageList.get(messagePosition);
        Contact contact = contactList.get(contactPosition);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,string,Toast.LENGTH_SHORT).show();
            }
        });
        holder.textView.setText(messsage.getMessage());
        holder.textViewSpeaker.setText(contact.getName());
        holder.textViewTime.setText(messsage.getMessageDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        TextView textViewSpeaker;
        TextView textViewTime;
        LinearLayout linearLayout;
        Button button;
        ConstraintLayout constraintLayout;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.messagebubble_contstraint_layout);
            linearLayout = itemView.findViewById(R.id.messagebubble_linearLayout);
            textViewTime = itemView.findViewById(R.id.messagebubble_time_textview);
            textViewSpeaker = itemView.findViewById(R.id.messagebubble_speaker_textview);
            textView = itemView.findViewById(R.id.messagebubble_message_textview);
            button = itemView.findViewById(R.id.messagebubble_speak_btn);
            imageView = itemView.findViewById(R.id.imageView_chooseContact);
        }
    }
}