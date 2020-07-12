package com.nofirst.deliveroo;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nofirst.deliveroo.database.DataSource;
import com.nofirst.deliveroo.database.FoodModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RecycleViewAdaptor extends RecyclerView.Adapter<RecycleViewAdaptor.ViewHolder> {

    private List<Food> fI;
    private Context context;
    public DataSource dataSource;
    public RecycleViewAdaptor(List<Food> fI, Context context) {
        this.fI = fI;
        this.context = context;
        dataSource = new DataSource(context);
        dataSource.open();
    }

    public RecycleViewAdaptor() {
    }

    @NonNull
    @Override
    public ViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.name.setText(fI.get(position).getFoodName());
        holder.price.setText(String.valueOf(fI.get(position).getPrice()));
        Glide.with(context).asBitmap().load(fI.get(position).getFoodImage()).into(holder.imageView);

        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantity = holder.quantity.getText().toString();
                String food_id = fI.get(position).getFoodId();
                double price = fI.get(position).getPrice();
                String food_name = fI.get(position).getFoodName();;
//                Toast.makeText(context, quantity + "  "+price+"  "+food_id,Toast.LENGTH_SHORT).show();
                if(quantity == null){
                    Toast.makeText(context,"Quantity cannot be null",Toast.LENGTH_SHORT).show();
                }
                else if(food_id.trim().length() == 0|| quantity.trim().length() == 0){
                    Toast.makeText(context,"Quantity cannot be empty",Toast.LENGTH_SHORT).show();
                }
                else{
                    dataSource.insertDataOrderDetails(food_id,food_name,price,Integer.parseInt(quantity));
                    Toast.makeText(context,"Added to the cart",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        Log.e(".....length...........",fI.size()+"");

        return fI.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,price;
        ImageView imageView;
        EditText quantity;
        Button order;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.foodName);
            price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.foodImage);
            quantity = itemView.findViewById(R.id.quantitiy);
            order = itemView.findViewById(R.id.order);
        }

    }

}


