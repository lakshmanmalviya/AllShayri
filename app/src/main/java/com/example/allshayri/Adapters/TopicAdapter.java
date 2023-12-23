package com.example.allshayri.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allshayri.Modals.TopicModal;
import com.example.allshayri.R;

import java.util.ArrayList;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicHolder> {
   ArrayList<TopicModal> tlist;
   Context context;
    public TopicAdapter(ArrayList<TopicModal> tlist, Context context) {
        this.tlist = tlist;
        this.context = context;
    }
    @NonNull
    @Override
    public TopicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.topic_row,parent,false);
        return new TopicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicHolder holder, int position) {
        TopicModal topicModal = tlist.get(position);
        holder.topicName.setText(topicModal.getTopicName());
    }
    @Override
    public int getItemCount() {
        return tlist.size();
    }

    class TopicHolder extends RecyclerView.ViewHolder{
        LinearLayout topicLayout;
        TextView topicName;
        public TopicHolder(@NonNull View itemView) {
            super(itemView);
            topicName = itemView.findViewById(R.id.topicNameRow);
            topicLayout = itemView.findViewById(R.id.topicLayout);
        }
    }
}
