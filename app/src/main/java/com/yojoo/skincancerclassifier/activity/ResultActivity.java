package com.yojoo.skincancerclassifier.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.yojoo.skincancerclassifier.Connection.ConnectionManager;
import com.yojoo.skincancerclassifier.Connection.MyResponse;
import com.yojoo.skincancerclassifier.R;
import com.yojoo.skincancerclassifier.model.SamplesRes;
import com.yojoo.skincancerclassifier.model.SamplesScores;

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

//        getSamplesChart();
    }

    private void getSamplesChart(){
        Call<List<SamplesRes>> call = ConnectionManager.getApiServices().getSamples();
        call.enqueue(new Callback<List<SamplesRes>>() {
            @Override
            public void onResponse(Call<List<SamplesRes>> call, Response<List<SamplesRes>> response) {
                if (response.isSuccessful()){
                    Toast.makeText(ResultActivity.this, "this is Sparta", Toast.LENGTH_SHORT).show();
                    if (response.body() != null) {
                        List<PieEntry> pieEntries = new ArrayList<>();

                        for (SamplesRes samplesRes : response.body()) {
                            pieEntries.add(new PieEntry());
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
            public void onFailure(Call<List<SamplesRes>> call, Throwable t) {
                Toast.makeText(ResultActivity.this, "ERROR", Toast.LENGTH_SHORT).show();

            }

        });

    }
}
