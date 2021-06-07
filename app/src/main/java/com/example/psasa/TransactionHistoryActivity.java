package com.example.psasa;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.psasa.Adapters.BetTransactionAdapter;
import com.example.psasa.Model.BetHistory;
import com.example.psasa.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

// TODO :: create bet & transaction history with functionality (consult designs) create status for bet history (won/lose/active)--(NOT YET)
    // TODO ::  phone number below it-(NOT YET)

public class TransactionHistoryActivity extends AppCompatActivity {

    private BetTransactionAdapter mBetTransactionAdapter;

    // entry point
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_history_activity);

        mFirebaseAuth = FirebaseAuth.getInstance();

        List<BetHistory> arrayList=new ArrayList<BetHistory>();

        ListView listView=(ListView)findViewById(R.id.transaction_history_listview);

        mBetTransactionAdapter=new BetTransactionAdapter(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(mBetTransactionAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        attachDatabaseReadListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dettachDatabaseReadListener();
    }

    private void attachDatabaseReadListener(){
        if(mChildEventListener==null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    BetHistory betHistory = dataSnapshot.getValue(BetHistory.class);
                    mBetTransactionAdapter.add(betHistory);
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

            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
            mMessageDatabaseReference=FirebaseDatabase.getInstance().getReference("bets_history").child(firebaseUser.getUid());
            mMessageDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void dettachDatabaseReadListener(){
        if(mChildEventListener!=null) {
            mMessageDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener=null;
        }

    }

    public void back(View view) {
        finish();
    }
}
