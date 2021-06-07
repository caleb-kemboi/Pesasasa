package com.example.psasa;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;

// TODO :: back arrows not workin(NOT YET)

public class SignInUpActivity extends AppCompatActivity {

    private Button singin;
    private Button singup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sign_in_out);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Explode explode = new Explode();
            explode.setDuration(1000);
            getWindow().setEnterTransition(explode);
        }

        singin = (Button) findViewById(R.id.my_rounded_login_button);
        singup = (Button) findViewById(R.id.my_rounded_sing_up_btn);

        singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInUpActivity.this, ActivityPhoneSingIn.class));
            }
        });

        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInUpActivity.this, LoginActivity.class));
            }
        });
    }
}
