package com.nofirst.deliveroo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.nofirst.deliveroo.database.DataSource;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    List<String[]> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);

        DataSource dataSource = new DataSource(getApplicationContext());
        dataSource.open();


        list = dataSource.getAllDataOrderDetails();

        ListAdapter myAdapter = new CartAdapter(this,list);

        ListView listView = findViewById(R.id.myListView);
        listView.setAdapter(myAdapter);

        Button btn_PlaceOrder = findViewById(R.id.btn_PlaceOrder);

        btn_PlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.size() == 0){
                    Toast.makeText(getApplicationContext(),"Cart is Empty...", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(CartActivity.this,OrderActivity.class);
                    startActivity(intent);
                }

            }
        });


    }

}
