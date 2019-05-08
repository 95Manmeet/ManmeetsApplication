package com.manmeet.manmeetsapplication.PaymentMethod;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.manmeet.manmeetsapplication.Activities.Home;
import com.manmeet.manmeetsapplication.Activities.HomeActivity;
import com.manmeet.manmeetsapplication.Activities.LoginActivity;
import com.manmeet.manmeetsapplication.Activities.RegisterActivity;
import com.manmeet.manmeetsapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetails extends AppCompatActivity {

    private TextView textId;
    private TextView textAmount;
    private TextView textStatus;
    private Button btnDash;
    private Button btnSignOut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        textId = findViewById(R.id.textId);
        textAmount = findViewById(R.id.textAmount);
        textStatus = findViewById(R.id.textStatus);
        btnDash = findViewById(R.id.btnDash);
        btnSignOut = findViewById(R.id.btnSignOut);

        //Get Intent
        Intent intent = getIntent();

        btnDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Dasboard = new Intent(getApplicationContext(), Home.class);
                startActivity(Dasboard);

            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignOut = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(SignOut);
            }
        });


        try{
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("PaymentDetails"));
            showDetails(jsonObject.getJSONObject("response"),intent.getStringExtra("PaymentAmount"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showDetails(JSONObject response, String paymentAmount) {

        try {
            textId.setText(response.getString("id"));
            textStatus.setText(response.getString("state"));
            textAmount.setText(paymentAmount + " Euro");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
