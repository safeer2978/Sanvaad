package com.sanvaad.View.Home.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sanvaad.Model.UserData.db.Entity.Conversation;
import com.sanvaad.R;
import com.sanvaad.View.Home.Listener.BrowseChatsListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BrowseChatAdapter extends RecyclerView.Adapter<BrowseChatAdapter.ViewHolder> {

    BrowseChatsListener listener;
    Context context;


    public BrowseChatAdapter(BrowseChatsListener listener){
        this.listener = listener;
    }

    public void setConversationList(List<Conversation> conversationList) {
        this.conversationList = conversationList;
    }

    List<Conversation> conversationList = new ArrayList<>();


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_bcf_chats, parent, false);
        return new BrowseChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(conversationList.size()>0){
        Conversation conversation = conversationList.get(position);
        holder.title.setText(conversation.getTitle());
        DateFormat formatter = SimpleDateFormat.getDateInstance();
        holder.date.setText(formatter.format(new Date(conversation.getCdate())));

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.openChat(conversation);
            }
        });
        }
    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView date, title;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.item_bcf_date);
            title = itemView.findViewById(R.id.item_bcf_title);
            constraintLayout = itemView.findViewById(R.id.item_bcf_cl);
        }
    }
}
