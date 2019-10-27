package com.example.planningpoker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_register, btn_login;
    private EditText et_loginEmail, et_password;
    private String email, password;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Boolean saveLogin;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference rootRef, itemsRef;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_register = findViewById(R.id.button_register);
        btn_login = findViewById(R.id.button_login);
        et_loginEmail = findViewById(R.id.editText_loginEmail);
        et_password = findViewById(R.id.editText_password);
        auth = FirebaseAuth.getInstance();


        firebaseDatabase = FirebaseDatabase.getInstance();
        rootRef = firebaseDatabase.getReference();
        itemsRef = rootRef.child("Users");


        btn_login.setOnClickListener(this);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onClick(View v) {
        if (v == btn_login) {

            auth.fetchSignInMethodsForEmail(et_loginEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                    if(task.getResult().getSignInMethods().isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),"Invalid email adress",Toast.LENGTH_LONG).show();

                    }
                    else{

                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                        doSomethingElse();

                    }
                }
            });

        }
    }

    public void doSomethingElse() {
        startActivity(new Intent(MainActivity.this, SessionActivity.class));
        finish();
    }

    // FirebaseDatabase database = FirebaseDatabase.getInstance();
    // DatabaseReference ref = database.getReference("registration");


}

