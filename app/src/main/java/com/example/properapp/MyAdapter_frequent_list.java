package com.example.properapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mynameismidori.currencypicker.ExtendedCurrency;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class MyAdapter_frequent_list extends BaseAdapter {
    Context context;
    ArrayList<ExpenseIncome> arrayList;

    int pos =-1;
    DatabaseHelper mydb;
    String[] user = new String[2];

    public MyAdapter_frequent_list(Context context, ArrayList<ExpenseIncome> arrayList) {
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

       @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.customview3,null);
        // TextView ID_txt = (TextView)convertView.findViewById(R.id.id_customlistview);
        TextView Account_txt = (TextView)convertView.findViewById(R.id.account_customlistview);
        TextView Amount_txt = (TextView)convertView.findViewById(R.id.amount_customlistview);
        TextView Category_txt = (TextView)convertView.findViewById(R.id.category_customlistview);
        TextView plusminus_txt = (TextView)convertView.findViewById(R.id.plusminus_customlistview);
        ImageView imageView =(ImageView)convertView.findViewById(R.id.imageView_customlistview);
        ImageView imageView_currency =(ImageView)convertView.findViewById(R.id.imageView_currency);
        Button add = (Button)convertView.findViewById(R.id.addbutton_customlistview);
        ImageView del = (ImageView) convertView.findViewById(R.id.deletebutton_customlistview);
        ExpenseIncome expenseIncome = arrayList.get(position);

        Account_txt.setText(expenseIncome.getAccount().toString());
        Amount_txt.setText(expenseIncome.getAmount().toString());
           ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(expenseIncome.currencyname);
           imageView_currency.setImageResource(currencyset.getFlag());

        Category_txt.setText(expenseIncome.getCategory());


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                pos= position;

                    ExpenseIncome expenseIncome = getItem(pos);
                    Date date = Calendar.getInstance().getTime();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
                    String formattedDate = dateFormat.format(date);
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    String formattedTime = timeFormat.format(c.getTime());

                     expenseIncome.date=formattedDate;
                     expenseIncome.time=formattedTime;
                      mydb = new DatabaseHelper(context.getApplicationContext());
                    user =mydb.get_currentuser();
                   boolean inserted = mydb.insertData(expenseIncome.account,expenseIncome.amount,expenseIncome.category,expenseIncome.date,expenseIncome.time,expenseIncome.getNotes(),expenseIncome.incomeexpense,"NORMAL",expenseIncome.currencyname,user[0],user[1],expenseIncome.contactname,expenseIncome.contactphonenumbers,expenseIncome.contactemails);

                   if (inserted) {
                        Toast.makeText(context.getApplicationContext(), "Value is Inserted", Toast.LENGTH_LONG).show();
                       Intent show2 = new Intent(context.getApplicationContext(), ListView.class);
                       context.startActivity(show2);
                    } else {
                        Toast.makeText(context.getApplicationContext(), "Value not Inserted", Toast.LENGTH_LONG).show();
                    }


                }

        });
           del.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {


                   pos= position;

                   ExpenseIncome expenseIncome = getItem(pos);

                   mydb = new DatabaseHelper(context.getApplicationContext());
                   user=mydb.get_currentuser();

                   boolean deleted = mydb.updateData(expenseIncome.ID.toString(),expenseIncome.account,expenseIncome.amount,expenseIncome.category,expenseIncome.date,expenseIncome.time,expenseIncome.getNotes(),expenseIncome.incomeexpense,"NORMAL",expenseIncome.currencyname,user[0],user[1],expenseIncome.contactname,expenseIncome.contactphonenumbers,expenseIncome.contactemails);

                   if (deleted) {
                       Toast.makeText(context.getApplicationContext(), "Value is deleted from frequent list", Toast.LENGTH_LONG).show();
                       Intent show2 = new Intent(context.getApplicationContext(), ListView_Checked.class);
                       context.startActivity(show2);
                   } else {
                       Toast.makeText(context.getApplicationContext(), "Value not deleted", Toast.LENGTH_LONG).show();
                   }


               }

           });




           if(expenseIncome.incomeexpense.equals("expense")) {
            convertView.setBackgroundResource(R.color.lightRed);
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
            convertView.setBackgroundResource(R.color.lightGreen);
            plusminus_txt.setText("+");
        }

        return convertView;



    }




}