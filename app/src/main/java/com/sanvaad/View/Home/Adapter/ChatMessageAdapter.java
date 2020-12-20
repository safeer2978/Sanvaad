package com.sanvaad.View.Home.Adapter;


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
import androidx.recyclerview.widget.RecyclerView;

import com.sanvaad.Model.Util.Constants;
import com.sanvaad.Model.UserData.db.Entity.Contact;
import com.sanvaad.Model.UserData.db.Entity.Message;
import com.sanvaad.R;
import com.sanvaad.ViewModel.HomeActivityViewModel;

import java.util.Date;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ViewHolder> {


    public void setMessagesList(List<Message> messagesList) {
        this.messagesList = messagesList;
    }

    List<Message> messagesList;

    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_messagebubble, parent, false);
        return new ChatMessageAdapter.ViewHolder(view);
    }


    HomeActivityViewModel viewModel;
    public ChatMessageAdapter(HomeActivityViewModel viewModel){
        this.viewModel=viewModel;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message = messagesList.get(position);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) holder.cardView.getLayoutParams();


        if(message.getContactID() == Constants.USER_ID){
            holder.speakerLinerLayout.setVisibility(View.GONE);
            holder.button.setVisibility(View.INVISIBLE);
            layoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET;
        }else {
            holder.textViewSpeaker.setText("Speaker");
                    Contact c = viewModel.getContact(message.getContactID());
                    if(c!=null) {
                        holder.textViewSpeaker.setText(c.getName());
                        holder.textViewSpeaker.setTextColor(viewModel.getColorInteger(c));
                    }holder.recyclerView.setVisibility(View.GONE);
            layoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET;
            }


        holder.cardView.setLayoutParams(layoutParams);
        holder.textViewMessage.setText(message.getMessage());
        Date date = new Date(message.getMessageDate());
        holder.textViewTime.setText(date.getHours()+":"+date.getMinutes());

        }


    @Override
    public int getItemCount() {
        return messagesList.size();
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
