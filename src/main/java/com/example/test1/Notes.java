package com.example.test1;

public class Notes {
    private String name;
    private String sentences;

    public  Notes(String name,String sentences){
        this.name=name;
        this.sentences=sentences;
    }

    public String getName(){
        return name;
    }

    public String getSentences(){
        return  sentences;
    }
}

