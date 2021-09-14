package com.example.gymmanagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register;

    private EditText editTextEmail, editTextPassword;
    private Button Login;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        Login = (Button) findViewById(R.id.Login);
        Login.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextEmail.setOnClickListener(this);

        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPassword.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth=FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;


            case R.id.Login:
                userLogin();
                break;

        }
    }

    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("please Enter s valid Email");
            return;
        }

        if (password.isEmpty()) {
            editTextEmail.setError("password is required");
            editTextEmail.requestFocus();
            return;

        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()){

                  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                     if (user.isEmailVerified()){

                         //redirect to user profile
                         startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                         progressBar.setVisibility(View.GONE);
                     }else {
                         user.sendEmailVerification();
                         Toast.makeText(MainActivity.this,"Chenck your Email to verify Account", Toast.LENGTH_LONG).show();
                         progressBar.setVisibility(View.GONE);
                     }


              }else{
                  Toast.makeText(MainActivity.this,"Faild to Login please check your credentials",Toast.LENGTH_LONG).show();
                  progressBar.setVisibility(View.GONE);
              }
            }
        });


    }
}
