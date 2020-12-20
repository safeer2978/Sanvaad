package com.sanvaad.View.Chat.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sanvaad.Model.UserData.db.Entity.CommonMessage;
import com.sanvaad.R;
import com.sanvaad.ViewModel.ChatActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class CommonMessageAdapter extends RecyclerView.Adapter<CommonMessageAdapter.ViewHolder> {

    Context context;
ChatActivityViewModel viewModel;
    public CommonMessageAdapter(Context context, ChatActivityViewModel viewModel) {
        this.list = new ArrayList<>();
        this.context = context;
        this.viewModel = viewModel;
    }

    public void setList(List<CommonMessage> list) {
        this.list = list;
    }

    List<CommonMessage> list;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_commonmessages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommonMessage commonMessage= list.get(position);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        viewModel.onUserText(commonMessage.getMessage());
            }
        });
        holder.textView.setText(commonMessage.getMessage());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.commonMessage_constraint);
            textView = itemView.findViewById(R.id.commonMessage_textview);
        }
    }
}

