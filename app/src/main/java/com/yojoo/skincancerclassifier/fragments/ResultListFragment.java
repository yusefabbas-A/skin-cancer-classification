package com.yojoo.skincancerclassifier.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yojoo.skincancerclassifier.Data.Report;
import com.yojoo.skincancerclassifier.R;
import com.yojoo.skincancerclassifier.adabter.ReportAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private View fragmentView;

    public ResultListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_result_list, container, false);

        ArrayList<Report> reportList = new ArrayList<>();
        reportList.add(new Report("1","nv","2019"));
        reportList.add(new Report("2","nvc","2019"));
        reportList.add(new Report("3","bn","2019"));
        reportList.add(new Report("4","dv","2019"));
        reportList.add(new Report("5","nv","2018"));
        reportList.add(new Report("6","bgv","2018"));
        reportList.add(new Report("7","bc","2018"));
        reportList.add(new Report("8","sv","2018"));
        reportList.add(new Report("9","gs","2018"));

        recyclerView = fragmentView.findViewById(R.id.result_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new ReportAdapter(reportList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        return fragmentView;
    }


}
