package com.nofirst.deliveroo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class OrderActivity extends AppCompatActivity {

    //Initialize variables
    EditText address, city, mobile;
    Button add_location;
    RadioGroup radioGroup;

    String order_address, order_city, pay_method;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //Assign Variables
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        add_location = findViewById(R.id.add_location);
        radioGroup = findViewById(R.id.radio_group_pay_method);

        //initialize validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //add validation for address
        awesomeValidation.addValidation(this, R.id.address, RegexTemplate.NOT_EMPTY, R.string.invalid_address);


        //add validation for city
        awesomeValidation.addValidation(this, R.id.city, RegexTemplate.NOT_EMPTY, R.string.invalid_city);

        add_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    //on success
                    Toast.makeText(getApplicationContext(), "Order Details Validated Successfully...", Toast.LENGTH_SHORT).show();

                    order_address = address.getText().toString();
                    order_city = city.getText().toString();
                    int id = radioGroup.getCheckedRadioButtonId();
                    if (id == R.id.money) {
                        pay_method = "money";
                    } else {
                        pay_method = "credit-card";
                    }

                    Intent intent = new Intent(OrderActivity.this, GoogleMapActivity.class);

                    //pass values got by user details
                    intent.putExtra("address", order_address);
                    intent.putExtra("city", order_city);
                    intent.putExtra("pay_method", pay_method);

                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Validation Failed...TryAgain...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
