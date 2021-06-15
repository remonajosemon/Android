package com.example.properapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class budget_planning extends AppCompatActivity {

   EditText shopping,car,education,entertainment,grocery,foodndrink,health,travel,other;
   Switch shopping_switch,car_switch,education_switch,entertainment_switch,grocery_switch,foodndrink_switch,health_switch,travel_switch,other_switch;
   FloatingActionButton add_budget_button;
   Boolean shopping_state,car_state,education_state,entertainment_state,grocery_state,foodndrink_state,health_state,travel_state,other_state;
   DatabaseHelper mydb;
   String[] user = new String[2];

    BottomNavigationView bottomNav;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_planning);

        shopping= findViewById(R.id.shopping_edittext);
        car=findViewById(R.id.car_edittext);
        education =findViewById(R.id.education_edittext);
        entertainment= findViewById(R.id.entertainment_edittext);
        foodndrink= findViewById(R.id.Food_n_drink_edittext);
        grocery= findViewById(R.id.grocery_edittext);
        health =findViewById(R.id.health_edittext);
        travel = findViewById(R.id.travel_edittext);
        other= findViewById(R.id.other_edittext);

        shopping.setText("-1");
        car.setText("-1");
        education.setText("-1");
        entertainment.setText("-1");
        foodndrink.setText("-1");
        grocery.setText("-1");
        health.setText("-1");
        travel.setText("-1");
        other.setText("-1");

        shopping.setVisibility(View.INVISIBLE);
        car.setVisibility(View.INVISIBLE);
        education.setVisibility(View.INVISIBLE);
        entertainment.setVisibility(View.INVISIBLE);
        foodndrink.setVisibility(View.INVISIBLE);
        grocery.setVisibility(View.INVISIBLE);
        health.setVisibility(View.INVISIBLE);
        travel.setVisibility(View.INVISIBLE);
        other.setVisibility(View.INVISIBLE);


        shopping_switch= findViewById(R.id.switch_shopping);
        car_switch=findViewById(R.id.switch_car);
        education_switch =findViewById(R.id.switch_education);
        entertainment_switch= findViewById(R.id.switch_entertainment);
        foodndrink_switch= findViewById(R.id.switch_food_n_drink);
        grocery_switch= findViewById(R.id.switch_grocery);
        health_switch=findViewById(R.id.switch_health);
        travel_switch= findViewById(R.id.switch_travel);
        other_switch= findViewById(R.id.switch_other);

        shopping_switch.setChecked(false);
        car_switch.setChecked(false);
        education_switch.setChecked(false);
        entertainment_switch.setChecked(false);
        foodndrink_switch.setChecked(false);
        grocery_switch.setChecked(false);
        health_switch.setChecked(false);
        travel_switch.setChecked(false);
        other_switch.setChecked(false);

        add_budget_button= findViewById(R.id.add_budget_button);
        mydb = new DatabaseHelper(this);
        user =mydb.get_currentuser();

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);

        Double[] budget = mydb.getDataBudget(user[0],user[1]);
        if(budget != null)
        {
            shopping.setText(budget[0].toString());
            if(budget[0] !=-1)
            {
                shopping.setVisibility(View.VISIBLE);
                shopping_switch.setChecked(true);

            }
            car.setText(budget[1].toString());
            if(budget[1]!=-1)
            {
                car.setVisibility(View.VISIBLE);
                car_switch.setChecked(true);

            }
            education.setText(budget[2].toString());
            if(budget[2]!=-1)
            {
                education.setVisibility(View.VISIBLE);
                education_switch.setChecked(true);

            }
            entertainment.setText(budget[3].toString());
            if(budget[3]!=-1)
            {
                entertainment.setVisibility(View.VISIBLE);
                entertainment_switch.setChecked(true);

            }
            foodndrink.setText(budget[4].toString());
            if(budget[4]!=-1)
            {
                foodndrink.setVisibility(View.VISIBLE);
                foodndrink_switch.setChecked(true);

            }
            grocery.setText(budget[5].toString());
            if(budget[5]!=-1)
            {
                grocery.setVisibility(View.VISIBLE);
                grocery_switch.setChecked(true);

            }
            health.setText(budget[6].toString());
            if(budget[6]!=-1)
            {
                health.setVisibility(View.VISIBLE);
                health_switch.setChecked(true);

            }
            travel.setText(budget[7].toString());
            if(budget[7]!=-1)
            {
               travel.setVisibility(View.VISIBLE);
                travel_switch.setChecked(true);

            }
            other.setText(budget[8].toString());
            if(budget[8]!=-1)
            {
                other.setVisibility(View.VISIBLE);
                other_switch.setChecked(true);

            }
        }


        shopping_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopping_state = shopping_switch.isChecked();
                if(shopping_state)
                {
                    shopping.setText("0");
                    shopping.setVisibility(View.VISIBLE);
                }
                if(!shopping_state)
                {
                    shopping.setText("-1");
                    shopping.setVisibility(View.INVISIBLE);
                }
            }
        });



        car_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                car_state = car_switch.isChecked();
                if(car_state)
                {
                    car.setText("0");
                    car.setVisibility(View.VISIBLE);
                }
                if(!car_state)
                {
                    car.setText("-1");
                    car.setVisibility(View.INVISIBLE);
                }
            }
        });



        education_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                education_state = education_switch.isChecked();
                if(education_state )
                {
                    education.setText("0");
                    education.setVisibility(View.VISIBLE);
                }
                if(!education_state)
                {
                    education.setText("-1");
                    education.setVisibility(View.INVISIBLE);
                }
            }
        });


        entertainment_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entertainment_state = entertainment_switch.isChecked();
                if(entertainment_state)
                {
                    entertainment.setText("0");
                    entertainment.setVisibility(View.VISIBLE);
                }
                if(!entertainment_state)
                {
                    entertainment.setText("-1");
                    entertainment.setVisibility(View.INVISIBLE);
                }
            }
        });


        foodndrink_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodndrink_state = foodndrink_switch.isChecked();
                if(foodndrink_state)
                {
                    foodndrink.setText("0");
                    foodndrink.setVisibility(View.VISIBLE);
                }
                if(!foodndrink_state)
                {
                    foodndrink.setText("-1");
                    foodndrink.setVisibility(View.INVISIBLE);
                }
            }
        });


        grocery_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grocery_state = grocery_switch.isChecked();
                if(grocery_state)
                {
                    grocery.setText("0");
                    grocery.setVisibility(View.VISIBLE);
                }
                if(!grocery_state)
                {
                    grocery.setText("-1");
                    grocery.setVisibility(View.INVISIBLE);
                }
            }
        });


        health_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                health_state = health_switch.isChecked();
                if(health_state)
                {
                    health.setText("0");
                    health.setVisibility(View.VISIBLE);
                }
                if(!health_state)
                {
                    health.setText("-1");
                    health.setVisibility(View.INVISIBLE);
                }
            }
        });


        travel_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                travel_state = travel_switch.isChecked();
                if(travel_state)
                {
                    travel.setText("0");
                    travel.setVisibility(View.VISIBLE);
                }
                if(!travel_state )
                {
                    travel.setText("-1");
                    travel.setVisibility(View.INVISIBLE);
                }
            }
        });


        other_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                other_state = other_switch.isChecked();
                if(other_state)
                {
                    other.setText("0");
                    other.setVisibility(View.VISIBLE);
                }
                if(!other_state)
                {
                    other.setText("-1");
                    other.setVisibility(View.INVISIBLE);
                }
            }
        });





        add_budget_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               boolean inserted= mydb.insertDataBudget(Double.parseDouble(shopping.getText().toString()),
                        Double.parseDouble(car.getText().toString()),
                        Double.parseDouble(education.getText().toString()),
                        Double.parseDouble(entertainment.getText().toString()),
                        Double.parseDouble(foodndrink.getText().toString()),
                        Double.parseDouble(grocery.getText().toString()),
                        Double.parseDouble(health.getText().toString()),
                        Double.parseDouble(travel.getText().toString()),
                        Double.parseDouble(other.getText().toString()),user[0],user[1]);

                if (inserted) {
                    Toast.makeText(getApplicationContext(), "Value is Inserted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Value not Inserted", Toast.LENGTH_LONG).show();
                }

                startActivity(new Intent(getApplicationContext(), com.example.properapp.MainActivity.class));
                finish();
            }
        });

        bottom_navigation_helper();


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

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.Nav_Dashboard:

                            startActivity(new Intent(budget_planning.this,MainActivity.class));
                            finish();

                            break;
                        case R.id.Nav_List:
                            Intent show1 = new Intent(budget_planning.this, ListView.class);
                            startActivity(show1);
                            finish();

                            break;

                        case R.id.Nav_Add:
                            Intent show2 = new Intent(budget_planning.this, ListView_Checked.class);
                            startActivity(show2);
                            finish();
                            break;
                    }
                    return true;
                }
            };


    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            //backToast.show();
            startActivity(new Intent(budget_planning.this,MainActivity.class));
        }
        backPressedTime = System.currentTimeMillis();
    }


}
