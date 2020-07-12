package com.nofirst.deliveroo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends ArrayAdapter<String[]> {

    static DecimalFormat df = new DecimalFormat("0.00");

    public CartAdapter(@NonNull Context context, List<String[]> list) {
        super(context, R.layout.cart_row,list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.cart_row,parent,false);

        //get a reference
        String[] singleOrderItem =  getItem(position);
        TextView foodName = customView.findViewById(R.id.foodName);
        TextView price = customView.findViewById(R.id.price);
        TextView quantity = customView.findViewById(R.id.quantity);

        assert singleOrderItem != null;
        foodName.setText(singleOrderItem[1]);
        price.setText(df.format(Double.parseDouble(singleOrderItem[2]))+" Rs");
        quantity.setText(singleOrderItem[3]);
        return customView;
    }
}
