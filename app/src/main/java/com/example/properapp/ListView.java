package com.example.properapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mynameismidori.currencypicker.ExtendedCurrency;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListView extends AppCompatActivity {

    TextView expenseDetails;
    Dialog detailDialog;
    //abhijith >>>Button backHome;
    //abhijith >>>Button addExpense;
    MyAdapter myAdapter;
    private long backPressedTime;
    private Toast backToast;
    SwipeMenuListView listView;
    //android.widget.ListView listView_income;
    DatabaseHelper myDB;
    ArrayList<ExpenseIncome> arrayList;
    FloatingActionButton fab_list;
    BottomNavigationView bottomNav;

    ImageView filter_button;
    String[] user = new String[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        expenseDetails = (TextView)findViewById(R.id.expenseDetails);
        detailDialog = new Dialog(this);
        //abhijith >>>> backHome = (Button)findViewById(R.id.backHome);
        //abhijith >>>> addExpense = (Button)findViewById(R.id.addExpense);
        listView = (SwipeMenuListView) findViewById(R.id.listview3);
        fab_list = (FloatingActionButton) findViewById(R.id.fab_list);

        myDB = new DatabaseHelper(this);
        user =myDB.get_currentuser();
        filter_button = (ImageView) findViewById(R.id.filter_button);
        loadData();
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                editItem.setBackground(new ColorDrawable(Color.rgb(0x33, 0xFF,
                        0x3E)));
                // set item width
                editItem.setWidth(170);
                // set item title
                editItem.setTitle("Edit");
                // set item title fontsize
                editItem.setTitleSize(14);
                // set item title font color
                editItem.setTitleColor(Color.WHITE);
                // add to menu
               // editItem.setMenuHeight
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFB,
                        0x00, 0x00)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.delete_image);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);
        detailData_popUp();
        //swipeMenu();


        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);
        bottom_navigation_helper();

        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListView.this, filter_tabbed_activity.class));
                finish();
            }
        });

        fab_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListView.this, expense_income_tabbed_activity.class));
                finish();
            }
        });
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        updateDataSwipe(position);
                        break;
                    case 1:
                        delete(position);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
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
            //backToast.show();
            startActivity(new Intent(ListView.this,MainActivity.class));

        }
        backPressedTime = System.currentTimeMillis();
    }
    @SuppressLint("ResourceAsColor")
    public void bottom_navigation_helper()
    {
        bottomNav.setItemIconTintList(null);
        MenuItem menuItem = bottomNav.getMenu().findItem(R.id.Nav_List);
        menuItem.setIcon(R.drawable.ic_list_white_24dp);
        SpannableString s = new SpannableString(menuItem.getTitle());
        s.setSpan(new RelativeSizeSpan(1.15f),0,s.length(),0);
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        menuItem.setTitle(s);

        MenuItem menuItem2 = bottomNav.getMenu().findItem(R.id.Nav_Dashboard);
        SpannableString s2 = new SpannableString(menuItem2.getTitle());
        s2.setSpan(new ForegroundColorSpan(R.color.darkergreen), 0, s2.length(), 0);
        s2.setSpan(new RelativeSizeSpan(0.90f),0,s2.length(),0);
        menuItem2.setTitle(s2);


    }
    public void swipeMenu()
    {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                editItem.setBackground(new ColorDrawable(Color.rgb(0x33, 0xFF,
                        0x3E)));
                // set item width
                editItem.setWidth(170);
                // set item title
                editItem.setTitle("Edit");
                // set item title fontsize
                editItem.setTitleSize(18);
                // set item title font color
                editItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xFB,
                        0x00, 0x00)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.delete_image);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);
    }
    public void loadData()
    {
        arrayList = myDB.getallData(user[0],user[1]);
        myAdapter = new MyAdapter(this,arrayList);
        listView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();

    }

    public void delete(int position) {


                ExpenseIncome expenseIncome = myAdapter.getItem(position);
                boolean deleted = myDB.deleteDataExpense(expenseIncome.getID().toString());
                if (deleted) {
                    Toast.makeText(getApplicationContext(), "Value deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Value not deleted", Toast.LENGTH_LONG).show();
                }
                Intent update = new Intent(ListView.this, ListView.class);
                startActivity(update);
                finish();
            }


    public void detailData() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent update_1 = new Intent(ListView.this, ExpenseDetails.class);
                Intent update_2 = new Intent(ListView.this, ExpenseDetails.class);


                ExpenseIncome expenseIncome = myAdapter.getItem(position);


                    if (expenseIncome != null) {

                      if(expenseIncome.incomeexpense.equals("expense")) {
                            update_1.putExtra("serialize_data", expenseIncome);
                            startActivity(update_1);
                            finish();
                        }
                        else {update_2.putExtra("serialize_data", expenseIncome);
                        startActivity(update_2);
                            finish();}
                    }


            }
        });
    }
    public void detailData_popUp() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView txtclose;
                TextView category;
                TextView account;
                TextView amount;
                TextView date;
                TextView time;
                TextView notes;
                TextView contactname;
                TextView contactno;
                TextView contactemail;
                ImageView currencysymbol;
                detailDialog.setContentView(R.layout.activity_listview_highlight);
                txtclose = (TextView)detailDialog.findViewById(R.id.text_close);
                category = (TextView)detailDialog.findViewById(R.id.category);
                account = (TextView)detailDialog.findViewById(R.id.account);
                amount = (TextView)detailDialog.findViewById(R.id.amount);
                currencysymbol = (ImageView)detailDialog.findViewById(R.id.currencysymbol);
                date = (TextView)detailDialog.findViewById(R.id.date);
                time = (TextView)detailDialog.findViewById(R.id.time);
                notes = (TextView)detailDialog.findViewById(R.id.notes);
                contactname = (TextView)detailDialog.findViewById(R.id.contactname);
                contactno = (TextView)detailDialog.findViewById(R.id.contactno);
                contactemail = (TextView)detailDialog.findViewById(R.id.contactemail);

                ExpenseIncome expenseIncome = myAdapter.getItem(position);
                category.setText(expenseIncome.getCategory().toString());
                account.setText(expenseIncome.account);
                amount.setText((expenseIncome.amount).toString());
                ExtendedCurrency currency = ExtendedCurrency.getCurrencyByName(expenseIncome.currencyname);
                currencysymbol.setImageResource(currency.getFlag());
                date.setText(expenseIncome.date);
                time.setText(expenseIncome.time);
                CharSequence notes_text = Html.fromHtml(expenseIncome.notes,1);
                notes.setText(notes_text);
                contactname.setText(expenseIncome.getContactname().toString());
                contactno.setText(expenseIncome.getContactphonenumbers());
                contactemail.setText(expenseIncome.getContactemails());
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        detailDialog.dismiss();
                    }
                });
                detailDialog.show();





            }
        });
    }
    public void updateDataSwipe(int position) {
       // listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                Intent update_1 = new Intent(ListView.this, UpdateActivity_expense.class);
                Intent update_2 = new Intent(ListView.this, UpdateActivity_Income.class);


                ExpenseIncome expenseIncome = myAdapter.getItem(position);


                if (expenseIncome != null) {

                if(expenseIncome.incomeexpense.equals("expense")) {
                        update_1.putExtra("serialize_data", expenseIncome);
                        startActivity(update_1);
                        finish();
                    }
                    else {update_2.putExtra("serialize_data", expenseIncome);
                        startActivity(update_2);
                        finish();}
                }


            }




    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    // Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.Nav_Dashboard:

                            startActivity(new Intent(ListView.this,MainActivity.class));
                            finish();

                            break;
                        case R.id.Nav_List:
                            Intent show1 = new Intent(ListView.this, ListView.class);
                            startActivity(show1);
                            finish();
                            break;

                        case R.id.Nav_Add:
                            Intent show2 = new Intent(ListView.this, ListView_Checked.class);
                            startActivity(show2);
                            finish();
                            break;
                    }
                    return true;
                }
            };
}
