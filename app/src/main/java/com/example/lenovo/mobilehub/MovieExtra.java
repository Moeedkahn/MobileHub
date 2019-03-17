package com.example.lenovo.mobilehub;

public class MovieExtra {
    int i;
    public int Up;
    public int Down;
    String na;
    String gen;
    String imageLink;
    String main;

    public MovieExtra() {
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setUp(int up) {
        Up = up;
    }

    public void setDown(int down) {
        Down = down;
    }

    public void setNa(String na) {
        this.na = na;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public int getI() {
        return i;
    }

    public int getUp() {
        return Up;
    }

    public int getDown() {
        return Down;
    }

    public String getNa() {
        return na;
    }

    public String getGen() {
        return gen;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getMain() {
        return main;
    }
}
