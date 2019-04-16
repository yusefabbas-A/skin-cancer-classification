package com.yojoo.skincancerclassifier.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import com.yojoo.skincancerclassifier.Data.Sample;
import com.yojoo.skincancerclassifier.Data.SampleResult;
import com.yojoo.skincancerclassifier.R;
import com.yojoo.skincancerclassifier.adabter.SamplesAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultActivity extends AppCompatActivity {
        private PieChart MPieChart;
        TextView TheResult,scores;
        RecyclerView recycler;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        MPieChart = findViewById(R.id.pieChart);
        TheResult = findViewById(R.id.result_txt);
//        scores = findViewById(R.id.Score_list);
//        recycler = findViewById(R.id.recyclerList);
//        recycler.setHasFixedSize(true);


//        getResults();


       getSamplesChart();


    }

//    private void getResults() {
//        SkinAPI skinAPI = ConnectionManager.getApiServices();
//        Call<SampleResult> call = skinAPI.getSamples();
//        call.enqueue(new Callback<SampleResult>() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onResponse(Call<SampleResult> call, Response<SampleResult> response) {
//                if (response.isSuccessful()){
//                    if (response.body() != null){
//                       SampleResult sampleResult = response.body();
//                        List<Sample> samples = response.body().getSamples();
//                        for (Sample sample : samples){
//                            String content = "";
//                            content += "Type: " + sample.getType() + " Score: " + sample.getScore()+ "\n";
//                            scores.append(content);
//
//                        }
//                        TheResult.setText(sampleResult.getClassifiedAs());
////                        recycler.setAdapter(new SamplesAdapter(samples));
////                        TheResult.setText("Classified as: "+ sampleResult.getClassifiedAs());
//                        //Toast.makeText(ResultActivity.this, "The message is: "+ TheString, Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//                else {
//                    TheResult.setText("code: " + response.code());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<SampleResult> call, Throwable t) {
//                TheResult.setText(t.getMessage());
//                Toast.makeText(ResultActivity.this, "look at:"+ t.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.v("Response gotten is", t.getMessage());
//            }
//        });
//    }


    private void getSamplesChart(){
        SkinAPI skinAPI = ConnectionManager.getApiServices();
        Call<SampleResult> call = skinAPI.getSamples();
        call.enqueue(new Callback<SampleResult>() {
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
                        MPieChart.animateXY(5000, 5000);
                        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Samples");
                        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                        PieData pieData = new PieData(pieDataSet);
                        MPieChart.setData(pieData);
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
                Toast.makeText(ResultActivity.this, "ERROR", Toast.LENGTH_SHORT).show();

            }

        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
