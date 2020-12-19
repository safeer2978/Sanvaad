package com.sanvaad.View.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sanvaad.CircleTransform;
import com.sanvaad.ContactsViewModel;
import com.sanvaad.Model.Constants;
import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.R;
import com.sanvaad.ViewModel.HomeActivityViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    Context context;


    ContactsViewModel viewModel;

    public ContactsAdapter(Context context, ContactsViewModel viewModel) {
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
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_contacts, parent, false);
        return new ContactsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        Contact contact= list.get(position);
        holder.editBtn.setVisibility(View.VISIBLE);
        holder.onclickConstraintLayout.setVisibility(View.VISIBLE);
        holder.onclickConstraintLayout.setFocusable(true);

        if(viewModel.parent()==Constants.CHAT_VIEWMODEL){
            holder.editBtn.setVisibility(View.GONE);
            holder.onclickConstraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.addParticipant(contact);
                }
            });
        }

        holder.textView.setFocusable(false);

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.onclickConstraintLayout.setVisibility(View.GONE);
                holder.button.setVisibility(View.VISIBLE);
                holder.editBtn.setVisibility(View.GONE);
                holder.textView.setFocusableInTouchMode(true);
                holder.textView.setFocusable(true);
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contact.setName(holder.textView.getText().toString());
                        viewModel.updateContact(contact);
                        holder.textView.setFocusable(false);
                        holder.button.setVisibility(View.GONE);
                        holder.deleteBtn.setVisibility(View.GONE);
                        holder.editBtn.setVisibility(View.VISIBLE);
                        holder.onclickConstraintLayout.setVisibility(View.VISIBLE);
                    }
                });
                holder.deleteBtn.setVisibility(View.VISIBLE);
                holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.deleteContact(contact);
                        holder.deleteBtn.setVisibility(View.GONE);
                    }
                });
            }
        });

        holder.textView.setText(contact.getName());
        Picasso.get()
                .load(Constants.DUMMY_CONTACT_IMAGE_LINK)
                .transform(new CircleTransform())
                .into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ConstraintLayout constraintLayout, onclickConstraintLayout;
        ImageView imageView;
        Button button;
        Button deleteBtn;
        Button editBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout=itemView.findViewById(R.id.itemc_cl);
            textView = itemView.findViewById(R.id.itemc_et);
            imageView = itemView.findViewById(R.id.itemc_image);
            button = itemView.findViewById(R.id.itemc_btn);
            deleteBtn = itemView.findViewById(R.id.itemc_deletebtn);
            editBtn = itemView.findViewById(R.id.itemc_edit_btn);
            onclickConstraintLayout = itemView.findViewById(R.id.contacts_cl_for_onclick);

        }
    }
}
