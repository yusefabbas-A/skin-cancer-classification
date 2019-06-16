package com.yojoo.skincancerclassifier.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yojoo.skincancerclassifier.Connection.ConnectionManager;
import com.yojoo.skincancerclassifier.Data.Report;
import com.yojoo.skincancerclassifier.Data.SampleResult;
import com.yojoo.skincancerclassifier.Database.DatabaseManager;
import com.yojoo.skincancerclassifier.R;
import com.yojoo.skincancerclassifier.adabter.ReportAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultListFragment extends Fragment implements ResultBottomSheetDialog.ReceiveIdInterface, Callback<SampleResult> {

    private RecyclerView recyclerView;
    private ReportAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private View fragmentView;
    String theKey;
    SenderFragmentListener Listener;
    int positionId;
    int nPosition;


    public ResultListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_result_list, container, false);

//        ArrayList<Report> reportList = new ArrayList<>();
//        reportList.add(new Report("1",dates,"sad"));

        buildRecyclerView();

        return fragmentView;
    }

    public void buildRecyclerView(){
       List<Report> reports = DatabaseManager.getInstance(getActivity()).getAllReports();

        recyclerView = fragmentView.findViewById(R.id.result_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new ReportAdapter(reports);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new ReportAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                positionId = position +1;
                theKey = DatabaseManager.getInstance(getActivity()).getKey(positionId);
                Toast.makeText(getActivity(), "position"+positionId, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "the key is"+ theKey, Toast.LENGTH_SHORT).show();
                Listener.positionSender(positionId);
                callResult();

            }
        });
    }

    public void callResult(){
        ConnectionManager.getInstance().getSamples(theKey).enqueue(this);
    }


    @Override
    public void onResponse(Call<SampleResult> call, Response<SampleResult> response) {
        if (response.isSuccessful()){
            if (response.body() != null) {
                SampleResult sampleResult = response.body();
                assert sampleResult != null;
                DatabaseManager.getInstance(getActivity()).UpdateClass(positionId, sampleResult.getClassifiedAs());
                adapter.notifyItemChanged(nPosition);
//                ResultBottomSheetDialog bottomSheetDialog = new ResultBottomSheetDialog();
//                bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "myBottomSheet");
            }
        }
    }

    @Override
    public void onFailure(Call<SampleResult> call, Throwable t) {
        Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void receiveId(int id) {
        positionId = id;
    }

    public interface SenderFragmentListener{
        void positionSender(int positionId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SenderFragmentListener){
            Listener = (SenderFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()+"must implement SenderFragmentListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Listener = null;
    }

}
