package com.yojoo.skincancerclassifier.adabter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yojoo.skincancerclassifier.Data.Sample;
import com.yojoo.skincancerclassifier.R;

import java.util.List;

public class SamplesAdapter extends RecyclerView.Adapter<SamplesAdapter.ViewHolder>{

    private List<Sample> samples;

    public SamplesAdapter(List<Sample> samples) {
        this.samples = samples;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.samplescores_items, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.thesamples = samples.get(position);
        holder.types.setText(holder.thesamples.getType());
//        holder.scores.setText(holder.thesamples.getScore());


    }

    @Override
    public int getItemCount() {
        return samples.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        Sample thesamples;
        TextView types, scores;

        public ViewHolder(View itemView) {
            super(itemView);

           types = itemView.findViewById(R.id.sample_type);
           scores = itemView.findViewById(R.id.sample_score);
        }
    }

}
