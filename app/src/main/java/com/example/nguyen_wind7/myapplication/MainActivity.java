package com.example.nguyen_wind7.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    RecyclerView recyclerView;
    List<Data> data;
    DataApdater apdater;
    String url = "http://192.168.0.4:8888/myPHP/getDATA.php";
    String urlDelete = "http://192.168.0.4:8888/myPHP/deleteDATA.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.rv_data);
        data = new ArrayList<>();
        apdater = new DataApdater(data,this);
        GetData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(apdater);





    }

    private void GetData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                data.clear();
                for (int i = 0;i<response.length();i++){

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        data.add(new Data(
                                jsonObject.getString("Name"),
                                jsonObject.getString("Class"),
                                jsonObject.getString("Mail"),
                                jsonObject.getString("Phone"),
                                jsonObject.getInt("Id")
                                ));

                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
                apdater.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        Singleton.getInstance(this).getRequestQueue().add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_student,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuAddStudent){
            startActivity(new Intent(MainActivity.this,Add.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void delete(final int idStudent){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("SUCCESS")){
                    Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
                    GetData();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
                Log.d("Error",error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",String.valueOf(idStudent));
                return params;
            }
        };
        Singleton.getInstance(this).getRequestQueue().add(stringRequest);
    }
}
