package com.cs122b.fabflix;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class LoginPage extends AppCompatActivity {
    private EditText email;
    private EditText password;
    public String loginStatus = "false";
    public HashMap<String,String> loginMap = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        email = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        String url = "http://10.0.2.2:8080/fabflix2/fablixmobile/mobilelogin"; //change to aws url

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray loginList = response.getJSONArray("loginList");
                    System.out.println("SIZE: " + loginList.length());
                    for(int i = 0;i<loginList.length();i++) {
                        String addPassword = loginList.getJSONObject(i).getString("password");
                        String addEmail = loginList.getJSONObject(i).getString("email");
                        loginMap.put(addEmail,addPassword);
                    }
                }
                catch(JSONException e) {
                    System.out.println(e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });

        queue.add(jsonObjectRequest);



    }



    public void login(View v) {
        EditText email = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);




        final TextView mTextView = (TextView) findViewById(R.id.text);

        if(loginMap.containsKey(email.getText().toString())){
            if(loginMap.get(email.getText().toString()).equals(password.getText().toString())){
                loginStatus = "true";
            }
        }
        if(loginStatus.equals("true")) {
            Intent goToSearch = new Intent(this, SearchPage.class);
            startActivity(goToSearch);
        }
    }

}
