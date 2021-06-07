package com.example.psasa;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

    // TODO :: create timer for each round and functionality/ability of the app to jump to next round and handle each separately
    // TODO :: create uid for every round and respective round stakes and winning box/card(NOT YET)
    // TODO :: create notification for every stakers of each round with their status and the box/card that won(NOT YET)
public class LuckyCard extends AppCompatActivity {

    private LinearLayout cardA;
    private LinearLayout cardB;
    private LinearLayout cardC;
    private LinearLayout cardD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lucky_card);

            cardA = (LinearLayout) findViewById(R.id.card_a);
            cardB = (LinearLayout) findViewById(R.id.card_b);
            cardC = (LinearLayout) findViewById(R.id.card_c);
            cardD = (LinearLayout) findViewById(R.id.card_d);

            cardA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogLoadData("CARD A CHOOSEN");
                }
            });

            cardB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogLoadData("CARD B CHOOSEN");
                }
            });

            cardC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogLoadData("CARD C CHOOSEN");
                }
            });

            cardD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogLoadData("CARD D CHOOSEN");
                }
            });
        }
    private void dialogLoadData(String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(msg);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setMaxLines(0);
        input.setSingleLine(true);
        input.setEms(100);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        input.setHint("Place you stake");
        input.setText("");



        LinearLayout lyt = new LinearLayout(this);
        int padding = Tools.dpToPx(this, 20);
        lyt.setPadding(padding, padding, padding, padding);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lyt.setLayoutParams(params);
        lyt.addView(input);
        builder.setView(lyt);

        builder.setPositiveButton("PLACE BET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = input.getText().toString().trim();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }
}
