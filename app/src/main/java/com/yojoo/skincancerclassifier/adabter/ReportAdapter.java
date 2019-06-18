package com.yojoo.skincancerclassifier.adabter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yojoo.skincancerclassifier.Data.Report;
import com.yojoo.skincancerclassifier.R;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder>{
    List<Report> mReportList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public ReportAdapter(List<Report> reportList){
        mReportList = reportList;
    }

    static class ReportViewHolder extends RecyclerView.ViewHolder{
        TextView IdText;
        TextView ClassText;
        TextView DateText;

        ReportViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            IdText = itemView.findViewById(R.id.id_text);
            ClassText = itemView.findViewById(R.id.class_text);
            DateText = itemView.findViewById(R.id.Date2_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_list_item, parent, false);
        ReportViewHolder rvh = new ReportViewHolder(v,mListener);

        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        Report currentItem = mReportList.get(position);
        String idSt = Integer.toString(currentItem.getId());
        holder.IdText.setText(idSt);
        holder.ClassText.setText(currentItem.getClassification());
        holder.DateText.setText(currentItem.getDate());

    }



    @Override
    public int getItemCount() {
        return mReportList.size();
    }
}
