package com.example.properapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListView_Checked extends AppCompatActivity {

    TextView expenseDetails;
    //abhijith >>>Button backHome;
    //abhijith >>>Button addExpense;
    private long backPressedTime;
    private Toast backToast;
    MyAdapter_frequent_list myAdapter;
    android.widget.ListView listView;
    android.widget.ListView listView_income;
    DatabaseHelper myDB;
    ArrayList<ExpenseIncome> arrayList;
    BottomNavigationView bottomNav;
    ImageView filter_button;
    String[] user = new String[2];
    FloatingActionButton fablist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_fav);
        expenseDetails = (TextView)findViewById(R.id.expenseDetails);
        //abhijith >>>> backHome = (Button)findViewById(R.id.backHome);
        //abhijith >>>> addExpense = (Button)findViewById(R.id.addExpense);
        listView = (android.widget.ListView)findViewById(R.id.listview3);
        fablist = findViewById(R.id.fab_list);

        myDB = new DatabaseHelper(this);
        user =myDB.get_currentuser();
       // filter_button = (ImageView) findViewById(R.id.filter_button);
        loadData();
        updateData();

       bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);
        bottom_navigation_helper();

        fablist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListView_Checked.this, expense_income_tabbed_activity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
           // backToast.show();
            startActivity(new Intent(ListView_Checked.this,MainActivity.class));

        }
        backPressedTime = System.currentTimeMillis();
    }

    @SuppressLint("ResourceAsColor")
    public void bottom_navigation_helper()
    {
        bottomNav.setItemIconTintList(null);
        MenuItem menuItem = bottomNav.getMenu().findItem(R.id.Nav_Add);
        menuItem.setIcon(R.drawable.ic_menu_white_24dp);
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
    public void loadData()
    {
        arrayList = myDB.getallData_checked(user[0],user[1]);
        myAdapter = new MyAdapter_frequent_list(this,arrayList);
        listView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

    }

    public void updateData() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent update_1 = new Intent(ListView_Checked.this, insert_expense_checked.class);
                Intent update_2 = new Intent(ListView_Checked.this, insert_income_checked.class);

                ExpenseIncome expenseIncome = myAdapter.getItem(position);


                if (expenseIncome != null) {
                    if(expenseIncome.incomeexpense.equals("expense")) {
                        update_1.putExtra("serialize_data", expenseIncome);
                        startActivity(update_1);
                        finish();
                    }
                    else
                        {
                            update_2.putExtra("serialize_data", expenseIncome);
                        startActivity(update_2);
                        finish();
                    }
                }


            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.Nav_Dashboard:

                            startActivity(new Intent(ListView_Checked.this,MainActivity.class));
                            finish();

                            break;
                        case R.id.Nav_List:
                            Intent show1 = new Intent(ListView_Checked.this, ListView.class);
                            startActivity(show1);
                            finish();
                            break;

                        case R.id.Nav_Add:
                            Intent show2 = new Intent(ListView_Checked.this, ListView_Checked.class);
                            startActivity(show2);
                            finish();
                            break;
                    }
                    return true;
                }
            };
}
