package com.sanvaad.View.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.Model.Entity.Message;
import com.sanvaad.R;
import com.sanvaad.ViewModel.ChatActivityViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AssignParticipantAdapter extends RecyclerView.Adapter<AssignParticipantAdapter.ViewHolder>{
    Context context;

    ChatActivityViewModel chatActivityViewModel;
    public AssignParticipantAdapter(Context context, ChatActivityViewModel viewModel) {
        this.list = new ArrayList<>();
        this.context = context;
        this.chatActivityViewModel = viewModel;
    }

    Message message;
    public void setMessage(Message message){this.message = message;}
    public void setList(List<Contact> list) {
        this.list = list;
    }

    List<Contact> list;
    int messagePosition;

    public void setMessagePosition(int messagePosition) {
        this.messagePosition = messagePosition;
    }

    @NonNull
    @Override
    public AssignParticipantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_assign_participant, parent, false);
        return new AssignParticipantAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignParticipantAdapter.ViewHolder holder, int position) {
        Contact contact= list.get(position);
        int colorInteger =chatActivityViewModel.getColorInteger(contact);
        holder.constraintLayout.setBackgroundColor(colorInteger);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatActivityViewModel.assignContactToMessage(messagePosition,contact);
                Toast.makeText(context,"Contact assigned", Toast.LENGTH_SHORT).show();
                chatActivityViewModel.updateMessageBubble();
                notifyDataSetChanged();
            }
        });

        //int color = Color.argb(255, rnd.nextInt(128), rnd.nextInt(128), rnd.nextInt(128));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.item_ap_cl);
        }
    }
}
