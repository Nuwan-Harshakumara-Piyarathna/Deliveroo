package com.nofirst.deliveroo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.nofirst.deliveroo.database.DataSource;

public class MainActivity extends AppCompatActivity {

    ///5 seconds
    private static int splashScreen = 5000;

    Animation topAnim,bottomAnim;
    ImageView logo;
    TextView name;

    public DataSource dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //set wifi connection
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);

        //recreate table
        dataSource = new DataSource(getApplicationContext());
        dataSource.open();
        dataSource.deleteTableCart();
        //this must happen only once during execution of the app


        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logo = findViewById(R.id.logo);
        name = findViewById(R.id.textView);

        logo.setAnimation(topAnim);
        name.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,FoodActivity.class);
                startActivity(intent);
                finish();
            }
        },splashScreen);

    }
}
