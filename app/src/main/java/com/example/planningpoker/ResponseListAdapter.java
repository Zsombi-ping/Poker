package com.example.planningpoker;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Response;

import java.util.ArrayList;

public class ResponseListAdapter extends RecyclerView.Adapter<ResponseListAdapter.ListViewHolder> {
    private ArrayList<QuestionResponses> questionResponses;
    private Context context;

    public ResponseListAdapter(ArrayList<QuestionResponses> questionResponses, Context context) {
        this.questionResponses = questionResponses;
        this.context = context;
    }

    @NonNull
    @Override
    public ResponseListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.response_list_item, parent,false);
        ResponseListAdapter.ListViewHolder viewHolder = new ResponseListAdapter.ListViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResponseListAdapter.ListViewHolder holder, int position) {
        holder.question.setText(questionResponses.get(position).getQuestionId());
        ArrayMap<String, Integer> responses = questionResponses.get(position).getResponses();
        for (ArrayMap.Entry<String,Integer> entry : responses.entrySet()){

            LinearLayout responseContainer = new LinearLayout(context);
            responseContainer.setOrientation(LinearLayout.VERTICAL);
            responseContainer.setPadding(30,0,30,0);
            responseContainer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            TextView user = new TextView(context);
            user.setText(entry.getKey());
            user.setTextColor(Color.WHITE);
            responseContainer.addView(user);

            TextView response = new TextView(context);
            response.setText(String.valueOf(entry.getValue()));
            response.setTextColor(Color.WHITE);
            responseContainer.addView(response);

            holder.responsesContainer.addView(responseContainer);
        }
    }

    @Override
    public int getItemCount() {
        return questionResponses.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private TextView question;
        private LinearLayout responsesContainer;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            this.question = itemView.findViewById(R.id.question);
            this.responsesContainer = itemView.findViewById(R.id.responsesContainer);
        }
    }
}
