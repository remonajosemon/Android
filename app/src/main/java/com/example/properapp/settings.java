package com.example.properapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;
import com.mynameismidori.currencypicker.ExtendedCurrency;

public class settings extends AppCompatActivity {

    TextView default_currency ;
    ImageView default_currency_image;
    Button default_currency_button;
    String currencyname= "Euro";
    DatabaseHelper myDB;
    Button back_button;
    BottomNavigationView bottomNav;
    String[] user = new String[2];
    private long backPressedTime;
    private Toast backToast;
    TextView currency_selected;
    int selected_currency;
    String default_currency_name= "Euro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        default_currency= (TextView)findViewById(R.id.default_currency_textview);
        default_currency_image=(ImageView)findViewById(R.id.default_currency_imageview);
        default_currency_button=(Button)findViewById(R.id.default_currency_button);
        currency_selected=(TextView)findViewById(R.id.currency_textview);
        myDB = new DatabaseHelper(this);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);


        bottom_navigation_helper();
        currency();
        insertDataSettings();


    }//

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            //backToast.show();
            startActivity(new Intent(settings.this, MainActivity.class));
        }
        backPressedTime = System.currentTimeMillis();
    }

    @SuppressLint("ResourceAsColor")
    public void bottom_navigation_helper()
    {
        bottomNav.setItemIconTintList(null);
        MenuItem menuItem = bottomNav.getMenu().findItem(R.id.Nav_Dashboard);
        menuItem.setIcon(R.drawable.ic_dashboard_black_24dp);
        SpannableString s = new SpannableString(menuItem.getTitle());
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        menuItem.setTitle(s);

        MenuItem menuItem2 = bottomNav.getMenu().findItem(R.id.Nav_Dashboard);
        SpannableString s2 = new SpannableString(menuItem2.getTitle());
        s2.setSpan(new ForegroundColorSpan(R.color.darkergreen), 0, s2.length(), 0);
        s2.setSpan(new RelativeSizeSpan(0.90f),0,s2.length(),0);
        menuItem2.setTitle(s2);


    }
    public void insertDataSettings()
    {
        default_currency_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDB = new DatabaseHelper(getApplicationContext());
                user=myDB.get_currentuser();
                boolean inserted = myDB.insertDataSettings(currencyname,user[0],user[1]);
                if (inserted) {
                    Toast.makeText(settings.this, "Default currency value is set", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(settings.this, "Default currency value not set", Toast.LENGTH_LONG).show();
                }
                startActivity(new Intent(getApplicationContext(), com.example.properapp.MainActivity.class));
                finish();
            }

        });
    }
    public void currency()
    {
        user=myDB.get_currentuser();

        String default_currency= "Euro";
        ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(default_currency);
        default_currency_image.setImageResource(currencyset.getFlag());
        currencyname=default_currency;
        currency_selected.setText(currencyname);

        default_currency_name = myDB.getDataSettings(user[0],user[1]);
        if(default_currency_name != null) {
            currencyset = ExtendedCurrency.getCurrencyByName(default_currency_name);
            default_currency_image.setImageResource(currencyset.getFlag());
            currencyname=default_currency_name;
            currency_selected.setText(currencyname);
        }

        default_currency_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");  // dialog title
                picker.setListener(new CurrencyPickerListener() {
                    @Override
                    public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {
                        default_currency_image.setImageResource(flagDrawableResID);
                        //selected_currency = flagDrawableResID;
                        currencyname=name;
                        currency_selected.setText(currencyname);
                        picker.dismiss();

                    }
                });
                picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");
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

                            startActivity(new Intent(settings.this, MainActivity.class));
                            finish();

                            break;
                        case R.id.Nav_List:
                            Intent show1 = new Intent(settings.this, ListView.class);
                            startActivity(show1);
                            finish();
                            break;

                        case R.id.Nav_Add:
                            Intent show2 = new Intent(settings.this, ListView_Checked.class);
                            startActivity(show2);
                            finish();
                            break;
                    }
                    return true;
                }
            };



}
