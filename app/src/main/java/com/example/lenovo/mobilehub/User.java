package com.example.lenovo.mobilehub;

import java.util.ArrayList;

public abstract class User extends Person {

    public User(){
        super();
    }
    public User(String em, String pas, ArrayList<String> favgen, String ty){
        super(em,pas,favgen,ty);
    }

}
