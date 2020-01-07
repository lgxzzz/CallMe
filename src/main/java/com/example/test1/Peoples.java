package com.example.test1;

public class Peoples {
    private String name;
    private int imageId;

    public Peoples(String name, int imageId){
        this.name=name;
        this.imageId=imageId;
    }

    public String getName(){
        return name;
    }

    public int getImageId(){
        return  imageId;
    }
}
