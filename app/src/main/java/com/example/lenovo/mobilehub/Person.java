package com.example.lenovo.mobilehub;

import java.util.ArrayList;

public abstract class Person {
    public String Email;
    public String Password;
    public ArrayList<String>FavGenre;
    public String Type;
    public ArrayList<Recommendation>allrecommendations;
    public Person(){
        this.Email=null;
        this.Password=null;
        this.FavGenre=new ArrayList<>();
        this.Type=null;
        this.allrecommendations=new ArrayList<>();
    }
    public Person(String em,String pas,ArrayList<String>favgen,String ty){
        this.Email=em;
        this.Password=pas;
        this.FavGenre=favgen;
        this.Type=ty;
        this.allrecommendations=null;
    }
    public abstract Recommendation GiveRecommendation(Item i);
}
