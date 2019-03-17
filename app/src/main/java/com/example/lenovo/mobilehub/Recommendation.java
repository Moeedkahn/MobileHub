package com.example.lenovo.mobilehub;

public class Recommendation {
    public Item item;
    public Person recommendedby;

    public Recommendation(Item it, Person per){
        this.item=it;
        this.recommendedby=per;
    }
}
