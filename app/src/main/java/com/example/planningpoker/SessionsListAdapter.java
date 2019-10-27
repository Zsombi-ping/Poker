package com.example.planningpoker;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SessionsListAdapter extends RecyclerView.Adapter<SessionsListAdapter.ListViewHolder> {
    private ArrayList<Session> sessions;
    private OnSessionListener onSessionListener;

    public SessionsListAdapter(ArrayList<Session> sessions, OnSessionListener onSessionListener) {
        this.sessions = sessions;
        this.onSessionListener = onSessionListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.session_list_item, parent,false);
        ListViewHolder viewHolder = new ListViewHolder(listItem, onSessionListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SessionsListAdapter.ListViewHolder holder, int position) {
        holder.sessionName.setText(sessions.get(position).getSessionName());
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView sessionName;
        OnSessionListener onSessionListener;

        public ListViewHolder(@NonNull View itemView, OnSessionListener onSessionListener) {
            super(itemView);
            this.sessionName = itemView.findViewById(R.id.name);
            this.onSessionListener = onSessionListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onSessionListener.onSessionClick(getAdapterPosition());
        }
    }

    public interface OnSessionListener{
        void onSessionClick(int position);
    }
}
