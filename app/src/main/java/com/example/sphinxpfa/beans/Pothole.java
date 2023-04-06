package com.example.sphinxpfa.beans;

public class Pothole {
    private int imageview1;  //tswira lkbira Circle Image
    private int imageview2;  //maps icon
    private String textview1; //name
    private String textview2; //weigth
    private String textview3; //heigth
    private String textview4; //Time Date de creation

    public Pothole(int imageview1, int imageview2, String textview1, String textview2, String textview3, String textview4) {
        this.imageview1 = imageview1;
        this.imageview2 = imageview2;
        this.textview1 = textview1;
        this.textview2 = textview2;
        this.textview3 = textview3;
        this.textview4 = textview4;
    }

    public int getImageview1() {
        return imageview1;
    }

    public int getImageview2() {
        return imageview2;
    }

    public String getTextview1() {
        return textview1;
    }

    public String getTextview2() {
        return textview2;
    }

    public String getTextview3() {
        return textview3;
    }

    public String getTextview4() {
        return textview4;
    }

    public void setImageview1(int imageview1) {
        this.imageview1 = imageview1;
    }

    public void setImageview2(int imageview2) {
        this.imageview2 = imageview2;
    }

    public void setTextview1(String textview1) {
        this.textview1 = textview1;
    }

    public void setTextview2(String textview2) {
        this.textview2 = textview2;
    }

    public void setTextview3(String textview3) {
        this.textview3 = textview3;
    }

    public void setTextview4(String textview4) {
        this.textview4 = textview4;
    }
}

