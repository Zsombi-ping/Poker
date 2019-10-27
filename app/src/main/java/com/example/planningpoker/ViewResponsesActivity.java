package com.example.planningpoker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewResponsesActivity extends AppCompatActivity {
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("responses");
    private RecyclerView responseList;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<QuestionResponses> sessionResponses = new ArrayList<>();
    private int response;
    private String sessionId;
    private TextView noResponses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_responses);

        noResponses = findViewById(R.id.noResponses);
        sessionId = getIntent().getStringExtra("sessionId");
        readData(questionResponses -> initializeResponseList());
    }

    public void initializeResponseList(){
        if (sessionResponses.size() != 0){
            noResponses.setVisibility(View.INVISIBLE);
        }

        responseList = findViewById(R.id.responseContainer);
        layoutManager = new LinearLayoutManager(this);
        responseList.setLayoutManager(layoutManager);
        responseList.addItemDecoration(new DividerItemDecoration(responseList.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new ResponseListAdapter(sessionResponses, getApplicationContext());
        responseList.setAdapter(adapter);
    }

    public void readData (FirebaseCallback callback){
        database.child(sessionId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sessionResponses.clear();
                for (DataSnapshot questions : dataSnapshot.getChildren()){
                    String questionId = questions.getKey();
                    ArrayMap<String, Integer> responses = new ArrayMap<>();
                    for (DataSnapshot users : questions.getChildren()) {
                        String userId = users.getKey();
                        int response = users.getValue(Integer.class);
                        responses.put(userId,response);
                    }
                    sessionResponses.add(new QuestionResponses(questionId,responses));
                    callback.onCallback(sessionResponses);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //waits until all the data from firebase is loaded
    private interface FirebaseCallback{
        void onCallback(ArrayList<QuestionResponses> questionResponses);
    }
}
