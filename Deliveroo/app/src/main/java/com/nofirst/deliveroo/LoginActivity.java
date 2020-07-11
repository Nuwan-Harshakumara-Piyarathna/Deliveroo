package com.nofirst.deliveroo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginActivity extends AppCompatActivity {

    TextInputEditText pass,num;
    TextInputLayout passWd,number;
    String password,mNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please connect to the internet to proceed")
        .setCancelable(false).setPositiveButton("Okay",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog,int i){
                //Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                //intent.setClassName("com.android.phone","com.android.phone.NetworkSetting");
               // startActivity(intent);
                return;
            }
        }).setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int i){
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        }).show();
    }

    private boolean isConnected(LoginActivity loginActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) loginActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State mobile = connectivityManager.getNetworkInfo(0).getState();
        NetworkInfo.State wifi = connectivityManager.getNetworkInfo(1).getState();

        if(mobile == NetworkInfo.State.CONNECTED || wifi==NetworkInfo.State.CONNECTED){
            return true;
        }
        else
            return false;


    }

    public void signUp(View view){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    public void signIn(View view){

        if(!isConnected(this)){
            //Log.i("check","running");
            showCustomDialog();
        }

        else if(!validNumber() || !validPasswd()){
            return;
        }
        else {
            num = findViewById(R.id.number);
            mNumber = num.getText().toString();

            pass = findViewById(R.id.pass);
            password = pass.getText().toString();

            JSONObject data = new JSONObject();
            try {
                data.put("mobileNumber", mNumber);
                data.put("password", password);
            } catch (Exception e) {
                Log.e("ERR", e.toString());
            }

            SendLoginReq loginReq = new SendLoginReq(this, data,LoginActivity.this);
            loginReq.execute("https://deliveroo-nc.herokuapp.com/user/login");
        }



    }
    private boolean validNumber(){

        number = findViewById(R.id.mobile_number);
        num = findViewById(R.id.number);
        mNumber = num.getText().toString();

        if(mNumber.isEmpty()){
            number.setError("Mobile Number cannot be empty");
            return false;
        }else if(mNumber.length()!=12) {
            if(!mNumber.substring(0,3).equals("+94")){
                number.setError("Mobile number should be like +9477*******");
                return false;
            }
            number.setError("Length is incorrect");
            return false;
        }
        else if(!mNumber.substring(0,3).equals("+94")){
            number.setError("Mobile number should be like +9477*******");
            return false;
        }

        else if(!mNumber.substring(3,5).equals("77") && !mNumber.substring(3,5).equals("76")
                && !mNumber.substring(3,5).equals("78") && !mNumber.substring(3,5).equals("75")
                && !mNumber.substring(3,5).equals("71") && !mNumber.substring(3,5).equals("72")

        ){

            number.setError("Mobile number is invalid");
            return false;

        }

        else if(!mNumber.substring(5,12).matches("[0-9]+")) {
            number.setError("Mobile Number is invalid");
            return false;
        }
        else{
            number.setError(null);
            number.setErrorEnabled(false);
            return true;
        }

    }
    private boolean validPasswd(){

        passWd = findViewById(R.id.password);
        pass = findViewById(R.id.pass);
        password = pass.getText().toString();

        if(password.isEmpty()){
            passWd.setError("Password cannot be empty");
            return false;
        }
        else{
            passWd.setError(null);
            passWd.setErrorEnabled(false);
            return true;
        }

    }


}

class SendLoginReq extends AsyncTask<String,Void,String>{

    JSONObject postData;
    int status;
    Context con;
    LoadingDialog loadingDialog;

    public SendLoginReq(Context context, JSONObject data, Activity activity){
        this.postData = data;
        this.con = context;
        loadingDialog =new LoadingDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog.startLoadindDialog();
    }



    @Override
    protected String doInBackground(String... parms){

        String line = "";
        String data=" ";


        try{
            URL url = new URL(parms[0]);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setRequestMethod("POST");


            if(this.postData!=null){
                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write(postData.toString());
                writer.flush();
            }
            status =urlConnection.getResponseCode();
            BufferedReader bf;
            if(status<400) {
                 bf = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            }
            else{
                bf = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
            }

           // Log.i("code",Integer.toString(status));

                while (line != null) {
                    line = bf.readLine();
                    data += line;
                }

        }catch (Exception e){
            e.printStackTrace();
        }


        return data;

    }
    @Override
    protected void onPostExecute(String data){

        //Log.i("data",data);
        JSONObject token;
        String jwt=null;

        if(status == 200) {
            try {
                token = new JSONObject(data);
                jwt = token.get("jwt").toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        loadingDialog.dismissDialog();
        if(data.equals(" Incorrect MobileNumber or Password.null")){
            Toast toast=Toast.makeText(con, "Incorrect MobileNumber or Password", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(data.equals(" user not foundnull")){
            Toast toast=Toast.makeText(con, "User not found", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(status == 200 && jwt !=null){

            try {

                SharedPreferences pref = con.getSharedPreferences("MyPreferences",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("jwt",jwt);
                editor.commit();
                Intent intent = new Intent(con,FoodActivity.class);
                con.startActivity(intent);


            }catch (Exception e){
                e.printStackTrace();
            }



        }else{
            Toast toast=Toast.makeText(con, "Something went wrong Try again later", Toast.LENGTH_SHORT);
            toast.show();

        }

    }
}
