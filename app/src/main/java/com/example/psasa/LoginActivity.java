package com.example.psasa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    // TODO :: confirm login number

    private EditText mobile;
    private EditText password;
    private Button loginButton;
    private String no;

    // entry point
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    public static final String MyPREFERENCES = "com.example.psasa.PREFERENCE_FILE_KEY";
    private SharedPreferences prefs;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mobile = (EditText) findViewById(R.id.editText_phonenumber_login);
        password = (EditText) findViewById(R.id.editText_password);
        loginButton = (Button)findViewById(R.id.button_login);


        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        // connect to the firebase realtime db "users"
        mDatabaseReference = mFirebaseDatabase.getReference().child("users");



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no = mobile.getText().toString();
                validData(no,password.getText().toString());
                checkForPhoneNumber(no);
            }
        });


    }

    private void validData(String no,String pass_word){
        if(no.isEmpty() || no.length() < 10){
            mobile.setError("Enter a valid mobile");
            mobile.requestFocus();
            return;
        }
        if(pass_word.isEmpty()){
            password.setError("Enter a password");
            password.requestFocus();
            return;
        }
        if(pass_word.length() <6){
            password.setError("Password should atleast be 6 characters");
            password.requestFocus();
            return;
        }
    }

    public void backToMain(View view) {
        finish();
    }

    private void checkForPhoneNumber(String number){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users");

        ref.child(number).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    Toast.makeText(LoginActivity.this, "NUMBER EXISTS", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(LoginActivity.this, "DOESN'T", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
