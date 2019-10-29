package com.example.planningpoker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionsFragment extends Fragment implements View.OnClickListener {
    private View view;
    private static int questionId = 0;
    private int chosenCard = 0, userResponse;
    private Session session;
    private String sessionId;
    private TextView questionText;
    private Button sendButton;
    private ArrayList<String> questions = new ArrayList<>();
    private DatabaseReference database;
    private String userName;

    public QuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_questions, container, false);

        sessionId =  getArguments().getString("sessionId");
        userName =  getArguments().getString("userName");
        readData(questions  -> setQuestion(questionId));

        initializeCardImages();

        sendButton = view.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //shows error when no card is chosen
                if (chosenCard == 0){
                    Snackbar error = Snackbar.make(view, getString(R.string.chooseCardError), Snackbar.LENGTH_SHORT);
                    error.getView().setBackgroundColor(getResources().getColor(R.color.RED));
                    TextView snackbarText = error.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                    snackbarText.setBackgroundColor(getResources().getColor(R.color.RED));
                    error.show();
                } else {
                    //add response to database
                    database =  FirebaseDatabase.getInstance().getReference("responses");
                    database.child(sessionId).child(questions.get(questionId)).child(userName).setValue(userResponse);
                    questionId++;

                    //checks if there is question left
                    if (questionId < questions.size()){
                        chosenCard = 0;
                        refreshFragment();
                    } else {
                        //the last question leads to view responses fragment
                        Intent responsesIntent = new Intent(getActivity(), ViewResponsesActivity.class);
                        responsesIntent.putExtra("sessionId",sessionId);
                        startActivity(responsesIntent);
                    }
                }
            }
        });

        return view;
    }

    //reloads fragment with next question
    private void refreshFragment(){
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.detach(currentFragment);
        fragmentTransaction.attach(currentFragment);
        fragmentTransaction.commit();
    }

    private void initializeCardImages() {
        ImageView cardA, card2, card3, card4, card5;
        cardA = view.findViewById(R.id.cardA);
        card2 = view.findViewById(R.id.card2);
        card3 = view.findViewById(R.id.card3);
        card4 = view.findViewById(R.id.card4);
        card5 = view.findViewById(R.id.card5);

        cardA.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
    }

    public void highlightCard(int cardId){
        ImageView card = view.findViewById(cardId);
        card.setBackgroundResource(R.drawable.photo_highlight);
    }

    private void removeHighlight(int cardId) {
        ImageView card = view.findViewById(cardId);
        card.setBackgroundResource(0);
    }

    public void setQuestion(int i) {
        questionText = view.findViewById(R.id.questionText);

        String title = " (" + (questionId+1) + "/" + questions.size() +")";
        questionText.setText(questions.get(i).concat(title));

        if (questionId == questions.size()-1){
            sendButton.setText(getString(R.string.viewResponses));
        }
    }

    public void readData (FirebaseCallback callback){
        database =  FirebaseDatabase.getInstance().getReference("sessions");
        database.child(sessionId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                session = dataSnapshot.getValue(Session.class);
                questions = session.getQuestions();
                callback.onCallback(questions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (chosenCard != 0){
            removeHighlight(chosenCard);
        }
        switch (view.getId()){
            case R.id.cardA:
                chosenCard = R.id.cardA;
                highlightCard(chosenCard);
                userResponse = 1;
                break;

            case R.id.card2:
                chosenCard = R.id.card2;
                highlightCard(chosenCard);
                userResponse = 2;
                break;

            case R.id.card3:
                chosenCard = R.id.card3;
                highlightCard(chosenCard);
                userResponse = 3;
                break;

            case R.id.card4:
                chosenCard = R.id.card4;
                highlightCard(chosenCard);
                userResponse = 4;
                break;

            case R.id.card5:
                chosenCard = R.id.card5;
                highlightCard(chosenCard);
                userResponse = 5;
                break;
        }
    }

    //waits until all the data from firebase is loaded
    private interface FirebaseCallback{
        void onCallback(ArrayList<String> questions );
    }

}