package com.example.gymmanagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private TextView strong,Caccount;
    private EditText fullName,Email,password,Age,phone;
    private Button Registerbtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();

        strong = (TextView)findViewById(R.id.strong);
        strong.setOnClickListener(this);
        Caccount=(TextView)findViewById(R.id.Caccount);

        Registerbtn=(Button)findViewById(R.id.Login);
        Registerbtn.setOnClickListener(this);

        fullName=(EditText)findViewById(R.id.fullName);
        Email=(EditText)findViewById(R.id.editTextEmail);
        password=(EditText)findViewById(R.id.editTextPassword);
        Age=(EditText)findViewById(R.id.Age);
        phone=(EditText)findViewById(R.id.phone);

        progressBar=(ProgressBar)findViewById(R.id.progressBar);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.strong:
               startActivity(new Intent(this,MainActivity.class));
               break;
            case R.id.Login:
                Registerbtn();
        }

    }

    private void Registerbtn() {
        String FullName = fullName.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String age = Age.getText().toString().trim();
        String Phone = phone.getText().toString().trim();

         if (FullName.isEmpty()){
             fullName.setError("full name is required");
             fullName.requestFocus();
             return;
         }


        if (email.isEmpty()) {
            Email.setError("email is erroe");
            Email.requestFocus();
            return;



        }






          if (Password.isEmpty()){
        password.setError("Password is error");
        return;
    }

        if(password.length()<6) {
            password.setError("min password length should be 6 characters!");
            password.requestFocus();
            return;
        }


         if (age.isEmpty()){
             Age.setError("Age is error");
             Age.requestFocus();
             return;

         }

         if (Phone.isEmpty()){
             phone.setError("invalid phone number");
             phone.requestFocus();
             return;
         }

         progressBar.setVisibility(View.VISIBLE);
         mAuth.createUserWithEmailAndPassword(email,Password)
                 .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {


                         if (task.isSuccessful()){
                             user user = new user(fullName, email,age,phone);
                             FirebaseDatabase.getInstance().getReference("users")
                                     .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                     .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {

                                     progressBar.setVisibility(View.VISIBLE);
                                     if(task.isSuccessful()){
                                         Toast.makeText(RegisterUser.this,"user has been registerd successfully",Toast.LENGTH_LONG).show();
                                         progressBar.setVisibility(View.GONE);



                                         //redirect to login layout:

                                     }else{

                                         Toast.makeText(RegisterUser.this,"Faild to register!",Toast.LENGTH_LONG).show();
                                         progressBar.setVisibility(View.GONE);

                                     }




                                 }
                             });


                         }else {

                             Toast.makeText(RegisterUser.this,"Faild to register!",Toast.LENGTH_LONG).show();
                             progressBar.setVisibility(View.GONE);


                         }
                     }
                 });

        }
    }
