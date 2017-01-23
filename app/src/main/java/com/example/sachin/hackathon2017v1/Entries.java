package com.example.sachin.hackathon2017v1;

/**
 * Created by Sachin on 2017-01-21.
 */

public class Entries {
    private int amount;
    private String type;
    private String date;

    public Entries(int amount, String type, String date)    {
        this.amount = amount;
        this.type = type;
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
