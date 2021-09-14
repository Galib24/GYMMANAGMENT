package com.example.gymmanagment;

import android.widget.EditText;

import androidx.annotation.NonNull;

public class user {
    public  String fullName,age,email;

    public user(EditText fullName, String email, String age, EditText phone){

    }

    public user(String fullName,String age,String email){
        this.fullName=fullName;
        this.email=email;
        this.age=age;

    }



}
