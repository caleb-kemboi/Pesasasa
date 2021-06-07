package com.example.psasa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class ActivitySplash extends AppCompatActivity {
    public final String TAG = ActivitySplash.class.getSimpleName();





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        Toast.makeText(ActivitySplash.this," Oya",Toast.LENGTH_SHORT).show();



//        CheckBox cb = (CheckBox) findViewById(R.id.check_box_18_years);
//
//        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked == true) {
////                    Toast.makeText(getApplicationContext(), "Checkbox clicked", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(i);
//                }
//            }
//        });
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // go to the main activity
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                // kill current activity
                finish();


            }
        };
        // Show splash screen for 3 seconds
        new Timer().schedule(task, 2000);

        // for system bar in lollipop
//        Tools.systemBarLollipop(this);
    }
}
