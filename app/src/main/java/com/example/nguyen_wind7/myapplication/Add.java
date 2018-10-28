package com.example.nguyen_wind7.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Add extends Activity {
    EditText edtName,edtClass,edtMail,edtPhone;
    Button btAdd,btCancel;
    String urlInsert = "http://192.168.0.4:8888/myPHP/insertDATA.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        init();

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String cl = edtClass.getText().toString().trim();
                String mail = edtMail.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                if (name.isEmpty() || cl.isEmpty()||mail.isEmpty()||phone.isEmpty()){
                    Toast.makeText(Add.this,"Please Input All Information",Toast.LENGTH_LONG).show();
                }else {
                    Add(urlInsert);
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void init(){
        edtName = (EditText)findViewById(R.id.edtNameAdd);
        edtClass = (EditText)findViewById(R.id.edtClassAdd);
        edtMail = (EditText)findViewById(R.id.edtMailAdd);
        edtPhone = (EditText)findViewById(R.id.edtPhoneAdd);
        btAdd =(Button)findViewById(R.id.btAdd);
        btCancel =(Button)findViewById(R.id.btCancel);
    }

    public void Add(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("SUCCESS")){
                    Toast.makeText(Add.this,"Success",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Add.this,MainActivity.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Add.this,"Error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",edtName.getText().toString());
                params.put("class",edtClass.getText().toString());
                params.put("mail",edtMail.getText().toString());
                params.put("phone",edtPhone.getText().toString());
                return params;
            }
        };
        Singleton.getInstance(Add.this).getRequestQueue().add(stringRequest);
    }

}
