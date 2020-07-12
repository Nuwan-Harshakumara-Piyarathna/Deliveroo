package com.nofirst.deliveroo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText fname,lname,mobileNumber,passwordOne,passwordTwo;
    TextInputLayout fstName,lstName,mobNumber,passOne,passTwo;
    String pNumber,firstName,lastName,password,confirmPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

    private boolean isConnected(RegisterActivity registerActivity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) registerActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo.State mobile = connectivityManager.getNetworkInfo(0).getState();
        NetworkInfo.State wifi = connectivityManager.getNetworkInfo(1).getState();

        if(mobile == NetworkInfo.State.CONNECTED || wifi==NetworkInfo.State.CONNECTED){
            return true;
        }
        else
            return false;


    }

    public void regUser(View view){

         if(!isConnected(RegisterActivity.this)){
            //Log.i("check","running");
            showCustomDialog();
         }else if(!validNumber() || !validFname() || !validLname() || !validPass()){
            return;
         }
         else {

             //Log.i("chek","running");

             mobileNumber = findViewById(R.id.mobile_num);
             pNumber = mobileNumber.getText().toString();
             fname = findViewById(R.id.f_name);
             firstName = fname.getText().toString();
             lname = findViewById(R.id.l_name);
             lastName = lname.getText().toString();
             passwordOne = findViewById(R.id.password_one);
             password = passwordOne.getText().toString();

             JSONObject data = new JSONObject();
             try {
                 data.put("fname", firstName);
                 data.put("lname",lastName);
                 data.put("mobileNumber",pNumber);
                 data.put("password", password);
             } catch (Exception e) {
                 Log.e("ERR", e.toString());
             }

             SendRegReq regReq = new SendRegReq(this, data,RegisterActivity.this);
             regReq.execute("https://deliveroo-nc.herokuapp.com/user/registration");
         }

    }

    //validations
    private boolean validNumber(){
        mobNumber = findViewById(R.id.mob_num);
        mobileNumber = findViewById(R.id.mobile_num);
        pNumber = mobileNumber.getText().toString();

        if(pNumber.isEmpty()){
            mobNumber.setError("Mobile Number cannot be empty");
            return false;
        }
        else if(pNumber.length()!=12) {
            if(!pNumber.substring(0,3).equals("+94")){
                mobNumber.setError("Mobile number should be like +9477*******");
                return false;
            }
            mobNumber.setError("Length is incorrect");
            return false;
        }
        else if(!pNumber.substring(0,3).equals("+94")){
            mobNumber.setError("Mobile number should be like +9477*******");
            return false;
        }

        else if(!pNumber.substring(3,5).equals("77") && !pNumber.substring(3,5).equals("76")
                && !pNumber.substring(3,5).equals("78") && !pNumber.substring(3,5).equals("75")
                && !pNumber.substring(3,5).equals("71") && !pNumber.substring(3,5).equals("72")

        ){

            mobNumber.setError("Mobile number is invalid");
            return false;

        }

        else if(!pNumber.substring(5,12).matches("[0-9]+")) {
            mobNumber.setError("Mobile Number is invalid");
            return false;
        }
        else{
            mobNumber.setError(null);
            mobNumber.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validFname(){

        fstName = findViewById(R.id.fst_name);
        fname = findViewById(R.id.f_name);
        firstName = fname.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if(firstName.isEmpty()){
            fstName.setError("First Name cannot be empty");
            return false;
        }else if(firstName.length()<4){
            fstName.setError("First Name should have at least 4 characters");
            return false;
        }else if (!firstName.matches(noWhiteSpace)) {
            fstName.setError("White Spaces are not allowed");
            return false;
        }
        else{
            fstName.setError(null);
            fstName.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validLname(){
        lstName = findViewById(R.id.lst_name);
        lname = findViewById(R.id.l_name);
        lastName = lname.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if(lastName.isEmpty()){
            lstName.setError("First Name cannot be empty");
            return false;
        }else if(lastName.length()<4){
            lstName.setError("First Name should have at least 4 characters");
            return false;
        }
        else if (!lastName.matches(noWhiteSpace)) {
            lstName.setError("White Spaces are not allowed");
            return false;
        }
        else{
            lstName.setError(null);
            lstName.setErrorEnabled(false);
            return true;
        }

    }

    private boolean validPass(){

        //password one
        passOne = findViewById(R.id.pass_one);
        passwordOne = findViewById(R.id.password_one);
        password = passwordOne.getText().toString();

        //password two
        passTwo = findViewById(R.id.pass_two);
        passwordTwo = findViewById(R.id.password_two);
        confirmPass = passwordTwo.getText().toString();

        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=/*])" +    //at least 1 special character
                ".{8,}" +               //at least 8 characters
                "$";

        if (password.isEmpty()) {
            passOne.setError("Field cannot be empty");
            return false;
        }else if(password.length()<8){
            passOne.setError("Password should have atleast 8 characters");
            return false;
        }
        else if (!password.matches(passwordVal)) {
            passOne.setError("Password is too weak");
            return false;
        }
        else if(confirmPass.isEmpty()){
            passTwo.setError("Field cannot be empty");
            return false;
        }
        else if(!password.equals(confirmPass)){

            passTwo.setError("Password does  not match");
            return false;
        }
        else {
            passOne.setError(null);
            passOne.setErrorEnabled(false);
            return true;
        }

    }

    public void intSignIn(View view){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}

class SendRegReq extends AsyncTask<String,Void,String> {

    JSONObject postData;
    int status;
    Context con;
    LoadingDialog loadingDialog;

    public SendRegReq(Context context, JSONObject data, Activity activity){
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

        loadingDialog.dismissDialog();
        //Log.i("data",data);
        if(data.equals(" mobileNumber is already existnull")){

            Toast toast=Toast.makeText(con, "Already have an account? try with different Mobile Number", Toast.LENGTH_SHORT);
            toast.show();

        }
        else if(data.equals( " successfully registerednull")){
            Toast toast=Toast.makeText(con, "Registered Successfully", Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(con,LoginActivity.class);
            con.startActivity(intent);
        }
        else{
            Toast toast=Toast.makeText(con, "Something went wrong Try again later", Toast.LENGTH_SHORT);
            toast.show();


        }


    }
}
