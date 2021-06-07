package com.example.psasa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.psasa.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityPhoneSingIn extends AppCompatActivity {
    private EditText mobile;
    private EditText password;
    private Button button;
    String no;
    String my_password;

    public static final String MyPREFERENCES = "com.example.psasa.PREFERENCE_FILE_KEY";
    private SharedPreferences prefs;

    // entry point
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_sign_in_activity);

        mobile = (EditText) findViewById(R.id.editText_phonenumber);

        password = (EditText) findViewById(R.id.editText_password);

        button = (Button) findViewById(R.id.button_login);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mDatabaseReference = mFirebaseDatabase.getReference().child("users");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                no = mobile.getText().toString();
                my_password = password.getText().toString();
                validNo(no);
                checkIfItExist(no,my_password);



                Intent intent = new Intent(ActivityPhoneSingIn.this,VerifyMobile.class);

                prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("phone_number", no);
                editor.putString("password",my_password);
                editor.apply();

                startActivity(intent);
                Toast.makeText(ActivityPhoneSingIn.this,no,Toast.LENGTH_LONG).show();
            }
        });

    }

    private void validNo(String no){
        if(no.isEmpty() || no.length() < 10){
            mobile.setError("Enter a valid mobile");
            mobile.requestFocus();
            return;
        }
    }

    private void checkIfItExist(final String number, String password){
//        mDatabaseReference.child("users").orderByChild("pa")

    }


    public void backToMain(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}
