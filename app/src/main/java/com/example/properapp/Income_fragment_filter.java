package com.example.properapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Income_fragment_filter extends Fragment {
    DatabaseHelper databaseHelper;

    Spinner filter_spin, month_spin,year_spin,numofmonths_spin,category_spin,account_spin,month_spin1,year_spin1,numofmonths_spin1;
    TextView filteroptions_textview,category_textview,numofmonths_textview,fromdate_textview,account_textview,numofmonths_textview1,fromdate_textview1;
    ArrayList<ExpenseIncome> arrayList;
    MyAdapter myAdapter;
    Button show_button;
    Calendar c;
    DatePickerDialog dpd;
    ListView l1;
    FloatingActionButton fab_list;
    BottomNavigationView bottomNav;
    String[] user = new String[2];


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_filter_income,container,false);

        filter_spin = (Spinner) v.findViewById(R.id.filteroptions_spinner);
        month_spin=(Spinner) v.findViewById(R.id.monthsfilter_spinner);
        year_spin=(Spinner) v.findViewById(R.id.yearfilter_spinner);
        numofmonths_spin=(Spinner) v.findViewById(R.id.no_of_months_spinner);
        month_spin1=(Spinner) v.findViewById(R.id.monthsfilter_spinner1);
        year_spin1=(Spinner) v.findViewById(R.id.yearfilter_spinner1);
        numofmonths_spin1=(Spinner) v.findViewById(R.id.no_of_months_spinner1);
        category_spin=(Spinner) v.findViewById(R.id.category_filter_spinner);
        show_button = (Button) v.findViewById(R.id.show_button_filter);
        filteroptions_textview=(TextView) v.findViewById(R.id.filteroptions_textview);
        category_textview=(TextView) v.findViewById(R.id.category_filter_textview);
        fromdate_textview=(TextView) v.findViewById(R.id.fromdate_textview);
        numofmonths_textview=(TextView) v.findViewById(R.id.numberofmonths_textview);
        fromdate_textview1=(TextView) v.findViewById(R.id.fromdate_textview1);
        numofmonths_textview1=(TextView) v.findViewById(R.id.numberofmonths_textview1);
        account_textview=(TextView)v.findViewById(R.id.account_filter_textview);
        account_spin=(Spinner)v.findViewById(R.id.account_filter_spinner);
        l1 = (ListView) v.findViewById(R.id.listview_filter);
        databaseHelper = new DatabaseHelper(getContext());
       user =databaseHelper.get_currentuser();
        arrayList = databaseHelper.getallData_Income_filter(user[0],user[1]);
        myAdapter = new MyAdapter(getContext(), arrayList);
        l1.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        updateData();
        bottomNav = v.findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);
        bottom_navigation_helper();
        fab_list = (FloatingActionButton) v.findViewById(R.id.fab_list);
        fab_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), expense_income_tabbed_activity.class));
                getActivity().finish();
            }
        });
        show_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent show = new Intent(filter.this, show_activity.class);

                if(filter_spin.getSelectedItem().toString().equals("No Filter"))
                {
                   filter_income_all();

                }
                else if (filter_spin.getSelectedItem().toString().equals("Category"))
                {
                    filter_income_category();
                }


                else if (filter_spin.getSelectedItem().toString().equals("Date"))
                {
                    filter_income_date();


                }
                else if (filter_spin.getSelectedItem().toString().equals("Date and Category"))
                {

                    filter_income_date_and_category();
                }
                else if (filter_spin.getSelectedItem().toString().equals("Account"))
                {
                    filter_income_account();

                }
            }
        });
        spinner();

        return v;

    }


    @SuppressLint("ResourceAsColor")
    public void bottom_navigation_helper()
    {
        bottomNav.setItemIconTintList(null);
        MenuItem menuItem = bottomNav.getMenu().findItem(R.id.Nav_List);
        menuItem.setIcon(R.drawable.ic_list_white_24dp);
        SpannableString s = new SpannableString(menuItem.getTitle());
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        s.setSpan(new RelativeSizeSpan(1.15f),0,s.length(),0);
        menuItem.setTitle(s);

        MenuItem menuItem2 = bottomNav.getMenu().findItem(R.id.Nav_Dashboard);
        SpannableString s2 = new SpannableString(menuItem2.getTitle());
        s2.setSpan(new ForegroundColorSpan(R.color.darkergreen), 0, s2.length(), 0);
        s2.setSpan(new RelativeSizeSpan(0.90f),0,s2.length(),0);
        menuItem2.setTitle(s2);


    }

    public void filter_income_all(){
        //int i=1;

        //show.putExtra("serialize_data_nofilter",i);

        //startActivity(show);
        arrayList = databaseHelper.getallData_Income_filter(user[0],user[1]);
        myAdapter = new MyAdapter(getContext(), arrayList);
        l1.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    public void filter_income_account()
    {
        String account = account_spin.getSelectedItem().toString();

        //show.putExtra("serialize_data_category",category);

        //startActivity(show);
        arrayList = databaseHelper.getallData_Account_Income(account,user[0],user[1]);
        myAdapter =new MyAdapter(getContext(),arrayList);
        l1.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

    }

    public void filter_income_category(){
        String category = category_spin.getSelectedItem().toString();

        //show.putExtra("serialize_data_category",category);

        //startActivity(show);
        arrayList = databaseHelper.getallData_Category_Income(category,user[0],user[1]);
        myAdapter =new MyAdapter(getContext(),arrayList);
        l1.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }
    public void filter_income_date(){

        String startDate =makestartdate_1();
        int numofmonths =num_months_1();
        ArrayList<Date> dates =makeDatelist(startDate,numofmonths);
        //show.putExtra("serialize_data_date",dates);
        //startActivity(show);

        arrayList = databaseHelper.getallData_Date_Income(dates,user[0],user[1]);
        myAdapter = new MyAdapter(getContext(), arrayList);
        l1.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }
    public void filter_income_date_and_category(){
        String category = category_spin.getSelectedItem().toString();
        String startdate =makestartdate();
        int numofmonths =num_months();
        ArrayList<Date> date= makeDatelist(startdate,numofmonths);
        Date_and_category dnc = new Date_and_category(category,date);

        //show.putExtra("serialize_data_dateandcategory",dnc);

        //startActivity(show);
        arrayList = databaseHelper.getallData_Date_category_Income(dnc,user[0],user[1]);
        myAdapter = new MyAdapter(getContext(), arrayList);
        l1.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

    }
    public  int num_months(){
        int i=0;

        if(numofmonths_spin.getSelectedItem().toString().equals("One month"))
            i=1;
        if(numofmonths_spin.getSelectedItem().toString().equals("Two month"))
            i=2;
        if(numofmonths_spin.getSelectedItem().toString().equals("Three month"))
            i=3;
        if(numofmonths_spin.getSelectedItem().toString().equals("Six month"))
            i=6;
        if(numofmonths_spin.getSelectedItem().toString().equals("Twelve month"))
            i=12;
        return i;

    }

    public  int num_months_1(){
        int i=0;

        if(numofmonths_spin1.getSelectedItem().toString().equals("One month"))
            i=1;
        if(numofmonths_spin1.getSelectedItem().toString().equals("Two month"))
            i=2;
        if(numofmonths_spin1.getSelectedItem().toString().equals("Three month"))
            i=3;
        if(numofmonths_spin1.getSelectedItem().toString().equals("Six month"))
            i=6;
        if(numofmonths_spin1.getSelectedItem().toString().equals("Twelve month"))
            i=12;
        return i;

    }


    public String makestartdate(){
        String[] s = new String[2];
        String month=null;
        if(month_spin.getSelectedItem().toString().equals("January"))
            month="1";
        if(month_spin.getSelectedItem().toString().equals("February"))
            month="2";
        if(month_spin.getSelectedItem().toString().equals("March"))
            month="3";
        if(month_spin.getSelectedItem().toString().equals("April"))
            month="4";
        if(month_spin.getSelectedItem().toString().equals("May"))
            month="5";
        if(month_spin.getSelectedItem().toString().equals("June"))
            month="6";
        if(month_spin.getSelectedItem().toString().equals("July"))
            month="7";
        if(month_spin.getSelectedItem().toString().equals("August"))
            month="8";
        if(month_spin.getSelectedItem().toString().equals("September"))
            month="9";
        if(month_spin.getSelectedItem().toString().equals("October"))
            month="10";
        if(month_spin.getSelectedItem().toString().equals("November"))
            month="11";
        if(month_spin.getSelectedItem().toString().equals("December"))
            month="12";



        s[0] = month;
        s[1] = year_spin.getSelectedItem().toString();
        String startDate = "1"+"/"+s[0]+"/"+s[1];
        return startDate;

    }

    public String makestartdate_1(){
        String[] s = new String[2];
        String month=null;
        if(month_spin1.getSelectedItem().toString().equals("January"))
            month="1";
        if(month_spin1.getSelectedItem().toString().equals("February"))
            month="2";
        if(month_spin1.getSelectedItem().toString().equals("March"))
            month="3";
        if(month_spin1.getSelectedItem().toString().equals("April"))
            month="4";
        if(month_spin1.getSelectedItem().toString().equals("May"))
            month="5";
        if(month_spin1.getSelectedItem().toString().equals("June"))
            month="6";
        if(month_spin1.getSelectedItem().toString().equals("July"))
            month="7";
        if(month_spin1.getSelectedItem().toString().equals("August"))
            month="8";
        if(month_spin1.getSelectedItem().toString().equals("September"))
            month="9";
        if(month_spin1.getSelectedItem().toString().equals("October"))
            month="10";
        if(month_spin1.getSelectedItem().toString().equals("November"))
            month="11";
        if(month_spin1.getSelectedItem().toString().equals("December"))
            month="12";



        s[0] = month;
        s[1] = year_spin1.getSelectedItem().toString();
        String startDate = "1"+"/"+s[0]+"/"+s[1];
        return startDate;

    }

    public ArrayList<Date> makeDatelist(String startDate, int numofmonths){

        DateFormat formatter;
        Date date = Calendar.getInstance().getTime();;
        formatter = new SimpleDateFormat("d/M/yyyy");
        try {
            date = formatter.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar now = Calendar.getInstance();
        Calendar later = Calendar.getInstance();

        later.setTime(date);
        now.setTime(date);
        later.add(Calendar.MONTH, numofmonths);
        Date future= later.getTime();
        Date present= now.getTime();
        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
        String formattedDate = df.format(present);


        ArrayList<Date> dates = new ArrayList<Date>();
        while(now.before(later))
        {
            dates.add(now.getTime());
            now.add(Calendar.MONTH, 1);
        }
        return dates;

    }
    public void updateData() {
        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent update1 = new Intent(getActivity(), UpdateActivity_Income.class);

                ExpenseIncome expenseIncome = myAdapter.getItem(position);

                if (expenseIncome != null) {

                        update1.putExtra("serialize_data", expenseIncome);
                        startActivity(update1);
                        getActivity().finish();

                }
            }
        });
    }
    public void spinner()

    {
        ArrayAdapter<String> my_spinner_adapter_filter_spin = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Filters));
        my_spinner_adapter_filter_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter_spin.setAdapter(my_spinner_adapter_filter_spin);

        ArrayAdapter<String> my_spinner_adapter_month_spin = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Months));
        my_spinner_adapter_month_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_spin.setAdapter(my_spinner_adapter_month_spin);

        ArrayAdapter<String> my_spinner_adapter_month_spin_1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Months));
        my_spinner_adapter_month_spin_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_spin1.setAdapter(my_spinner_adapter_month_spin_1);

        ArrayAdapter<String> my_spinner_adapter_account_spin = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Account_Income));
        my_spinner_adapter_account_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        account_spin.setAdapter(my_spinner_adapter_account_spin);


        ArrayAdapter<String> my_spinner_adapter_year_spin = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Years));
        my_spinner_adapter_year_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spin.setAdapter(my_spinner_adapter_year_spin);

        ArrayAdapter<String> my_spinner_adapter_year_spin_1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Years));
        my_spinner_adapter_year_spin_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spin1.setAdapter(my_spinner_adapter_year_spin_1);


        ArrayAdapter<String> my_spinner_adapter_numofmonths_spin = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.num_months));
        my_spinner_adapter_numofmonths_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numofmonths_spin.setAdapter(my_spinner_adapter_numofmonths_spin);


        ArrayAdapter<String> my_spinner_adapter_category_spin = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Category_Income));
        my_spinner_adapter_category_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spin.setAdapter(my_spinner_adapter_category_spin);

        ArrayAdapter<String> my_spinner_adapter_numofmonths_spin_1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.num_months));
        my_spinner_adapter_numofmonths_spin_1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numofmonths_spin1.setAdapter(my_spinner_adapter_numofmonths_spin_1);

        filter_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView,View selectedItemView, int position, long id) {
                if ("CATEGORY".equals(filter_spin.getSelectedItem().toString().toUpperCase())) {
                    fromdate_textview.setVisibility(View.INVISIBLE);
                    numofmonths_textview.setVisibility(View.INVISIBLE);
                    month_spin.setVisibility(View.INVISIBLE);
                    year_spin.setVisibility(View.INVISIBLE);
                    numofmonths_spin.setVisibility(View.INVISIBLE);
                    category_spin.setVisibility(View.VISIBLE);
                    category_textview.setVisibility(View.VISIBLE);
                    account_textview.setVisibility(View.INVISIBLE);
                    account_spin.setVisibility(View.INVISIBLE);
                    fromdate_textview1.setVisibility(View.INVISIBLE);
                    numofmonths_textview1.setVisibility(View.INVISIBLE);
                    month_spin1.setVisibility(View.INVISIBLE);
                    year_spin1.setVisibility(View.INVISIBLE);
                    numofmonths_spin1.setVisibility(View.INVISIBLE);
                    fromdate_textview.setVisibility(View.INVISIBLE);

                } else if ("DATE".equals(filter_spin.getSelectedItem().toString().toUpperCase())) {
                    fromdate_textview1.setVisibility(View.VISIBLE);
                    numofmonths_textview1.setVisibility(View.VISIBLE);
                    month_spin1.setVisibility(View.VISIBLE);
                    year_spin1.setVisibility(View.VISIBLE);
                    numofmonths_spin1.setVisibility(View.VISIBLE);
                    fromdate_textview.setVisibility(View.INVISIBLE);
                    numofmonths_textview.setVisibility(View.INVISIBLE);
                    month_spin.setVisibility(View.INVISIBLE);
                    year_spin.setVisibility(View.INVISIBLE);
                    numofmonths_spin.setVisibility(View.INVISIBLE);
                    category_textview.setVisibility(View.INVISIBLE);
                    category_spin.setVisibility(View.INVISIBLE);
                    account_textview.setVisibility(View.INVISIBLE);
                    account_spin.setVisibility(View.INVISIBLE);
                }

                else if ("ACCOUNT".equals(filter_spin.getSelectedItem().toString().toUpperCase())) {
                    fromdate_textview.setVisibility(View.INVISIBLE);
                    numofmonths_textview.setVisibility(View.INVISIBLE);
                    month_spin.setVisibility(View.INVISIBLE);
                    year_spin.setVisibility(View.INVISIBLE);
                    numofmonths_spin.setVisibility(View.INVISIBLE);
                    category_textview.setVisibility(View.INVISIBLE);
                    category_spin.setVisibility(View.INVISIBLE);
                    account_textview.setVisibility(View.VISIBLE);
                    account_spin.setVisibility(View.VISIBLE);
                    fromdate_textview1.setVisibility(View.INVISIBLE);
                    numofmonths_textview1.setVisibility(View.INVISIBLE);
                    month_spin1.setVisibility(View.INVISIBLE);
                    year_spin1.setVisibility(View.INVISIBLE);
                    numofmonths_spin1.setVisibility(View.INVISIBLE);
                    fromdate_textview.setVisibility(View.INVISIBLE);
                }

                else if ("DATE AND CATEGORY".equals(filter_spin.getSelectedItem().toString().toUpperCase())) {

                    fromdate_textview.setVisibility(View.VISIBLE);
                    numofmonths_textview.setVisibility(View.VISIBLE);
                    month_spin.setVisibility(View.VISIBLE);
                    year_spin.setVisibility(View.VISIBLE);
                    numofmonths_spin.setVisibility(View.VISIBLE);
                    category_textview.setVisibility(View.VISIBLE);
                    category_spin.setVisibility(View.VISIBLE);
                    account_textview.setVisibility(View.INVISIBLE);
                    account_spin.setVisibility(View.INVISIBLE);
                    fromdate_textview1.setVisibility(View.INVISIBLE);
                    numofmonths_textview1.setVisibility(View.INVISIBLE);
                    month_spin1.setVisibility(View.INVISIBLE);
                    year_spin1.setVisibility(View.INVISIBLE);
                    numofmonths_spin1.setVisibility(View.INVISIBLE);
                    fromdate_textview.setVisibility(View.INVISIBLE);
                }
                else if ("NO FILTER".equals(filter_spin.getSelectedItem().toString().toUpperCase())) {
                    fromdate_textview.setVisibility(View.INVISIBLE);
                    numofmonths_textview.setVisibility(View.INVISIBLE);
                    month_spin.setVisibility(View.INVISIBLE);
                    year_spin.setVisibility(View.INVISIBLE);
                    numofmonths_spin.setVisibility(View.INVISIBLE);
                    category_textview.setVisibility(View.INVISIBLE);
                    category_spin.setVisibility(View.INVISIBLE);
                    account_textview.setVisibility(View.INVISIBLE);
                    account_spin.setVisibility(View.INVISIBLE);
                    fromdate_textview1.setVisibility(View.INVISIBLE);
                    numofmonths_textview1.setVisibility(View.INVISIBLE);
                    month_spin1.setVisibility(View.INVISIBLE);
                    year_spin1.setVisibility(View.INVISIBLE);
                    numofmonths_spin1.setVisibility(View.INVISIBLE);
                    fromdate_textview.setVisibility(View.INVISIBLE);

                }
                else
                {
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.Nav_Dashboard:
                            startActivity(new Intent(getContext(),MainActivity.class));
                            getActivity().finish();
                            break;

                        case R.id.Nav_List:
                            Intent show1 = new Intent(getContext(), com.example.properapp.ListView.class);
                            startActivity(show1);
                            getActivity().finish();
                            break;

                        case R.id.Nav_Add:
                            Intent show2 = new Intent(getContext(), ListView_Checked.class);
                            startActivity(show2);
                            getActivity().finish();
                            break;
                    }
                    return true;
                }
            };
}
