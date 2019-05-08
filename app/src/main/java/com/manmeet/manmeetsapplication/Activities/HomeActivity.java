package com.manmeet.manmeetsapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.manmeet.manmeetsapplication.R;

public class HomeActivity extends AppCompatActivity {

    private CardView one_month_offer, three_month_offer, six_month_offer;
    private Intent ClientActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        one_month_offer = findViewById(R.id.OfferOne);
        three_month_offer = findViewById(R.id.OfferTwo);
        six_month_offer = findViewById(R.id.OfferThree);
        ClientActivity = new Intent(this, ClientInfoActivity.class);


        one_month_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ClientInfoActivity = new Intent(getApplicationContext(), ClientInfoActivity.class);
                startActivity(ClientInfoActivity);

            }
        });


        three_month_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ClientInfoActivity = new Intent(getApplicationContext(), ClientInfoActivity.class);
                startActivity(ClientInfoActivity);

            }
        });


        six_month_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ClientInfoActivity = new Intent(getApplicationContext(), ClientInfoActivity.class);
                startActivity(ClientInfoActivity);

            }
        });


    }
}
