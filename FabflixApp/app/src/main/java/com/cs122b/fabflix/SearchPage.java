package com.cs122b.fabflix;

import android.app.DownloadManager;
import android.content.Intent;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ishan_000 on 5/17/2016.
 */
public class SearchPage extends AppCompatActivity {
    TextView newText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        newText = (TextView)findViewById(R.id.newtext);

        Intent intent = getIntent();

    }

    public void search(View v){
        EditText title = (EditText)findViewById(R.id.movieTitle);
        String add = title.getText().toString();
        if(title.getText().toString().contains(" ")){
            add = add.replaceAll("\\s+","%20");
        }
        String url = "http://10.0.2.2:8080/fabflix2/fablixmobile/MobileSearch/?movieTitle="+add;
        //change url to aws ip

        newText.setText("");

        RequestQueue queue = Volley.newRequestQueue(this);

        System.out.println(url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray movieList = response.getJSONArray("movielist");
                    Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();

                    for(int i = 0; i<movieList.length();i++) {
                        System.out.println(movieList.getJSONObject(i).getString("title"));
                        newText.append(movieList.getJSONObject(i).getString("title")+"\n");
                    }
                }
                catch(JSONException e) {
                    System.out.println(e.getMessage());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                newText.setText(error.getMessage());
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });

        queue.add(jsonObjectRequest);

    }


}
