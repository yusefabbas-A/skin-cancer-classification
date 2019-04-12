package com.yojoo.skincancerclassifier.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.yojoo.skincancerclassifier.Connection.ConnectionManager;
import com.yojoo.skincancerclassifier.Connection.SkinAPI;
import com.yojoo.skincancerclassifier.Data.SampleResult;
import com.yojoo.skincancerclassifier.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {
        private PieChart MPieChart;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        MPieChart = findViewById(R.id.pieChart);

       getSamplesChart();

//        getResults();
    }

//    private void getResults() {
//        SkinAPI skinAPI = ConnectionManager.getApiServices();
//        Call<SampleResult> call = skinAPI.getSamples();
//        call.enqueue(new Callback<SampleResult>() {
//            @Override
//            public void onResponse(Call<SampleResult> call, Response<SampleResult> response) {
//                if (response.isSuccessful()){
//                    if (response.body() != null){
//                        SampleResult sampleResult = response.body();
//                        Toast.makeText(ResultActivity.this, "The message is: "+ sampleResult.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SampleResult> call, Throwable t) {
//                Log.v("Response gotten is", t.getMessage());
//            }
//        });
//    }


    private void getSamplesChart(){
        Call<List<SampleResult>> call = ConnectionManager.getApiServices().getSamples();
        call.enqueue(new Callback<List<SampleResult>>() {
            @Override
            public void onResponse(Call<List<SampleResult>> call, Response<List<SampleResult>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(ResultActivity.this, "this is Sparta", Toast.LENGTH_SHORT).show();
                    if (response.body() != null) {
                        List<PieEntry> pieEntries = new ArrayList<>();

                        for (SampleResult sampleResult : response.body()) {
                            pieEntries.add(new PieEntry(sampleResult.getOpStatus(),sampleResult.getSamplesScores()));
                        }
                        MPieChart.setVisibility(View.VISIBLE);
                        MPieChart.animateXY(5000, 5000);
                        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Samples Scores");
                        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                        PieData pieData = new PieData(pieDataSet);
                        MPieChart.setData(pieData);
                        Description description = new Description();
                        description.setText("Scores for Samples");
                        MPieChart.setDescription(description);
                        MPieChart.invalidate();

                    }

                }

            }


            @Override
            public void onFailure(Call<List<SampleResult>> call, Throwable t) {
                Toast.makeText(ResultActivity.this, "ERROR", Toast.LENGTH_SHORT).show();

            }

        });

    }
}
