package com.example.lenovo.mobilehub;

import java.util.ArrayList;

public class Admin extends Person {

    public Admin(){
        super();
    }
    public Admin(String em, String pas, ArrayList<String> favgen){
        super(em,pas,favgen,"Admin");
    }
    public Recommendation GiveRecommendation(Item i){
        Recommendation rec=new Recommendation(i,this);
        return rec;
    }
    public boolean DeleteUser(User p){
        if(p.Type!="Admin"){
            return false;
        }
        else
        {
            return true;
        }
    }
}
