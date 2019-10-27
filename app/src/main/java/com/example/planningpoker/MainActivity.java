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
    private CheckBox cb;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Boolean saveLogin;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference rootRef, itemsRef;
    private ArrayList items_emails;
    private ArrayList items_passwords;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_register = findViewById(R.id.button_register);
        btn_login = findViewById(R.id.button_login);
        et_loginEmail = findViewById(R.id.editText_loginEmail);
        et_password = findViewById(R.id.editText_password);
        cb = findViewById(R.id.checkBox);


        firebaseDatabase = FirebaseDatabase.getInstance();
        rootRef = firebaseDatabase.getReference();
        itemsRef = rootRef.child("users");
        items_emails = new ArrayList<String>();
        items_passwords = new ArrayList<String>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    String emails = ds.child("userEmail").getValue(String.class);
                    items_emails.add(emails);
                    String passwords = ds.child("userPassword").getValue(String.class);
                    items_passwords.add(passwords);
                }
                Log.d("LIST",items_emails.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        itemsRef.addListenerForSingleValueEvent(valueEventListener);


        btn_login.setOnClickListener(this);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        sharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        saveLogin = sharedPreferences.getBoolean("saveLogin", false);

        if (saveLogin == true) {
            et_loginEmail.setText(sharedPreferences.getString("useremail", ""));
            et_password.setText(sharedPreferences.getString("password", ""));
            cb.setChecked(true);

        }

    }

    public void onClick(View v) {
        if (v == btn_login) {


            email = et_loginEmail.getText().toString();
            password = et_password.getText().toString();

            if(items_emails.contains(email) && items_passwords.contains(password))
            {
                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(et_loginEmail.getWindowToken(), 0);

                if (cb.isChecked()) {
                    editor.putBoolean("saveLogin", true);
                    editor.putString("useremail", email);
                    editor.putString("password", password);

                    editor.commit();
                } else {
                    editor.clear();
                    editor.commit();
                }


                doSomethingElse();

            }
            else{
                Toast.makeText(getApplicationContext(),"Invalid email or password",Toast.LENGTH_LONG).show();
            }
        }

    }

    public void doSomethingElse() {
        startActivity(new Intent(MainActivity.this, SessionActivity.class));
        finish();
    }
}

