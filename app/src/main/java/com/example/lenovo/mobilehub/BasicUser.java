package com.example.lenovo.mobilehub;

import java.util.ArrayList;

public class BasicUser extends User {

    public BasicUser(){
        super();
    }
    public BasicUser(String em, String pas, ArrayList<String> favgen){

        super(em,pas,favgen,"Basic");

    }
    public Recommendation GiveRecommendation(Item i){
        return null;
    }
    public Item UploadVideo(Item i){
        return null;
    }
}
