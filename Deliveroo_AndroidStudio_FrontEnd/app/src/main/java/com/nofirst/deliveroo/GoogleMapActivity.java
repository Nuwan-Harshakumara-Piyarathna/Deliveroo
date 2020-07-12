package com.nofirst.deliveroo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;


public class GoogleMapActivity extends AppCompatActivity {

    //initialize constants
    private static final int DEFAULT_ZOOM = 10;
    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String INTERNET = Manifest.permission.INTERNET;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 44;
    private static final int INTERNET_PERMISSION_REQUEST_CODE = 89;


    //initialize variables
    AutoCompleteTextView editText;
    Button btn_confirm;
    ImageView my_location;


    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    GoogleMap mMap;

    private Boolean mLocationPermissionGranted = false;

    //passing variables
    public static LatLng latLng_order;
    public static String place_order = "My Location";

    //passed variables from order details
    String address,city,pay_method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        statusCheck();

        //Assign Variable
        editText = findViewById(R.id.input_search);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        btn_confirm = findViewById(R.id.btn_confirm);
        my_location = findViewById(R.id.ic_gps);

        //get variables from OrderActivity
        Intent intent = getIntent();
        address = intent.getExtras().getString("address");
        city = intent.getExtras().getString("city");
        pay_method = intent.getExtras().getString("pay_method");

        //initialize places
        Places.initialize(getApplicationContext(), "AIzaSyCjCQnZYLFWIffPayLRw7crWKYzjoJ8UIY");

        //initialize fused Location
        client = LocationServices.getFusedLocationProviderClient(this);

        getCurrentLocation();

        init();

    }
    private void init() {
        //set editText Non focusable
        editText.setFocusable(false);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initialize place field list
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                //create intent
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList)
                        .build(GoogleMapActivity.this);
                //Start Activity result
                startActivityForResult(intent, 100);
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(latLng_order == null){
                    Toast.makeText(getApplicationContext(),"location is not selected...", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent_result = new Intent(GoogleMapActivity.this, ResultActivity.class);
                    intent_result.putExtra("locality_name", place_order);
                    intent_result.putExtra("latLng", latLng_order);
                    intent_result.putExtra("address", address);
                    intent_result.putExtra("city",city);
                    intent_result.putExtra("pay_method",pay_method);
                    startActivity(intent_result);
                }

            }
        });

        my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation();
            }
        });
    }



    private void getCurrentLocation() {
//        Toast.makeText(getApplicationContext(),"My Location function called...",Toast.LENGTH_SHORT).show();
        String permissions[] = {FINE_LOCATION,COARSE_LOCATION};
        //initialize task location
        //check fine,coarse location permission
        if (ActivityCompat.checkSelfPermission(GoogleMapActivity.this, FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(GoogleMapActivity.this, COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //when permission denied
            //Request Permission
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
        else{
            mLocationPermissionGranted = true;
            //when permission granted
            //call method
            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(final Location location) {
                    //when success
                    if(location != null){
                        //sync map
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {

                                mMap = googleMap;
                                //initialize lat lng
                                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                                latLng_order = latLng;

                                moveCamera(latLng, DEFAULT_ZOOM, "My Location");

                                if (ActivityCompat.checkSelfPermission(GoogleMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GoogleMapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    return;
                                }
                                mMap.setMyLocationEnabled(true);
                            }
                        });
                    }
                }
            });
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if (grantResults.length > 0){
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionGranted = true;
                    //recreate activity
                    finish();
                    startActivity(getIntent());
                }
                break;
            }
            case INTERNET_PERMISSION_REQUEST_CODE:{
                if (grantResults.length > 0){
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionGranted = true;
                    //recreate activity
                    finish();
                    startActivity(getIntent());
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            //when success
            //initialize place
            Place place = Autocomplete.getPlaceFromIntent(data);
            //set address on EditText
            editText.setText(place.getAddress());

            latLng_order = place.getLatLng();
            place_order = place.getAddress();

            //move camera
            moveCamera(latLng_order, DEFAULT_ZOOM, place_order);
        }
        else if(resultCode == AutocompleteActivity.RESULT_ERROR) {
            //when error
            //initialize status
            Status status = Autocomplete.getStatusFromIntent(data);
            //display toast
            Toast.makeText(getApplicationContext(),status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: moving the camera to : latitude = " + latLng.latitude + " , longitude = " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        //create marker options
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title(title);
        //zoom map
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
        //add marker on map
        mMap.addMarker(options);

    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            //recreate activity
            finish();
            startActivity(getIntent());
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}