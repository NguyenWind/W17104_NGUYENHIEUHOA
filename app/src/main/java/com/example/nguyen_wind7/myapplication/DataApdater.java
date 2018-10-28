package com.example.nguyen_wind7.myapplication;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nguyen_wind7 on 2018/08/03.
 */

public class DataApdater extends RecyclerView.Adapter<DataApdater.DataItemViewholder> {
    List<Data> data;
    MainActivity context;

    public DataApdater(List<Data> data, MainActivity context) {
        this.data = data;
        this.context = context;
    }

    @NonNull

    @Override
    public DataApdater.DataItemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data,parent,false);


        return new DataItemViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataApdater.DataItemViewholder holder, final int position) {
        final Data dt = data.get(position);
        holder.tvName.setText(dt.getName()+"");
        holder.tvClass.setText(dt.getCl()+"");
        holder.tvMail.setText(dt.getMail()+"");
        holder.tvPhone.setText(dt.getPhone()+"");

        holder.iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Update.class);
                intent.putExtra("data",dt);
                context.startActivity(intent);
            }
        });

        holder.iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDelete(dt.getName(),dt.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class DataItemViewholder extends RecyclerView.ViewHolder {
        private TextView tvName,tvClass,tvMail,tvPhone;
        private ImageView iv1,iv2;
        public DataItemViewholder(View itemView) {
            super(itemView);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            tvClass = (TextView)itemView.findViewById(R.id.tvClass);
            tvMail = (TextView)itemView.findViewById(R.id.tvMail);
            tvPhone = (TextView)itemView.findViewById(R.id.tvPhone);
            iv1 = (ImageView)itemView.findViewById(R.id.imageView);
            iv2 = (ImageView)itemView.findViewById(R.id.imageView2);
        }
    }

    private void DialogDelete(String name, final int idStudent){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("Delete "+name);
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.delete(idStudent);

            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
}
