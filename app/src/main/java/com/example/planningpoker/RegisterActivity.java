package com.example.planningpoker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText et_name, et_email, et_password;
    Button btn_register;
    DatabaseReference databaseUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseUsers = FirebaseDatabase.getInstance().getReference("users"); // a json file users -nodeja

        et_name = (EditText) findViewById(R.id.editText_name);
        et_email = (EditText) findViewById(R.id.editText_email);
        et_password = (EditText) findViewById(R.id.editText_passw);
        btn_register = (Button) findViewById(R.id.button);


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addUser();
            }
        });
    }

    private void addUser()
    {
        String name = et_name.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
        {
            String id = databaseUsers.push().getKey(); // egyedi id-t rendel  minden userhez
            Integer type=0;


            User user = new User (id,name,email,password,type);

            databaseUsers.child(id).setValue(user); // a setValue-t hasznaljuk a user firebase adatbazisban valo tarolasara
            // a usert az id-jan belul taroljuk amit egyedien generalunk


            Toast.makeText(this,"User added", Toast.LENGTH_LONG).show();

        }
        else{
            Toast.makeText(this,"Missing information", Toast.LENGTH_LONG).show();
        }

    }

}
