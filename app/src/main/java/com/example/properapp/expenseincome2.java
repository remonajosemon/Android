package com.example.properapp;

import java.io.Serializable;

public class expenseincome2 implements Serializable
{
    Integer ID;
    String account;
    Double amount;
    String category;
    String date;
    String time;
    String notes;
    String incomeexpense;
    String checked;
    String currencyname;
    String email;
    String profile;
    String contactname;
    String contactphonenumbers;
    String contactemails;



    public expenseincome2()
    {

    }

    public expenseincome2(Integer ID, String account, Double amount, String category, String date, String time, String notes, String incomeexpense, String checked, String currencyname, String email, String profile, String contactname, String contactphonenumbers, String contactemails) {
        this.ID = ID;
        this.account = account;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.time = time;
        this.notes = notes;
        this.incomeexpense = incomeexpense;
        this.checked = checked;
        this.currencyname = currencyname;
        this.email = email;
        this.profile = profile;
        this.contactname = contactname;
        this.contactphonenumbers = contactphonenumbers;
        this.contactemails = contactemails;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getContactphonenumbers() {
        return contactphonenumbers;
    }

    public void setContactphonenumbers(String contactphonenumbers) {
        this.contactphonenumbers = contactphonenumbers;
    }

    public String getContactemails() {
        return contactemails;
    }

    public void setContactemails(String contactemails) {
        this.contactemails = contactemails;
    }
    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getCurrencyname() {
        return currencyname;
    }

    public void setCurrencyname(String currencyname) {
        this.currencyname = currencyname;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }//

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getIncomeexpense() {
        return incomeexpense;
    }

    public void setIncomeexpense(String incomeexpense) {
        this.incomeexpense = incomeexpense;
    }

}
