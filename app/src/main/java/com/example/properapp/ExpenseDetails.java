package com.example.properapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mynameismidori.currencypicker.ExtendedCurrency;

public class ExpenseDetails extends AppCompatActivity {

    TextView category_name;
    TextView account_name;
    TextView amount_value;
    ImageView currency_value;
    TextView date_value;
    TextView time_value;
    TextView notes_value;
    ExpenseIncome olditem;
    Button back_button;
    private long backPressedTime;
    private Toast backToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_details);
        category_name = findViewById(R.id.category_name_textview);
        account_name = findViewById(R.id.account_textviewdetails);
        amount_value = findViewById(R.id.amountlent_textView);
        currency_value = findViewById(R.id.currencysymbol_imageView);
        date_value = findViewById(R.id.date_textviewdetails);
        time_value = findViewById(R.id.time_textView_details);
        notes_value = findViewById(R.id.notes_Textview_details);
        back_button=findViewById(R.id.back_button);
        Bundle extras = getIntent().getExtras();
        if(extras !=null)

        {
            olditem = (ExpenseIncome) getIntent().getSerializableExtra("serialize_data"); //Obtaining data

        }
        assert olditem !=null;
        category_name.setText(olditem.category);
        account_name.setText(olditem.account);
        amount_value.setText((olditem.amount).toString());
        ExtendedCurrency currency = ExtendedCurrency.getCurrencyByName(olditem.currencyname);
        currency_value.setImageResource(currency.getFlag());
        date_value.setText(olditem.date);
        time_value.setText(olditem.time);
        CharSequence notes_text = Html.fromHtml(olditem.notes,1);
        notes_value.setText(notes_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExpenseDetails.this,ListView.class));
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
            startActivity(new Intent(ExpenseDetails.this,ListView.class));
        }
        backPressedTime = System.currentTimeMillis();
    }
}
