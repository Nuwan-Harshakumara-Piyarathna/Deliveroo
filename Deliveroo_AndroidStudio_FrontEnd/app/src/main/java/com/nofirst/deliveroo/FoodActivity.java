package com.nofirst.deliveroo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FoodActivity extends AppCompatActivity {


    public static ViewPager viewPager;
    PageAdaptor pageAdaptor;
    static Context context;
    public static List<Food> foodItems;
    static String baseURL = "https://deliveroo-nc.herokuapp.com/foods/find?category=";
    static final String[] categories = new String[]{"Burger","Pasta","Pizza","Rice","HotDog"};
    static RequestQueue requestQueue;
    static int tmpLength = 0;
    static Map<String,Integer> sizes;
    static Map<Integer,String> positions;
    static  Context co;
    public static ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);


        progressBar = findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.VISIBLE);


        String jwt = null;
        try {
            Intent intent_order = getIntent();
            String order_msg = intent_order.getExtras().getString("order-success");
            if(order_msg != null){
                Toast.makeText(getApplicationContext(), "Ordered Successfully...", Toast.LENGTH_SHORT);
            }
        }
        catch (Exception e){

        }

        try {
            SharedPreferences pref = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
            jwt=pref.getString("jwt",null);
            //TextView text = findViewById(R.id.jwt);
            //text.setText(jwt);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(jwt==null){
            Intent intent = new Intent(this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        viewPager = findViewById(R.id.viewP);
        viewPager.setVisibility(View.INVISIBLE);
        pageAdaptor = new PageAdaptor(getSupportFragmentManager());
        viewPager.setAdapter(pageAdaptor);
        context = this;
        foodItems = new ArrayList<Food>();
        sizes = new HashMap<String, Integer>();
        positions = new HashMap<Integer, String>();
        positions.put(0,"Burger");
        positions.put(1,"Pasta");
        positions.put(2,"Pizza");
        positions.put(3,"Rice");
        co = getApplicationContext();
        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();
        Log.i("Started","......OK.......");

    }

    //disable back button
    @Override
    public void onBackPressed(){
        return;
    }

    public void logOut(View view){

        SharedPreferences pref = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        pref.edit().remove("jwt").commit();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);

    }


    public static JsonArrayRequest sendGETRequest(String URL, final String category , final List<Food> fI, final RecyclerView recyclerView){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {

                            Food foodItem;

                            for(int i = 0;i<response.length();i++){

                                foodItem = new Food(
                                        response.getJSONObject(i).get("foodId").toString(),
                                        response.getJSONObject(i).get("foodName").toString(),
                                        response.getJSONObject(i).get("category").toString(),
                                        Double.parseDouble(response.getJSONObject(i).get("price").toString()),
                                        response.getJSONObject(i).get("foodImage").toString()



                                );

                                fI.add(foodItem);
                                sizes.put(category,fI.size());

                                Log.i("data",fI.toString()+" "+fI.size()+"");

                            }
                            RecycleViewAdaptor recycleViewAdaptor = new RecycleViewAdaptor(fI,co);
                            recyclerView.setAdapter(recycleViewAdaptor);
                            recyclerView.setVisibility(View.VISIBLE);
                            FoodActivity.progressBar.setVisibility(View.GONE);
                            viewPager.setVisibility(View.VISIBLE);




                            //recycleViewAdaptor.notifyItemRangeChanged(0,MainActivity.sizes.get(MainActivity.positions.get(PageAdaptor.c)));





                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Request Failed",e.getMessage());
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        return  jsonArrayRequest;
    }

}
