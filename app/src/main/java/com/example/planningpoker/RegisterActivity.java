package com.example.planningpoker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_name, et_email, et_password;
    private Button btn_register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //databaseUsers = FirebaseDatabase.getInstance().getReference("users"); // a json file users -nodeja

        et_name = (EditText) findViewById(R.id.editText_name);
        et_email = (EditText) findViewById(R.id.editText_email);
        et_password = (EditText) findViewById(R.id.editText_passw);
        mAuth = FirebaseAuth.getInstance();



        findViewById(R.id.button).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null)
        {
            //handle the already login user
        }
    }

    private void registerUser()
    {
        final String name = et_name.getText().toString().trim();
        final String email = et_email.getText().toString().trim();
        final String password = et_password.getText().toString().trim();

        if(name.isEmpty())
        {
            et_name.setError("Name required");
            et_name.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            et_email.setError("Email required");
            et_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            et_email.setError("enter a valid email");
            et_email.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            et_password.setError("Password required");
            et_password.requestFocus();
            return;
        }

        if(password.length() < 6)
        {
            et_password.setError("password should be at least 6 characters");
            et_password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    // we will store the additional fields in firebase db
                    User user = new User(name,email,password,0);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this,"Siker", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this,"Task failed",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.button:
                registerUser();
                break;
        }
    }


}
