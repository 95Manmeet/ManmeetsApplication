package com.manmeet.manmeetsapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.manmeet.manmeetsapplication.PaymentMethod.PaypalAmountActivity;
import com.manmeet.manmeetsapplication.R;

public class ClientInfoActivity extends AppCompatActivity {

    private EditText username, quantity, allergens, phone, location;
    private Button btnSave;
    private ProgressBar loadingProgress;
    private Intent PayPalAmount_Intent;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_info);


        username = findViewById(R.id.usernameFirebase);
        quantity = findViewById(R.id.quantityFirebase);
        allergens = findViewById(R.id.allergicFirebase);
        phone = findViewById(R.id.phoneFirebase);
        loadingProgress = findViewById(R.id.Info_progressBar);
        location = findViewById(R.id.addressFirebase);
        btnSave = findViewById(R.id.btnFirebase);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference("UserInfo");


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addUsers();
                //updateUI();

            }
        });
    }

    public void addUsers(){

        btnSave.setVisibility(View.INVISIBLE);
        loadingProgress.setVisibility(View.VISIBLE);

        String name = username.getText().toString();
        String tiffin_quant = quantity.getText().toString();
        String food_allergy = allergens.getText().toString();
        String phone_number = phone.getText().toString();
        String user_location = location.getText().toString();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(tiffin_quant) && !TextUtils.isEmpty(food_allergy)
                && !TextUtils.isEmpty(phone_number) && !TextUtils.isEmpty(user_location)) 
        {

            String userId = mDatabaseReference.push().getKey();

            UserDetails user = new UserDetails(userId,name,tiffin_quant,food_allergy,phone_number,user_location);

            mDatabaseReference.child(userId).setValue(user);
            username.setText("");
            quantity.setText("");
            allergens.setText("");
            phone.setText("");
            location.setText("");

            updateUI();


        }
        
        
        else
        {

            btnSave.setVisibility(View.VISIBLE);
            loadingProgress.setVisibility(View.INVISIBLE);
            Toast.makeText(ClientInfoActivity.this, "Please Fill All The Details", Toast.LENGTH_SHORT).show();

        }
    }

    private void updateUI()
    {
        Intent PayPalAmount_Intent = new Intent(getApplicationContext(), PaypalAmountActivity.class);
        startActivity(PayPalAmount_Intent);
        finish();
    }

}
