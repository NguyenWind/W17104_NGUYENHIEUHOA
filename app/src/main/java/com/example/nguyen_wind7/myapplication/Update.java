package com.example.nguyen_wind7.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Update extends Activity {
    EditText edt1,edt2,edt3,edt4;
    Button btUpdate,btCancel;
    String urlUpdate = "http://192.168.0.4:8888/myPHP/updateDATA.php";
    Data dt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        init();
        Intent intent = getIntent();
        dt = (Data) intent.getSerializableExtra("data");
        edt1.setText(dt.getName());
        edt2.setText(dt.getCl());
        edt3.setText(dt.getMail());
        edt4.setText(dt.getPhone());

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt1.getText().toString().trim();
                String cl = edt2.getText().toString().trim();
                String mail = edt3.getText().toString().trim();
                String phone = edt4.getText().toString().trim();
                if (name.isEmpty() || cl.isEmpty()||mail.isEmpty()||phone.isEmpty()){
                    Toast.makeText(Update.this,"Please Input All Information",Toast.LENGTH_LONG).show();
                }else {
                    Update(urlUpdate);
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
        edt1 = (EditText)findViewById(R.id.edtName);
        edt2 = (EditText)findViewById(R.id.edtClass);
        edt3 = (EditText)findViewById(R.id.edtMail);
        edt4 = (EditText)findViewById(R.id.edtPhone);
        btUpdate = (Button)findViewById(R.id.btUpdate);
        btCancel = (Button)findViewById(R.id.btCancel);
    }

    private void Update(String url){
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("SUCCESS")){
                    Toast.makeText(Update.this,"Success",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Update.this,MainActivity.class));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Update.this,"Error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",String.valueOf(dt.getId()));
                params.put("name",edt1.getText().toString().trim());
                params.put("class",edt2.getText().toString().trim());
                params.put("mail",edt3.getText().toString().trim());
                params.put("phone",edt4.getText().toString().trim());
                return params;
            }
        };
        Singleton.getInstance(this).getRequestQueue().add(stringRequest);
    }

}
