package com.manmeet.manmeetsapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.manmeet.manmeetsapplication.PaymentMethod.PaypalAmountActivity;
import com.manmeet.manmeetsapplication.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseAuth mAuth;
    FirebaseUser currentUser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Home.this.finish();
                Intent startDashBoardActivity = new Intent(Home.this, HomeActivity.class);
                startActivity(startDashBoardActivity);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateNavHeader();

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent Home_Drawer = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(Home_Drawer);

        } else if (id == R.id.nav_slideshow) {

            Intent Intro_Drawer = new Intent(getApplicationContext(), Home.class);
            startActivity(Intro_Drawer);

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_signout) {

            FirebaseAuth.getInstance().signOut();
            Intent SignOut_Drawer = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(SignOut_Drawer);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavHeader(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_username);
        TextView navUserEmail = headerView.findViewById(R.id.nav_user_email);
        CircleImageView navUserPhoto = headerView.findViewById(R.id.nav_user_photo);

        navUserEmail.setText(currentUser.getEmail());
        navUsername.setText(currentUser.getDisplayName());

        // Here We will use Glide to load user image.
        // To do that we have to import the library.

        navUserPhoto.setImageDrawable(getResources().getDrawable(R.drawable.userprofileadd));

        /*
        First You need to save the proffile picture when user sign up and save it in firabase
        database then retrieve it and use GLIDE otherwise it shows no image
        */

        Glide.with(this).load(currentUser.getPhotoUrl()).into(navUserPhoto);



    }

}
