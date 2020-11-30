package com.sanvaad.View.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sanvaad.Model.Entity.Contact;
import com.sanvaad.R;
import com.sanvaad.ViewModel.HomeActivityViewModel;

import java.util.List;
import java.util.Objects;

public class ContactsFragment extends Fragment {

    HomeActivityViewModel viewModel;
    public ContactsFragment(HomeActivityViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.ha_cf_rv);

        ContactsAdapter adapter = new ContactsAdapter(getContext(), viewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getContacts().observe(Objects.requireNonNull(getActivity()), new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                adapter.setList(contacts);
                adapter.notifyDataSetChanged();
                //recyclerView.notify();
            }
        });

        EditText editText = view.findViewById(R.id.ha_cf_et);
        ImageView imageView = view.findViewById(R.id.ha_cf_iv);
        Button button = view.findViewById(R.id.ha_cf_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact contact = new Contact();
                contact.setName(editText.getText().toString());
                viewModel.saveContact(contact);
            }
        });
    }
}