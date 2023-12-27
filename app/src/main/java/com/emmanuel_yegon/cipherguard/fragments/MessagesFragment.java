package com.emmanuel_yegon.cipherguard.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.emmanuel_yegon.cipherguard.Model.Message;
import com.emmanuel_yegon.cipherguard.adapter.MessageAdapter;
import com.emmanuel_yegon.cipherguard.database.RoomDB;
import com.emmanuel_yegon.cipherguard.databinding.FragmentMessagesBinding;

import java.util.ArrayList;
import java.util.List;


public class MessagesFragment extends Fragment {

    FragmentMessagesBinding binding;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;
    RoomDB database;
    List<Message> messageList = new ArrayList<>();
    List<Message> tempList = null;

    EditText txtSearch;
    RelativeLayout relativeLayout;


    public MessagesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMessagesBinding.inflate(inflater, container, false);

        relativeLayout = binding.layoutMessage;

        recyclerView = binding.rvMessage;
        txtSearch = binding.txtSearch;

        database = RoomDB.getInstance(getActivity());
        messageList = database.mainDao().getAllMessages();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        messageAdapter = new MessageAdapter(messageList, getContext(), database);
        recyclerView.setAdapter(messageAdapter);

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tempList = new ArrayList<>();
                for (int data = 0; data < messageList.size(); data++) {
                    if (messageList.get(data).getPlain_text().toLowerCase().startsWith(txtSearch.getText().toString().toLowerCase
                            ())) {
                        tempList.add(messageList.get(data));
                    }
                }

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
                messageAdapter = new MessageAdapter(tempList, getActivity(), database);
                recyclerView.setAdapter(messageAdapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return binding.getRoot();
    }
}