package com.example.gymmanagment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Button logout = (Button) findViewById(R.id.Logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        String userID = user.getUid();



        final TextView fullnamebtnTextView =(TextView) findViewById(R.id.fullnamebtn);
        final TextView EmailbtntnTextView =(TextView) findViewById(R.id.Emailbtn);
        final TextView AgeTextView =(TextView) findViewById(R.id.Agebtn);


reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        user userProfile =snapshot.getValue(user.class);
        if (userProfile !=null){
            String fullname = userProfile.fullName;
            String Emailbtn = userProfile.email;
            String age = userProfile.age;


            fullnamebtnTextView.setText(fullname);
            EmailbtntnTextView.setText(Emailbtn);
            AgeTextView.setText(age);





        }



    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(ProfileActivity.this,"Something Wrong Happened",Toast.LENGTH_LONG).show();

    }
});

    }
}