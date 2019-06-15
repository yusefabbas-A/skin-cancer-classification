package com.yojoo.skincancerclassifier.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.yojoo.skincancerclassifier.Connection.ConnectionManager;
import com.yojoo.skincancerclassifier.Connection.SkinAPI;
import com.yojoo.skincancerclassifier.Data.MyResponse;
import com.yojoo.skincancerclassifier.Data.Sample;
import com.yojoo.skincancerclassifier.Data.SampleResult;
import com.yojoo.skincancerclassifier.R;
import com.yojoo.skincancerclassifier.activity.ResultActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultBottomSheetDialog extends BottomSheetDialogFragment {
    private PieChart MPieChart;
    TextView TheResult;
    MyResponse myResponse;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet, container,false);
        MPieChart = v.findViewById(R.id.pieChart_frag);
        TheResult = v.findViewById(R.id.result_txt_frag);
        getSamplesChart();
        return v;
    }

    private void getSamplesChart(){
        SkinAPI skinAPI = ConnectionManager.getApiServices();
        Call<SampleResult> call = skinAPI.getSamples();
        call.enqueue(new Callback<SampleResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<SampleResult> call, Response<SampleResult> response) {
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        SampleResult sampleResult = response.body();
                        List<PieEntry> pieEntries = new ArrayList<>();
                        List<Sample> samples = response.body().getSamples();
                        for (Sample sample : samples ) {
                            pieEntries.add(new PieEntry(sample.getScore(), sample.getType()));
                        }
                        MPieChart.setVisibility(View.VISIBLE);
                        MPieChart.animateXY(4000, 4000);
                        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Samples");
                        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                        PieData pieData = new PieData(pieDataSet);
                        pieData.setValueTextSize(10f);
                        pieData.setValueTextColor(Color.GREEN);
                        MPieChart.setData(pieData);
                        MPieChart.setDrawHoleEnabled(false);
                        Description description = new Description();
                        description.setText("Scores for Samples");
                        MPieChart.setDescription(description);
                        MPieChart.invalidate();
                        TheResult.setText("The Sample is Classified as: " + sampleResult.getClassifiedAs());
                    }
                }
            }
            @Override
            public void onFailure(Call<SampleResult> call, Throwable t) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }








}
