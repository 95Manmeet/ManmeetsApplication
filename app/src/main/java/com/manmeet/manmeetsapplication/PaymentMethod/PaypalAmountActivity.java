package com.manmeet.manmeetsapplication.PaymentMethod;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.manmeet.manmeetsapplication.PaymentMethod.PayPal_API_Key;
import com.manmeet.manmeetsapplication.PaymentMethod.PaymentDetails;
import com.manmeet.manmeetsapplication.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class PaypalAmountActivity extends AppCompatActivity {

    public static final int PAYPAL_REQUEST_CODE = 7171;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // Uses Sandbox Account to test payments
            .clientId(PayPal_API_Key.PAYPAL_CLIENT_ID);

    private EditText amountPayPal;
    private Button btnPayPal;

    private String amount = "";

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_amount);

        //Start PayPal Service
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        amountPayPal = findViewById(R.id.amountPayPal);
        btnPayPal = findViewById(R.id.btnPayPal);

        btnPayPal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                processPayment();

            }
        });


    }

    private void processPayment() {

        amount = amountPayPal.getText().toString();

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "EUR",
                                    "Pay for Tiffin Service", PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent,PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == PAYPAL_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if(confirmation != null)
                {
                    try{
                        String paymentDetails = confirmation.toJSONObject().toString(4);

                        startActivity(new Intent(this, PaymentDetails.class)
                                            .putExtra("PaymentDetails",paymentDetails)
                                            .putExtra("PaymentAmount",amount)
                        );

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            else if(resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(this, "Transcation Cancel", Toast.LENGTH_SHORT).show();
            }
        }

        else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
        {
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
        }

    }
}
