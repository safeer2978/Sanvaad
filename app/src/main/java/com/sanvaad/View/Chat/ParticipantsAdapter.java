
package com.sanvaad.View.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sanvaad.CommonParticipantsViewModel;
import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.R;
import com.sanvaad.ViewModel.ChatActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ViewHolder> {

    Context context;

    CommonParticipantsViewModel viewModel;
    public ParticipantsAdapter(Context context, CommonParticipantsViewModel viewModel) {
        this.list = new ArrayList<>();
        this.context = context;
        this.viewModel = viewModel;
    }

    public void setList(List<Contact> list) {
        this.list = list;
    }

    List<Contact> list;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_participants, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact= list.get(position);
        holder.textView.setText(contact.getName());
         int color = viewModel.getColorInteger(contact);
        // int color = Color.argb(255, rnd.nextInt(128), rnd.nextInt(128), rnd.nextInt(128));
        holder.textView.setTextColor(color);

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
            textView = itemView.findViewById(R.id.participant_textview);
        }
    }
}