package com.example.properapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Help_menu extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;

    TextView Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_menu);

        Q1 = (TextView) findViewById(R.id.help_menu1);
        Q2 = (TextView) findViewById(R.id.help_menu2);
        Q3 = (TextView) findViewById(R.id.help_menu3);
        Q4 = (TextView) findViewById(R.id.help_menu4);
        Q5 = (TextView) findViewById(R.id.help_menu5);
        Q6 = (TextView) findViewById(R.id.help_menu6);
        Q7 = (TextView) findViewById(R.id.help_menu7);
        Q8 = (TextView) findViewById(R.id.help_menu8);
        Q9 = (TextView) findViewById(R.id.help_menu9);
        Q10 = (TextView) findViewById(R.id.help_menu10);

        Q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Help_menu.this, help_menu_1.class));
                finish();
            }
        });

        Q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Help_menu.this, help_menu_2.class));
                finish();
            }
        });
        Q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Help_menu.this, help_menu_3.class));
                finish();
            }
        });
        Q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Help_menu.this, help_menu_4.class));
                finish();
            }
        });
        Q5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Help_menu.this, help_menu_5.class));
                finish();
            }
        });
        Q6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Help_menu.this, help_menu_6.class));
                finish();
            }
        });
        Q7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Help_menu.this, help_menu_7.class));
                finish();
            }
        });
        Q8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Help_menu.this, help_menu_8.class));
                finish();
            }
        });
        Q9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Help_menu.this, help_menu_9.class));
                finish();
            }
        });
        Q10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Help_menu.this, help_menu_10.class));
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
            //backToast.show();
            startActivity(new Intent(Help_menu.this,MainActivity.class));

        }
        backPressedTime = System.currentTimeMillis();
    }

}
