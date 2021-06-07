package com.example.psasa;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.psasa.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// TODO :: Create wallet for every deposited customer and retrievable tables for them, functionality upon deposit, withdraw and after a customer bets-(NOT YET)
    // TODO :: Verification for only register and forgot password pages-(NOT YET)
    // TODO :: Invite users to write and send custom messages to person's ticked contacts upon enter send(NOT YET)
    // TODO :: when not logged in upon clicking nav-icon takes you to log in page, one with register(NOT YET)
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TextView usersNumber ;
//    private Menu menu;

    public static final String MyPREFERENCES = "com.example.psasa.PREFERENCE_FILE_KEY";
    private SharedPreferences prefs;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private static final int RC_SIGN_IN = 1; // RC for request code

    // entry point
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;

    private String no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.drawable.ic_account_circle_white_24dp));
        setSupportActionBar(toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(MainActivity.this, "Nav clicked", Toast.LENGTH_LONG).show();
//                return;
//            }
//        });





       mFirebaseAuth = FirebaseAuth.getInstance();


//        Toast.makeText(MainActivity.this, "AAAA", Toast.LENGTH_SHORT).show();
//       updateMenuTitles(mFirebaseAuth.getCurrentUser());
//        Toast.makeText(MainActivity.this, "BBBB", Toast.LENGTH_SHORT).show();

        prefs = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        no = prefs.getString("phone_number","");
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

//        if(firebaseUser !=null){
//            updateMenuTitles(firebaseUser);
//        }

        invalidateOptionsMenu();




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View header = navigationView.getHeaderView(0);
        usersNumber= (TextView) header.findViewById(R.id.user_number);
        usersNumber.setText(prefs.getString("phone_number","phoneNumber"));

        prefs.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);

                        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        Toast.makeText(MainActivity.this," trying",Toast.LENGTH_SHORT).show();
                        if(user != null){
                            // user signed in
//                            updateMenuTitles(user);
                            Toast.makeText(MainActivity.this," You're now signed in, Welcome to Pesa sasa",Toast.LENGTH_SHORT).show();
                            invalidateOptionsMenu();
//                        onSignedInInitialize(user.getDisplayName());
                        }else{
                            //user sugned out
//                        onSignedOutCleanup();
//                            updateMenuTitles(user);
//                            Toast.makeText(MainActivity.this," trying",Toast.LENGTH_SHORT).show();
//                            startActivityForResult(
//                                    AuthUI.getInstance()
//                                            .createSignInIntentBuilder()
//                                            .setIsSmartLockEnabled(false)
//                                            .setAvailableProviders(Arrays.asList(
//                                                    new AuthUI.IdpConfig.EmailBuilder().build(),
//                                                    new AuthUI.IdpConfig.PhoneBuilder().build()))
//                                            .build(),
//                                    RC_SIGN_IN);
                        }
                    }
                };
//                startActivity(new Intent(getApplicationContext(), SignInUpActivity.class));


        LinearLayout luckyBox=(LinearLayout)findViewById(R.id.lucky_box_layout);
        luckyBox.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                if(mFirebaseAuth.getCurrentUser() != null)
                {
                    startActivity(new Intent(getApplicationContext(),LuckyBox.class));
                }
                if(mFirebaseAuth.getCurrentUser() == null){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                        startActivity(new Intent(getApplicationContext(), SignInUpActivity.class),options.toBundle());
                    }else{
                        startActivity(new Intent(getApplicationContext(), SignInUpActivity.class));
                    }
                }

            }
        });

        LinearLayout luckyCard=(LinearLayout)findViewById(R.id.lucky_card_layout);
        luckyCard.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(mFirebaseAuth.getCurrentUser() != null)
                {
                    startActivity(new Intent(getApplicationContext(),LuckyCard.class));
                }
                if(mFirebaseAuth.getCurrentUser() == null){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                        startActivity(new Intent(getApplicationContext(), SignInUpActivity.class),options.toBundle());
                    }else{
                        startActivity(new Intent(getApplicationContext(), SignInUpActivity.class));
                    }
                }
            }
        });

    }

    SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//            if (key.equals("your_key")){
//                // Write your code here
//            }
            usersNumber.setText(prefs.getString("phone_number","oya oya"));
        }
    };
    @Override
    protected void onPause() {
        super.onPause();
//        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }


    /**
     *
     * @param menu optionsMenu to be updated at runtime
     * @return menu
     *
     * remember to invalidateOptionsMenu()
     */
    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        // get current logged in user
        if(mFirebaseAuth.getCurrentUser()!= null){

            menu.findItem(R.id.action_login).setVisible(true).setTitle(prefs.getString("phone_number",""));
            menu.findItem(R.id.action_sign_up).setVisible(true).setTitle("Sign out");

        } else{
            menu.findItem(R.id.action_login).setVisible(true).setTitle(R.string.action_login);
            menu.findItem(R.id.action_sign_up).setVisible(true).setTitle(R.string.action_sign_up);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(mFirebaseAuth.getCurrentUser()==null){
            //noinspection SimplifiableIfStatement
            if (id == R.id.action_sign_up) {
                startActivity(new Intent(getApplicationContext(),ActivityPhoneSingIn.class));
            }
            if( id == R.id.action_login){
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        }
        if(mFirebaseAuth.getCurrentUser()!=null){
            if (id == R.id.action_sign_up) {
                userSignOut();
            }
        }


        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.deposit) {
            // TODO: add deposit activity
        } else if (id == R.id.bet_history) {
            Intent i = new Intent(getApplicationContext(), BetHistoryActivity.class);
            startActivity(i);
        } else if (id == R.id.withdraw) {
            // TODO: Add withdraw activity
        } else if (id == R.id.transaction_history) {
            Intent i = new Intent(getApplicationContext(), TransactionHistoryActivity.class);
            startActivity(i);
        }else if (id == R.id.change_password) {
            Intent i = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
            startActivity(i);
        }else if (id == R.id.invite_friends) {

        }else if (id == R.id.faqs) {

        } else if (id == R.id.contact_us) {

        } else if (id == R.id.terms_and_conditions) {

        } else if (id == R.id.log_out) {
            userSignOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void userSignOut(){
        mFirebaseAuth.getInstance().signOut();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("phone_number", "");
        editor.apply();
        invalidateOptionsMenu();
    }

}
