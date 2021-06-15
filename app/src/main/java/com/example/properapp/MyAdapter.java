package com.example.properapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mynameismidori.currencypicker.ExtendedCurrency;

import java.util.ArrayList;
import java.util.Collections;

public class MyAdapter extends BaseAdapter {

    Context context;
    ArrayList<ExpenseIncome> arrayList;

    public MyAdapter(Context context, ArrayList<ExpenseIncome> arrayList) {
        this.context = context;
        Collections.reverse(arrayList);
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public ExpenseIncome getItem(int position) {
        return this.arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.cumtomview2,null);
       // TextView ID_txt = (TextView)convertView.findViewById(R.id.id_customlistview);
        TextView Account_txt = (TextView)convertView.findViewById(R.id.account_customlistview);
        TextView Amount_txt = (TextView)convertView.findViewById(R.id.amount_customlistview);
        TextView Date_txt = (TextView)convertView.findViewById(R.id.date_customlistview);
        TextView Category_txt = (TextView)convertView.findViewById(R.id.category_customlistview);
        TextView plusminus_txt = (TextView)convertView.findViewById(R.id.plusminus_customlistview);
        ImageView imageView =(ImageView)convertView.findViewById(R.id.imageView_customlistview);
        ImageView imageView_currency =(ImageView)convertView.findViewById(R.id.imageView_currency);
        ExpenseIncome expenseIncome = arrayList.get(position);
      //  ID_txt.setText(String.valueOf(expense.getID()));
        Account_txt.setText(expenseIncome.getAccount().toString());
        Amount_txt.setText(expenseIncome.getAmount().toString());
        Date_txt.setText(expenseIncome.getDate());
        Category_txt.setText(expenseIncome.getCategory());
        ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(expenseIncome.currencyname);
        imageView_currency.setImageResource(currencyset.getFlag());
        if(expenseIncome.incomeexpense.equals("expense")) {
            //convertView(R.color.lightRed);
            plusminus_txt.setText("-");
            if (expenseIncome.category.equals("Shopping"))
            {
                imageView.setImageResource(R.drawable.gift_bag);
            }
            else if (expenseIncome.category.equals("Car"))
            {
                imageView.setImageResource(R.drawable.pickup_car);
            }
            else if (expenseIncome.category.equals("Entertainment"))
            {
                imageView.setImageResource(R.drawable.entertainment_60x60);
            }
            else if (expenseIncome.category.equals("Education"))
            {
                imageView.setImageResource(R.drawable.education_60x60);
            }
            else if (expenseIncome.category.equals("Food n Drink"))
            {
                imageView.setImageResource(R.drawable.food_60x60);
            }
            else if (expenseIncome.category.equals("Grocery"))
            {
                imageView.setImageResource(R.drawable.grocery_60x60);
            }
            else if (expenseIncome.category.equals("Health"))
            {
                imageView.setImageResource(R.drawable.insurance);
            }
            else if (expenseIncome.category.equals("Travel"))
            {
                imageView.setImageResource(R.drawable.travel_60x60);
            }
            else
            {
                imageView.setImageResource(R.drawable.other);
            }
        }
        else if (expenseIncome.incomeexpense.equals("income"))
        {
            plusminus_txt.setText("+");
        }

        return convertView;



    }
}
