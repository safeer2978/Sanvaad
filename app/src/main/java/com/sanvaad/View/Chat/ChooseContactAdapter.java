package com.sanvaad.View.Chat;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.sanvaad.CircleTransform;
import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.R;
import com.sanvaad.ViewModel.ChatActivityViewModel;
import com.sanvaad.messageListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChooseContactAdapter extends RecyclerView.Adapter<com.sanvaad.View.Chat.ChooseContactAdapter.ViewHolder> {

    Context context;


    ChatActivityViewModel viewModel;

    public ChooseContactAdapter(Context context, ChatActivityViewModel viewModel) {
        this.list = new ArrayList<>();
        this.context = context;
        this.viewModel=viewModel;
    }

    public void setList(List<Contact> list) {
        this.list = list;
    }

    List<Contact> list;

    @NonNull
    @Override
    public com.sanvaad.View.Chat.ChooseContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_choosecontact, parent, false);
        return new com.sanvaad.View.Chat.ChooseContactAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.sanvaad.View.Chat.ChooseContactAdapter.ViewHolder holder, int position) {
        Contact contact= list.get(position);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,string,Toast.LENGTH_SHORT).show();
                viewModel.addParticipant(contact);

            }
        });
        holder.textView.setText(contact.getName());
        Picasso.get()
                .load(contact.getImglink())
                //.resize(30,30)
                .transform(new CircleTransform())
                .into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ConstraintLayout constraintLayout;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.constraintLayout_chooseContact);
            textView = itemView.findViewById(R.id.textView_chooseContact);
            imageView = itemView.findViewById(R.id.imageView_chooseContact);
        }
    }
}
