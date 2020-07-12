package com.nofirst.deliveroo;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.JsonArrayRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView textView;
    private Context context;
    Button cart;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static View view;
    //static  RecyclerView recyclerView;
    //static  RecycleViewAdaptor recycleViewAdaptor;
    private List<Food> fI;

    public FoodFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodFragment newInstance(String param1, String param2) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_food, container, false);
        cart = view.findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),CartActivity.class);
                startActivity(intent);
            }
        });

        fI = new ArrayList<Food>();
        RecyclerView recyclerView = FoodFragment.view.findViewById(R.id.reView);
        TextView textView = FoodFragment.view.findViewById(R.id.cate);
        textView.setText(FoodActivity.categories[getArguments().getInt("pos")]);
        recyclerView.setLayoutManager(new LinearLayoutManager(FoodActivity.context));
       // recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.GONE);

        Log.i(".......category.......",FoodActivity.baseURL+""+FoodActivity.categories[getArguments().getInt("pos")]);
        JsonArrayRequest jsonArrayRequest = FoodActivity.sendGETRequest(FoodActivity.baseURL+""+FoodActivity.categories[getArguments().getInt("pos")],FoodActivity.categories[getArguments().getInt("pos")],fI,recyclerView);
        FoodActivity.requestQueue.add(jsonArrayRequest);

        Log.i("Length",FoodActivity.foodItems.size()+"");

        return  view;


    }
}
