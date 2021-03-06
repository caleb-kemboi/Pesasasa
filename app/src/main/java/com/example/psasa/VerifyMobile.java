package com.example.psasa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.psasa.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerifyMobile extends AppCompatActivity {

    public static final String USER_ACCOUNT = "user_account";

    EditText otp;
    Button login;
    String no;
    String password;
    User userAccount;

    public static final String MyPREFERENCES = "com.example.psasa.PREFERENCE_FILE_KEY";
    private SharedPreferences prefs;

    private FirebaseAuth mAuth;
    private String mVerificationId;

    // entry point
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile);

        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mFirebaseDatabase = FirebaseDatabase.getInstance();


        otp = (EditText) findViewById(R.id.otp);

        mAuth = FirebaseAuth.getInstance();



        try {

            no = prefs.getString("phone_number", null);

            userAccount = new User();
            userAccount.setPhoneNumber(no);
            userAccount.setPassword(prefs.getString("password", null));


        } catch (Exception e) {
            Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show();

        }


        sendVerificationCode(no);


        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = otp.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    otp.setError("Enter valid code");
                    otp.requestFocus();
                    return;
                }

                //verifying the code entered manually
                verifyVerificationCode(code);

            }
        });
    }

    private void sendVerificationCode(String no) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+254" + no,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                otp.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyMobile.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyMobile.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            mMessageDatabaseReference = mFirebaseDatabase.getReference().child("users");

//                            mMessageDatabaseReference.push().setValue(userAccount);

                            //get firebase user
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            userAccount.setUid(user.getUid());
                            userAccount.setWallet(1000);

//                            //get reference
//                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

                            //build child
                            mMessageDatabaseReference.child(userAccount.getPhoneNumber()).setValue(userAccount);

                            //verification successful we will start the main activity
                            Intent intent = new Intent(VerifyMobile.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }


                        }
                    }
                });

    }
    public void backToMain(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}