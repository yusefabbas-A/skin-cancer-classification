package com.yojoo.skincancerclassifier.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yojoo.skincancerclassifier.Connection.ConnectionManager;
import com.yojoo.skincancerclassifier.Data.Messages;
import com.yojoo.skincancerclassifier.Data.Report;
import com.yojoo.skincancerclassifier.Data.SampleResult;
import com.yojoo.skincancerclassifier.Database.DatabaseManager;
import com.yojoo.skincancerclassifier.R;
import com.yojoo.skincancerclassifier.activity.MainActivity;
import com.yojoo.skincancerclassifier.adabter.ReportAdapter;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultListFragment extends Fragment implements Callback<SampleResult> {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private ReportAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private View fragmentView;
    String theKey;
    String theClass;
    SwipeRefreshLayout swipeRefreshLayout;

    private List<Report> reports;

//    SenderFragmentListener Listener;
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
        swipeRefreshLayout = fragmentView.findViewById(R.id.swipe);
        buildRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
                newInstance();
                adapter.notifyDataSetChanged();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },3000);
            }
        });
        return fragmentView;
    }
    public static ResultListFragment newInstance(){
        return new ResultListFragment();
    }

    public void buildRecyclerView(){

        reports = DatabaseManager.getInstance(getActivity()).getAllReports();
        recyclerView = fragmentView.findViewById(R.id.result_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new ReportAdapter(reports);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ReportAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                positionId = position +1;
                theKey = DatabaseManager.getInstance(getActivity()).getKey(positionId);
                Toast.makeText(getActivity(), "position"+positionId, Toast.LENGTH_SHORT).show();
                theClass =  DatabaseManager.getInstance(getActivity()).getClassification(positionId);

                if (theClass == null){
                    callResult();
                }else {
                    Toast.makeText(getActivity(), "Already Got Result", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

public void receiveFromHome(List<Report> reportList){
        reports = reportList;
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
                DatabaseManager.getInstance(getActivity()).insertMessage(new Messages(sampleResult.getMessage(),getCurrentDate()));
                Log.d(TAG,"inserted");
                Toast.makeText(getActivity(), "Msg Inserted", Toast.LENGTH_SHORT).show();
                updateAc();
//                ResultBottomSheetDialog bottomSheetDialog = new ResultBottomSheetDialog();
//                bottomSheetDialog.show(getActivity().getSupportFragmentManager(), "myBottomSheet");
            }
        }
    }

    private void updateAc() {
        Intent refresh = new Intent(getActivity(), MainActivity.class);
        startActivity(refresh);
        getActivity().finish();
    }

    @Override
    public void onFailure(Call<SampleResult> call, Throwable t) {
        Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
    }


    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return DateFormat.getDateInstance().format(calendar.getTime());
    }



//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if(adapter != null){
//
//            Toast.makeText(getActivity(), "hello list", Toast.LENGTH_SHORT).show();
//        }
//
//
//    }

}
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof SenderFragmentListener){
//            Listener = (SenderFragmentListener) context;
//        } else {
//            throw new RuntimeException(context.toString()+"must implement SenderFragmentListener");
//        }
//    }
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        Listener = null;
//    }
//@Override
//public void receiveId(int id) {
//    positionId = id;
//}
//
//public interface SenderFragmentListener{
//    void positionSender(int positionId);
//}