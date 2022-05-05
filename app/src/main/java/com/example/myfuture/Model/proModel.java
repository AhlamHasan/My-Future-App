package com.example.myfuture.Model;

public class proModel extends proID {

    private String title, describe, year;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        System.out.println(title);
        return title;
    }

    public String getDescribe() {
        System.out.println(describe);
        return describe;
    }


    public String getYear() {
        System.out.println(year);
        return year;
    }
}
