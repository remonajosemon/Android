package com.example.properapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Date_and_category implements Serializable {

    public String Category;
    public ArrayList<Date> date;

    public Date_and_category() {
    }

    public Date_and_category(String category, ArrayList<Date> date) {
        Category = category;
        this.date = date;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public ArrayList<Date> getDate() {
        return date;
    }

    public void setDate(ArrayList<Date> date) {
        this.date = date;
    }
}
