package com.example.planningpoker;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddSessionFragment extends Fragment implements ValueEventListener {
    private static int sessionCounter = 0;
    private LinearLayout questionContainer;
    private Button addQuestion, addSession, backButton;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("sessions");
    private EditText nameInput;
    private ArrayList<String> questions = new ArrayList<>();

    public AddSessionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_session, container, false);

        questionContainer = view.findViewById(R.id.questionContainer);
        nameInput = view.findViewById(R.id.sessionName);

        addQuestion = view.findViewById(R.id.addQuestion);
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        addSession = view.findViewById(R.id.addSession);
        addSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sessionName = nameInput.getText().toString();

                if (TextUtils.isEmpty(sessionName) || questions.isEmpty()){
                    Snackbar error = Snackbar.make(view, getString(R.string.addSessionError), Snackbar.LENGTH_SHORT);
                    error.getView().setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.RED));
                    error.show();
                } else {
                    Session session = new Session(String.valueOf(sessionCounter),sessionName, questions);
                    addToDatabase(session);
                    getFragmentManager().popBackStack();
                }
            }
        });

        backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        Query query = database.orderByKey().limitToLast(1);
        query.addListenerForSingleValueEvent(this);

        return view;
    }

    public void addToDatabase(Session session){
        database.child(session.getSessionId()).setValue(session);
    }

    public void addTextView(String question){
        TextView questionView = new TextView(getContext());
        questionView.setText(question);
        questionContainer.addView(questionView);
    }

    public void showDialog(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle(getString(R.string.addQuestion));

        final EditText questionInput = new EditText(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        questionInput.setLayoutParams(layoutParams);

        alertDialog.setView(questionInput);
        alertDialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String question = questionInput.getText().toString();
                if(TextUtils.isEmpty(question)) {
                    dialogInterface.cancel();
                } else {
                    questions.add(question);
                    addTextView(question);
                }
            }
        });

        alertDialog.show();
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        //gets id of the last node
        for (DataSnapshot ds: dataSnapshot.getChildren()) {
            sessionCounter = Integer.parseInt(ds.getKey());
            sessionCounter++;
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
