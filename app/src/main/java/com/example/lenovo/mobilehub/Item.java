package com.example.lenovo.mobilehub;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Item implements Serializable {
    int id;
    String name;
    String genre;
    String imageLink;

    public Item() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public String getGenre() {
        return this.genre;
    }

    public String getImageLink() {
        return this.imageLink;
    }

    private Item(Parcel in) {
        this.name=in.readString();
        this.genre = in.readString();
        this.imageLink = in.readString();
        this.id=in.readInt();
    }
    /*@Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(genre);
        dest.writeString(imageLink);

    }
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };*/
}
