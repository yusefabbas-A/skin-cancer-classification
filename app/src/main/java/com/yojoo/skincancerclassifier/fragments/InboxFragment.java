package com.yojoo.skincancerclassifier.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yojoo.skincancerclassifier.Data.Messages;
import com.yojoo.skincancerclassifier.Data.Report;
import com.yojoo.skincancerclassifier.Database.DatabaseManager;
import com.yojoo.skincancerclassifier.R;
import com.yojoo.skincancerclassifier.adabter.MessageAdapter;
import com.yojoo.skincancerclassifier.adabter.ReportAdapter;

import java.util.List;

public class InboxFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private View fragmentView;


    public InboxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.fragment_inbox, container, false);
        List<Messages> messages = DatabaseManager.getInstance(getActivity()).getAllMessages();

        recyclerView = fragmentView.findViewById(R.id.messages_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new MessageAdapter(messages);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return fragmentView;
    }

}
