package com.example.psasa;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.psasa.Adapters.BetTransactionAdapter;
import com.example.psasa.Model.BetHistory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class BetHistoryActivity extends AppCompatActivity {

    private ListView mBetHistoryListView;
    private BetTransactionAdapter mBetHistoryAdapter;
    private ImageView betHistoryBacKToMain;

    // entry point
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
//    private FirebaseStorage mFirebaseStorage;
//    private StorageReference mChatPhotosStorageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bet_history_activity);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mDatabaseReference = mFirebaseDatabase.getInstance().getReference().child("bets_history");

        betHistoryBacKToMain = (ImageView) findViewById(R.id.bet_history_to_previous);
        betHistoryBacKToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBetHistoryListView = (ListView) findViewById(R.id.betHistoryListView);


        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseReference.child(firebaseUser.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                BetHistory betHistory = dataSnapshot.getValue(BetHistory.class);
                mBetHistoryAdapter.add(new BetHistory("Lucky Box","16/1/2019 11:25:20 AM","STATUS WON","BOX A PLACED","BOX A WON",50,"WINNINGS: 135.40"));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        ArrayList<String> arrayList=new ArrayList<String>();
        arrayList.add("Lucky Box\n16/1/2019 11:25:20 AM\t STATUS WON\nBOX A PLACED\nBOX A WON\nAMMOUNT PLACED: 50\nWINNINGS: 135.40");
        arrayList.add("Lucky Card\n15/1/2019 10:07:15 AM\t STATUS LOSE\nDICE PLACED\nFLOWER WON\nAMMOUNT PLACED: 100\nWINNINGS: 0.0");
        arrayList.add("Lucky Box\n17/1/2019 13:25:28 \t STATUS ACTIVE\nBOX C PLACED\n-\"- WON\nAMMOUNT PLACED0: 300\nWINNINGS: --\"--");

        // Initialize message ListView and its adapter
        List<BetHistory> betHistories = new ArrayList<>();
//        betHistories.add(new BetHistory("Lucky Box","16/1/2019 11:25:20 AM","STATUS WON","BOX A PLACED","BOX A WON",50,"WINNINGS: 135.40"));
//        betHistories.add(new BetHistory("Lucky Card","15/1/2019 10:07:15 AM","STATUS LOSE","DICE PLACED","FLOWER WON",100,"WINNINGS: 0.0"));
//        betHistories.add(new BetHistory("Lucky Box","17/1/2019 13:25:28","STATUS ACTIVE","BOX C PLACED","WON",300,"WINNINGS: --"));
//        betHistories.add(new BetHistory("Lucky Box","16/1/2019 11:25:20 AM","STATUS WON","BOX A PLACED","BOX A WON", 50,"WINNINGS: 135.40"));
//        betHistories.add(new BetHistory("Lucky Card","15/1/2019 10:07:15 AM","STATUS LOSE","DICE PLACED","FLOWER WON",100,"WINNINGS: 0.0"));
//        betHistories.add(new BetHistory("Lucky Box","17/1/2019 13:25:28","STATUS ACTIVE","BOX C PLACED","WON",300,"WINNINGS: --"));
//        betHistories.add(new BetHistory("Lucky Box","16/1/2019 11:25:20 AM","STATUS WON","BOX A PLACED","BOX A WON",50,"WINNINGS: 135.40"));
//        betHistories.add(new BetHistory("Lucky Card","15/1/2019 10:07:15 AM","STATUS LOSE","DICE PLACED","FLOWER WON", 100,"WINNINGS: 0.0"));
//        betHistories.add(new BetHistory("Lucky Box","17/1/2019 13:25:28","STATUS ACTIVE","BOX C PLACED","WON", 300,"WINNINGS: --"));
        mBetHistoryAdapter = new BetTransactionAdapter(this, R.layout.item_message, betHistories){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);
                if(position %2 == 1)
                {
                    // Set a background color for ListView regular row/item
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                else
                {
                    // Set the background color for alternate row/item
                    view.setBackgroundColor(Color.parseColor("#ADD8E6"));
                }
                return view;
            }
        };
        mBetHistoryListView.setAdapter(mBetHistoryAdapter);


//        ListView listView=(ListView)findViewById(R.id.bet_history_listview);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
//        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//        attachDatabaseReadListener();
    }

    private void attachDatabaseReadListener(){
        if(mChildEventListener==null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    BetHistory betHistory = dataSnapshot.getValue(BetHistory.class);
                    mBetHistoryAdapter.add(betHistory);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

            mDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void dettachDatabaseReadListener(){
        if(mChildEventListener!=null) {
            mDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener=null;
        }

    }
}
