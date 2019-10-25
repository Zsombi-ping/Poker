package com.example.planningpoker;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SessionListFragment extends Fragment implements SessionsListAdapter.OnSessionListener, View.OnClickListener {
    private View view;
    private RecyclerView sessionList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Session> sessions = new ArrayList<Session>();;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("sessions");
    private Button addSession;

    public SessionListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_session_list, container, false);

        initSessionList();
        setListData();

        addSession = view.findViewById(R.id.addSession);
        addSession.setOnClickListener(this);

        return view;
    }

    public void initSessionList(){
        sessionList = view.findViewById(R.id.sessionList);
        layoutManager = new LinearLayoutManager(getContext());
        sessionList.setLayoutManager(layoutManager);
        sessionList.addItemDecoration(new DividerItemDecoration(sessionList.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new SessionsListAdapter(sessions, this);
        sessionList.setAdapter(adapter);
    }

    public void setListData(){
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sessions.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Session session = ds.getValue(Session.class);
                    sessions.add(session);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onSessionClick(int position) {
        showDialog(sessions.get(position));
    }

    public void showDialog(Session session) {
        final CharSequence[] items = {getString(R.string.deleteSession), getString(R.string.viewResponses)};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(session.getSessionName())
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                database.child(session.getSessionId()).removeValue();
                                adapter.notifyDataSetChanged();
                                break;
                            case 1:
                                //TODO view responses
                                break;
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        ((SessionsActivity)getActivity()).replaceFragment(new AddSessionFragment());
    }
}