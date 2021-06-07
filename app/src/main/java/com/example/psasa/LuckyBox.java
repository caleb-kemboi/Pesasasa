package com.example.psasa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.psasa.Model.BetHistory;
import com.example.psasa.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

// TODO :: create timer for each round and functionality/ability of the app to jump to next round and handle each separately
    // TODO :: create uid for every round and respective round stakes and winning box/card(NOT YET)
    // TODO :: create notification for every stakers of each round with their status and the box/card that won(NOT YET)
public class LuckyBox extends AppCompatActivity {

    private long wallet;
    private long stake;
    private int boxSelected;
    private String thisGame="Lucky Card";
    private String winnings="0";
    private String gameStatus="Loose";

    private LinearLayout boxA;
    private LinearLayout boxB;
    private LinearLayout boxC;
    private LinearLayout boxD;

    final private String [] cards={"BOX A","BOX B","BOX C","BOX D"};

    // entry point
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    public static final String MyPREFERENCES = "com.example.psasa.PREFERENCE_FILE_KEY";
    private SharedPreferences prefs;

//    final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.myCoordinatorLayout);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lucky_box);

        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = mFirebaseDatabase.getInstance().getReference("users").child(prefs.getString("phone_number","phoneNumber"));
        


        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                wallet = user.getWallet();
//                Snackbar.make(coordinatorLayout, "WALLET ==>",
//                        Snackbar.LENGTH_SHORT)
//                        .show();
//                        Toast.makeText(LuckyBox.this, "WALLET ==>"+user.getWallet(), Toast.LENGTH_LONG).show();
//                        Log.e(this.toString(),"WALLET +===>"+user.getWallet());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        boxA=(LinearLayout)findViewById(R.id.box_a);
        boxB=(LinearLayout)findViewById(R.id.box_b);
        boxC=(LinearLayout)findViewById(R.id.box_c);
        boxD=(LinearLayout)findViewById(R.id.box_d);



        boxA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLoadData("BOX A");




            }

        });

        boxB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLoadData("BOX B");
            }
        });

        boxC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLoadData("BOX C");
            }
        });

        boxD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLoadData("BOX D");
            }
        });
    }


    private void dialogLoadData(final String msg){


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

        final AlertDialog builder=new AlertDialog.Builder(this)
                .setView(lyt)
                .setTitle(msg)
                .setPositiveButton("PLACE BET", null)
                .setNegativeButton("Cancel",null)
                .create();



//        builder.setPositiveButton("PLACE BET", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (input.getText().toString().isEmpty()){
//                    input.setError("Input Stake");
//                    return;
//                }
//                String name = input.getText().toString().trim();
//                stake = Long.valueOf(name);
//
////                if(stake < wallet){
////
////                }
//
//                Toast.makeText(LuckyBox.this, "BOX SELECTED"+msg, Toast.LENGTH_SHORT).show();
//
////                mDatabaseReference.child("wallet").setValue(wallet+stake);
//                dialog.dismiss();
//            }
//        });
//
//        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        builder.show();

        builder.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) builder).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        if (input.getText().toString().isEmpty()) {
                            input.setError("Input Stake");
                            return;
                        }
                        String name = input.getText().toString().trim();
                        stake = Long.valueOf(name);

                        if(stake>wallet){
                            input.setError("INSUFFIENT MONEY IN YOUR ACCOUNT");
                            Toast.makeText(LuckyBox.this, "YOU HAVE INSUFFICIENT FUNDS IN YOUR WALLET", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        int i = selectRandom();

                        if(cards[i].equals(msg)){
                            Toast.makeText(LuckyBox.this, "YOU WON", Toast.LENGTH_SHORT).show();
                            winnings = String.valueOf(stake+wallet);
                            gameStatus="Won";
                            mDatabaseReference.child("wallet").setValue(wallet+stake);
                        }else {
                            Toast.makeText(LuckyBox.this, "BOX SELECTED " + msg + "\n int==>" + i+"\n"+cards[i], Toast.LENGTH_LONG).show();
                        }


                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();

                        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("bets_history");
                        BetHistory betHistory= new BetHistory();
                        betHistory.setGame(thisGame);
                        betHistory.setAmount(stake);
                        betHistory.setBoxPlaced(msg);
                        betHistory.setBoxWon(cards[i]);
                        betHistory.setTime(formatter.format(date));
                        betHistory.setWinnings(winnings);
                        betHistory.setStatus(gameStatus);

                        //build child
                        ref.child(firebaseUser.getUid()).child(String.valueOf(System.currentTimeMillis())).setValue(betHistory);


                        ////                mDatabaseReference.child("wallet").setValue(wallet+stake);

                        //Dismiss once everything is OK.
                        builder.dismiss();
                    }
                });

                Button buttonCancel = ((AlertDialog) builder).getButton(AlertDialog.BUTTON_NEGATIVE);
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
            }
        });
        builder.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int selectRandom(){
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = ThreadLocalRandom.current().nextInt(0, 3+1);
        return  randomNum;
    }
}
