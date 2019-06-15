package com.yojoo.skincancerclassifier.adabter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yojoo.skincancerclassifier.Data.Messages;
import com.yojoo.skincancerclassifier.Data.Report;
import com.yojoo.skincancerclassifier.R;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{
    private List<Messages> MessageList;

    public MessageAdapter(List<Messages> messageList){
        MessageList = messageList;
    }


    public static  class MessageViewHolder extends RecyclerView.ViewHolder{
        public TextView message, TextDate;

        public MessageViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message_text);
            TextDate = itemView.findViewById(R.id.Date_text);

        }
    }



    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        MessageViewHolder evh = new MessageViewHolder(view);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Messages currentItem = MessageList.get(position);

        holder.message.setText(currentItem.getMessage());
        holder.TextDate.setText(currentItem.getDate());
    }

    @Override
    public int getItemCount() {
        return MessageList.size();
    }
}
