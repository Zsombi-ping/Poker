package com.example.planningpoker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, SessionsActivity.class);
        startActivity(intent);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("responses");
        database.child("0").child("question1").child("dsfsff").setValue(2);
        database.child("0").child("question0").child("dsfsdf").setValue(3);
        database.child("0").child("question1").child("dsfsdf").setValue(3);
    }

}
