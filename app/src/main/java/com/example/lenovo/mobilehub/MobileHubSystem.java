package com.example.lenovo.mobilehub;

import java.util.ArrayList;

public class MobileHubSystem {

    public ArrayList<User>AllUsers;
    public ArrayList<Admin>AllAdmins;
    public ArrayList<Item>AllItems;

    public MobileHubSystem(){
        this.AllUsers=null;
        this.AllItems=null;
        this.AllAdmins=null;
    }
    public MobileHubSystem(ArrayList<User>alluser, ArrayList<Item>allitem, ArrayList<Admin>alladmin){
        this.AllUsers=alluser;
        this.AllItems=allitem;
        this.AllAdmins=alladmin;
    }
    public void AddUser(User obj){
        this.AllUsers.add(obj);
    }
    public void AddItem(Item obj){
        this.AllItems.add(obj);
    }
    public void AddAdmin(Admin obj){
        this.AllAdmins.add(obj);
    }
}
