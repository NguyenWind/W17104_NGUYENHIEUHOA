package com.example.nguyen_wind7.myapplication;

import java.io.Serializable;

/**
 * Created by nguyen_wind7 on 2018/08/03.
 */

public class Data implements Serializable{
    private String Name,Cl,Mail,Phone;
    private int Id;

    public Data(String name, String cl, String mail, String phone, int id) {
        Name = name;
        Cl = cl;
        Mail = mail;
        Phone = phone;
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCl() {
        return Cl;
    }

    public void setCl(String cl) {
        Cl = cl;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
