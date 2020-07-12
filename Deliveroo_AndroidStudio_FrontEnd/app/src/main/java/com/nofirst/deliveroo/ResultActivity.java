package com.nofirst.deliveroo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.nofirst.deliveroo.database.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ResultActivity extends AppCompatActivity {




    public static String name;
    public static LatLng latLng;
    public static String address;
    public static String city;
    public static String pay_method;
    public static List<String[]> list;
    public static String jwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        DataSource dataSource = new DataSource(getApplicationContext());
        dataSource.open();

        jwt = null;

        try {
            SharedPreferences pref = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            jwt = pref.getString("jwt", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (jwt == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        list = dataSource.getAllDataOrderDetails();

        Intent intent = getIntent();
        name = intent.getStringExtra("locality_name");
        latLng = intent.getExtras().getParcelable("latLng");
        address = intent.getExtras().getString("address");
        city = intent.getExtras().getString("city");
        pay_method = intent.getExtras().getString("pay_method");



        sendDataToServer();

    }

    public void sendDataToServer() {
        //function in the activity that corresponds to the layout button

        JSONObject postDict = new JSONObject();

        JSONArray jsonArray = new JSONArray();


        try {
            for (int i = 0; i < list.size(); i++) {
                String[] strArr = list.get(i);
                JSONObject postedJSON = new JSONObject();
                postedJSON.put("food_id", strArr[0]);
                postedJSON.put("quantity", strArr[3]);
                jsonArray.put(postedJSON);
            }

            postDict.put("user", jwt);
            postDict.put("pay_method", pay_method);
            postDict.put("latitude", latLng.latitude);
            postDict.put("longitude", latLng.longitude);
            postDict.put("address", address);
            postDict.put("city", city);
            postDict.put("list", jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        SendDataToServer orderReq = new SendDataToServer(this, postDict);
        orderReq.execute("https://deliveroo-nc.herokuapp.com/users/orders/add");
    }

    class SendDataToServer extends AsyncTask<String, Void, String> {

        JSONObject postData;
        int status;
        Context context;

        public SendDataToServer(Context context, JSONObject data) {
            this.postData = data;
            this.context = context;
        }


        @Override
        protected String doInBackground(String... parms) {

            String data = " ";
            String line = "";

            try {
                URL url = new URL(parms[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Authorization", "Bearer " + jwt);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
//                textView1.setText(postData.toString());
                writer.write(postData.toString());
                writer.flush();
                status =urlConnection.getResponseCode();
                BufferedReader bf;
                if(status<400) {
                    bf = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                }
                else{
                    bf = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
                }

                while (line != null) {
                    line = bf.readLine();
                    data += line;
                }

            } catch (Exception e) {
                System.out.println(e);
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String data) {

            Intent intent = new Intent(ResultActivity.this,FoodActivity.class);
            startActivity(intent);
            intent.putExtra("order-success","Ordered Successfully...");
        }
    }

}

