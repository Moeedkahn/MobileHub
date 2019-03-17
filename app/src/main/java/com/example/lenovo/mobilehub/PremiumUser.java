package com.example.lenovo.mobilehub;

import java.util.ArrayList;

public class PremiumUser extends User {

    public PremiumUser(){
        super();
    }
    public PremiumUser(String em, String pas, ArrayList<String> favgen){
        super(em,pas,favgen,"Premium");
    }
    public Recommendation GiveRecommendation(Item i){
        Recommendation rec=new Recommendation(i,this);
        return rec;
    }

}
