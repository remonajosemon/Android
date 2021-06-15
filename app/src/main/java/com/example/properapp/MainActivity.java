package com.example.properapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mynameismidori.currencypicker.ExtendedCurrency;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    PieChart pieChart;
    PieChart pieChart_monthly;
    TextView graph_not;
    TextView graph_not_income;
    RelativeLayout rl_car;
    RelativeLayout rl_entertainment;
    RelativeLayout rl_education;
    RelativeLayout rl_food;
    RelativeLayout rl_grocery;
    RelativeLayout rl_health;
    RelativeLayout rl_shopping;
    RelativeLayout rl_travel;
    RelativeLayout rl_other;
    RelativeLayout rl_salary;
    RelativeLayout rl_allowance;
    RelativeLayout rl_bonus;
    RelativeLayout rl_borrow;
    TextView borrow_in;
    TextView bonus_in;
    TextView allowance_in;
    TextView salary_in;
    TextView other_ex;
    TextView travel_ex;
    TextView shopping_ex;
    TextView health_ex;
    TextView grocery_ex;
    TextView food_ex;
    TextView education_ex;
    TextView enterntainment_ex;
    TextView car_ex;
    TextView car_ex_textview;
    TextView entertainment_ex_textview;
    TextView education_ex_textview;
    TextView food_ex_textview;
    TextView grocery_ex_textview;
    TextView health_ex_textview;
    TextView shopping_ex_textview;
    TextView travel_ex_textview;
    TextView other_ex_textview;
    TextView salary_in_textview;
    TextView allowance_in_textview;
    TextView bonus_in_textview;
    TextView borrow_in_textview;





    PieChart pieChart_yearly;
    BottomSheetDialog bottomSheetDialog_type;
    BottomSheetDialog bottomSheetDialog_year;
    BottomSheetDialog bottomSheetDialog_month;
    SharedPreferences sharedPreferences;
    RadioGroup radioGroup;
    RadioGroup radioGroup_month;
    RadioGroup radioGroup_year;
    RadioButton radioButton;
    RadioButton total_radiobtn;
    RadioButton yearly_radiobtn;
    RadioButton monthly_radiobtn;
    public static final int PICK_CONTACT = 0;
    private static final String TAG = "MainActivity";
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    File file;
    ArrayList<String> pathHistory;
    String lastDirectory;
    int count = 0;
    ArrayList<expenseincome2> uploadData;
    Dialog getMyDialog;
    float shopping;
    float car;
    float entertainment;
    float education;
    float food;
    float grocery;
    float health;
    float travel;
    float other;
    float shopping_monthly;
    float car_monthly;
    float entertainment_monthly;
    float education_monthly;
    float food_monthly;
    float grocery_monthly;
    float health_monthly;
    float travel_monthly;
    float other_monthly;
    float shopping_yearly;
    float car_yearly;
    float entertainment_yearly;
    float education_yearly;
    float food_yearly;
    float grocery_yearly;
    float health_yearly;
    float travel_yearly;
    float other_yearly;
    float borrow;
    float salary;
    float allowance;
    float bonus;
    int month;
    int year;
    Double rateValue=0.00;
    private long backPressedTime;
    private Toast backToast;
    ArrayList<ExpenseIncome> shoppingList_monthly;
    ArrayList<ExpenseIncome> carList_monthly;
    ArrayList<ExpenseIncome> entertainmentList_monthly;
    ArrayList<ExpenseIncome> educationList_monthly;
    ArrayList<ExpenseIncome> foodList_monthly;
    ArrayList<ExpenseIncome> groceryList_monthly;
    ArrayList<ExpenseIncome> healthList_monthly;
    ArrayList<ExpenseIncome> travelList_monthly;
    ArrayList<ExpenseIncome> otherList_monthly;
    ArrayList<ExpenseIncome> shoppingList_yearly;
    ArrayList<ExpenseIncome> carList_yearly;
    ArrayList<ExpenseIncome> entertainmentList_yearly;
    ArrayList<ExpenseIncome> educationList_yearly;
    ArrayList<ExpenseIncome> foodList_yearly;
    ArrayList<ExpenseIncome> groceryList_yearly;
    ArrayList<ExpenseIncome> healthList_yearly;
    ArrayList<ExpenseIncome> travelList_yearly;
    ArrayList<ExpenseIncome> otherList_yearly;
    ArrayList<ExpenseIncome> shoppingList;
    ArrayList<ExpenseIncome> carList;
    ArrayList<ExpenseIncome> entertainmentList;
    ArrayList<ExpenseIncome> educationList;
    ArrayList<ExpenseIncome> foodList;
    ArrayList<ExpenseIncome> groceryList;
    ArrayList<ExpenseIncome> healthList;
    ArrayList<ExpenseIncome> travelList;
    ArrayList<ExpenseIncome> otherList;
    ArrayList<ExpenseIncome> salaryList;
    ArrayList<ExpenseIncome> allowanceList;
    ArrayList<ExpenseIncome> bonusList;
    ArrayList<ExpenseIncome> borrowList;
    //Button logout, export;
    DatabaseHelper myDB;
    FloatingActionButton floatingActionButton;
    TextView total_amount;
    TextView total_expense;
    TextView total_income;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    ImageView graph_image;
    NavigationView navigationView;
    BottomNavigationView bottomNav;
    Double resultVal3 = 0.00;
    Double moneyValue3 = 0.00;
    String to3;
    Double resultVal4 = 0.00;
    Double moneyValue4 = 0.00;
    Double moneyValue_category = 0.00;
    Double moneyValue_car_category = 0.00;
    Double moneyValue_entertainment_category = 0.00;
    Double moneyValue_education_category = 0.00;
    Double moneyValue_food_category = 0.00;
    Double moneyValue_grocery_category = 0.00;
    Double moneyValue_health_category = 0.00;
    Double moneyValue_travel_category = 0.00;
    Double moneyValue_other_category = 0.00;
    Double resultVal_category = 0.00;
    Double resultVal_car_category = 0.00;
    Double resultVal_entertainment_category = 0.00;
    Double resultVal_education_category = 0.00;
    Double resultVal_food_category = 0.00;
    Double resultVal_grocery_category = 0.00;
    Double resultVal_health_category = 0.00;
    Double resultVal_travel_category = 0.00;
    Double resultVal_other_category = 0.00;
    String to4;
    String to5;
    String to6;
    String to7;
    String to8;
    String to9;
    String to10;
    String to11;
    String to12;
    String to13;
    String to14;
    Double total_income_sum = 0.00;
    Double total_expense_sum = 0.00;
    Double total_money_euro_expense = 0.00;
    Double total_money_euro_category = 0.00;
    Double total_money_euro_car_category = 0.00;
    Double total_money_euro_entertainment_category = 0.00;
    Double total_money_euro_education_category = 0.00;
    Double total_money_euro_food_category = 0.00;
    Double total_money_euro_grocery_category = 0.00;
    Double total_money_euro_health_category = 0.00;
    Double total_money_euro_travel_category = 0.00;
    Double total_money_euro_other_category = 0.00;
    Double total_money_default_category = 0.00;
    Double total_money_default_car_category = 0.00;
    Double total_money_default_entertainment_category = 0.00;
    Double total_money_default_education_category = 0.00;
    Double total_money_default_food_category = 0.00;
    Double total_money_default_grocery_category = 0.00;
    Double total_money_default_health_category = 0.00;
    Double total_money_default_travel_category = 0.00;
    Double total_money_default_other_category = 0.00;
    Double total_money_euro_income = 0.00;
    Double total_money_default_expense = 0.00;
    Double total_money_default_income = 0.00;
    String currency_code = null;
    Double money_expense = 0.00;
    Double money_income = 0.00;
    String[] user = new String[2];
    Dialog myDialog;
    Spinner type_spinner,monthly_spinner,yearly_spinner;
   TextView avgexpense,avgincome;
   int[] monthcount=new int[13];
   ImageView type_button;
   TextView yearly_txt;
   TextView monthly_txt;
   View bottomSheetDialog_type_view;
   View bottomSheetDialog_year_view;
    View bottomSheetDialog_month_view;
    Calendar calendar;
    int currentmonth;
    TextView categories_in;
    TextView categories;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"ResourceAsColor", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMyDialog = new Dialog(this);
        bottomSheetDialog_type= new BottomSheetDialog(MainActivity.this);
        bottomSheetDialog_year = new BottomSheetDialog(MainActivity.this);
        bottomSheetDialog_month = new BottomSheetDialog(MainActivity.this);
        type_button = (ImageView)findViewById(R.id.type_button);
        yearly_txt = (TextView)findViewById(R.id.yearly_txt);
        monthly_txt = (TextView)findViewById(R.id.monthly_txt);
        rl_allowance = (RelativeLayout)findViewById(R.id.rl_allowance);
        rl_car = (RelativeLayout)findViewById(R.id.rl_car);
        rl_food = (RelativeLayout)findViewById(R.id.rl_food);
        rl_grocery = (RelativeLayout)findViewById(R.id.rl_grocery);
        rl_health = (RelativeLayout)findViewById(R.id.rl_health);
        rl_shopping = (RelativeLayout)findViewById(R.id.rl_shopping);
        rl_education = (RelativeLayout)findViewById(R.id.rl_edu);
        rl_entertainment =(RelativeLayout)findViewById(R.id.rl_ent);
        rl_travel = (RelativeLayout)findViewById(R.id.rl_travel);
        rl_other = (RelativeLayout)findViewById(R.id.rl_other);
        rl_salary = (RelativeLayout)findViewById(R.id.rl_salary);
        rl_bonus = (RelativeLayout)findViewById(R.id.rl_bonus);
        rl_borrow = (RelativeLayout)findViewById(R.id.rl_borrow);
        borrow_in = (TextView)findViewById(R.id.in_borrow_food);
        bonus_in = (TextView)findViewById(R.id.in_bonus_rate);
        allowance_in = (TextView)findViewById(R.id.in_allowance_rate);
        salary_in = (TextView)findViewById(R.id.in_salary_rate);
        other_ex = (TextView)findViewById(R.id.ex_other_rate);
        travel_ex = (TextView)findViewById(R.id.ex_travel_rate);
        shopping_ex = (TextView)findViewById(R.id.ex_shopping_rate);
        health_ex = (TextView)findViewById(R.id.ex_health_rate);
        grocery_ex = (TextView)findViewById(R.id.ex_grocery_rate);
        enterntainment_ex = (TextView)findViewById(R.id.ex_ent_rate);
        education_ex = (TextView)findViewById(R.id.ex_edu_rate);
        food_ex = (TextView)findViewById(R.id.ex_ent_food);
        grocery_ex = (TextView)findViewById(R.id.ex_grocery_rate);
        health_ex = (TextView)findViewById(R.id.ex_health_rate);
        shopping_ex = (TextView)findViewById(R.id.ex_shopping_rate);
        travel_ex = (TextView)findViewById(R.id.ex_travel_rate);
        other_ex = (TextView)findViewById(R.id.ex_other_rate);
        car_ex = (TextView)findViewById(R.id.ex_car_rate);
        categories = (TextView)findViewById(R.id.categories);
        categories_in = (TextView)findViewById(R.id.categories_in);




        graph_not = (TextView)findViewById(R.id.graph_not);
        graph_not_income= (TextView)findViewById(R.id.graph_not_income);
        bottomSheetDialog_type_view = getLayoutInflater().inflate(R.layout.category_dialog_layout,null);
       bottomSheetDialog_type.setContentView(bottomSheetDialog_type_view);
        bottomSheetDialog_year_view = getLayoutInflater().inflate(R.layout.yearly_dialog_layout,null);
        bottomSheetDialog_year.setContentView(bottomSheetDialog_year_view);
        bottomSheetDialog_month_view = getLayoutInflater().inflate(R.layout.monthly_dialog_layout,null);
        bottomSheetDialog_month.setContentView(bottomSheetDialog_month_view);
        sharedPreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        floatingActionButton = findViewById(R.id.fav);
        pieChart = (PieChart) findViewById(R.id.graph);
        //logout = findViewById(R.id.btn_logout);
        //export = findViewById(R.id.btn_export);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.getLegend().setEnabled(false);
        // graph_image = findViewById(R.id.graph_image);
        pieChart_monthly = (PieChart) findViewById(R.id.graph_income);
        //logout = findViewById(R.id.btn_logout);
        //export = findViewById(R.id.btn_export);
       pieChart_monthly.setUsePercentValues(true);
        pieChart_monthly.getDescription().setEnabled(false);
        pieChart_monthly.setExtraOffsets(5, 10, 5, 5);
        pieChart_monthly.setDragDecelerationFrictionCoef(0.95f);
        pieChart_monthly.setDrawHoleEnabled(true);
        pieChart_monthly.setHoleColor(Color.WHITE);
        pieChart_monthly.setTransparentCircleRadius(61f);
        pieChart_monthly.getLegend().setEnabled(false);
        rl_allowance.setVisibility(View.GONE);



       // pieChart_yearly = (PieChart) findViewById(R.id.graph_yearly);
        //logout = findViewById(R.id.btn_logout);
        //export = findViewById(R.id.btn_export);
      /*  pieChart_yearly.setUsePercentValues(true);
        pieChart_yearly.getDescription().setEnabled(false);
        pieChart_yearly.setExtraOffsets(5, 10, 5, 5);
        pieChart_yearly.setDragDecelerationFrictionCoef(0.95f);
        pieChart_yearly.setDrawHoleEnabled(true);
        pieChart_yearly.setHoleColor(Color.WHITE);
        pieChart_yearly.setTransparentCircleRadius(61f);
        pieChart_yearly.getLegend().setEnabled(false);

       */
        total_amount = findViewById(R.id.textMainTotalAmount);
        total_expense = findViewById(R.id.textMainExpenseAmount);
        total_income = findViewById(R.id.textMainIncomeAmount);
        avgexpense= findViewById(R.id.avgExpense);
        avgincome=findViewById(R.id.avgIncome);
        myDB = new DatabaseHelper(this);//
        user = myDB.get_currentuser();
        myDialog = new Dialog(this);
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);
        type_spinner =findViewById(R.id.overview_type_spinner);
        monthly_spinner= findViewById(R.id.overview_month_spinner);
        yearly_spinner = findViewById(R.id.overview_year_spinner);
        radioGroup = bottomSheetDialog_type_view.findViewById(R.id.radio_group);
        radioGroup_month = bottomSheetDialog_month_view.findViewById(R.id.radio_month_group);
        radioGroup_year = bottomSheetDialog_year_view.findViewById(R.id.radio_year_group);


        total_radiobtn = bottomSheetDialog_type_view.findViewById(R.id.total_radiobtn);
        yearly_radiobtn = bottomSheetDialog_type_view.findViewById(R.id.yearly_radiobtn);
        monthly_radiobtn= bottomSheetDialog_type_view.findViewById(R.id.monthly_radiobtn);

        type_spinner.setVisibility(View.INVISIBLE);
        yearly_spinner.setVisibility(View.INVISIBLE);
        monthly_spinner.setVisibility(View.INVISIBLE);


        monthly_txt.setVisibility(View.VISIBLE);
        yearly_txt.setVisibility(View.VISIBLE);
        avgexpense.setVisibility(View.VISIBLE);
        avgincome.setVisibility(View.VISIBLE);

        int a= myDB.getTotal_Month(user[0],user[1]);
        int b= myDB.getTotal_Year(user[0],user[1]);
       avgexpense.setText(String.valueOf(a));
       avgincome.setText(String.valueOf(b));

        graph_not.setVisibility(View.INVISIBLE);
        graph_not_income.setVisibility(View.INVISIBLE);

        bottom_navigation_helper();
        setNavigationViewListener();
        calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        currentmonth =month+1;
        //monthly_txt.setText("June");
        //yearly_txt.setText("2020");
        monthly_txt.setText(getMonth(currentmonth));
        yearly_txt.setText(String.valueOf(year));
        pieChart.setVisibility(View.VISIBLE);
        pieChart_monthly.setVisibility(View.VISIBLE);
       // pieChart_yearly.setVisibility(View.INVISIBLE);
        pie_chart_monthly();
        pie_chart_Income_monthly();
        filter_overview_monthly_down();

         type_button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 bottomSheetDialog_type.show();
             }
         });


        total_radiobtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(MainActivity.this, "Total", Toast.LENGTH_SHORT).show();

                 monthly_txt.setVisibility(View.INVISIBLE);
                 yearly_txt.setVisibility(View.INVISIBLE);
               //  yearly_txt.setText("Year");
              //   monthly_txt.setText("Month");
               //  pieChart.setVisibility(View.VISIBLE);
              //   pieChart_monthly.setVisibility(View.VISIBLE);
              //   pieChart_yearly.setVisibility(View.INVISIBLE);

                 filter_overview_total();
                 pie_chart();
                 pie_chart_Income();
                 bottomSheetDialog_type.dismiss();

             }
         });

         yearly_radiobtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(MainActivity.this, "Yearly", Toast.LENGTH_SHORT).show();
                 yearly_txt.setVisibility(View.VISIBLE);
                 monthly_txt.setVisibility(View.INVISIBLE);
                 avgexpense.setVisibility(View.VISIBLE);
                 avgincome.setVisibility(View.VISIBLE);
                 yearly_txt.setText(String.valueOf(year));
                 filter_overview_yearly_down();
                 //pieChart.setVisibility(View.VISIBLE);
                // pieChart_monthly.setVisibility(View.VISIBLE);
                 pie_chart_yearly();
                 pie_chart_Income_yearly();
                 bottomSheetDialog_type.dismiss();
                 yearly_txt.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         bottomSheetDialog_year.show();

                     }
                 });
             }
         });
         monthly_radiobtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Toast.makeText(MainActivity.this, "Selected: "+monthly_radiobtn.getText(), Toast.LENGTH_SHORT).show();
                 monthly_txt.setVisibility(View.VISIBLE);
                 yearly_txt.setVisibility(View.VISIBLE);
                 monthly_txt.setText(getMonth(currentmonth));
                // pieChart.setVisibility(View.VISIBLE);
               //  pieChart_monthly.setVisibility(View.VISIBLE);
                 yearly_txt.setText(String.valueOf(year));
                 avgincome.setVisibility(View.VISIBLE);
                 avgincome.setVisibility(View.VISIBLE);
                 filter_overview_monthly_down();
                 pie_chart_monthly();
                 pie_chart_Income_monthly();


                 bottomSheetDialog_type.dismiss();
                 yearly_txt.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         bottomSheetDialog_year.show();

                     }
                 });
                 monthly_txt.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         bottomSheetDialog_month.show();

                     }
                 });

             }
         });



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent show2 = new Intent(MainActivity.this, expense_income_tabbed_activity.class);
                startActivity(show2);
                finish();
            }
        });
        //Income_amount();
        //Expense_amount();
        //Balance_amount();

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.draw_layout);
        navigationView = findViewById(R.id.navigationView);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

       // spinner();



        }
public void checkMonthPressed(View view)
{
    int radioId = radioGroup_month.getCheckedRadioButtonId();
     radioButton = bottomSheetDialog_month_view.findViewById(radioId);
    Toast.makeText(this, "Selected "+radioButton.getText(), Toast.LENGTH_SHORT).show();
    monthly_txt.setText(radioButton.getText().toString());
    pieChart.setVisibility(View.VISIBLE);
    pieChart_monthly.setVisibility(View.VISIBLE);
   // pieChart_yearly.setVisibility(View.INVISIBLE);
    filter_overview_monthly_down();
    pie_chart_monthly();
    pie_chart_Income_monthly();
    bottomSheetDialog_month.dismiss();
}
    public void checkYearPressed(View view)
    {
        int radioId = radioGroup_year.getCheckedRadioButtonId();
        radioButton = bottomSheetDialog_year_view.findViewById(radioId);
        Toast.makeText(this, "Selected "+radioButton.getText(), Toast.LENGTH_SHORT).show();
        yearly_txt.setText(radioButton.getText().toString());
        filter_overview_yearly_down();
        pieChart.setVisibility(View.VISIBLE);
        pieChart_monthly.setVisibility(View.VISIBLE);
       // pieChart_yearly.setVisibility(View.VISIBLE);
        pie_chart_yearly();
        pie_chart_Income_yearly();
        bottomSheetDialog_year.dismiss();
    }

    public String getMonth(int month)
    {
        String month_now = "";

        if(month==1)
            month_now="January";
        if(month==2)
           month_now="February" ;
        if(month==3)
            month_now="March";
        if(month==4)
            month_now="April";
        if(month==5)
            month_now="May";
        if(month==6)
            month_now="June" ;
        if(month==7)
            month_now="July";
        if(month==8)
            month_now="August";
        if(month==9)
            month_now="September";
        if(month==10)
            month_now="October" ;
        if(month==11)
            month_now="November";
        if(month==12)
            month_now="December";

        return month_now;

    }









    public  void filter_overview_total()
    {
        total_income_sum = 0.00;
        total_expense_sum = 0.00;
        Income_amount();
        Expense_amount();
        Balance_amount();

    }
    public  void filter_overview_yearly_down()
    {
        total_income_sum = 0.00;
        total_expense_sum = 0.00;
        avgexpense.setText("0.00");
        avgincome.setText("0.00");
        Income_amount_yearly_down();
        Expense_amount_yearly_down();
        Balance_amount();



    }
   /* public  void filter_overview_yearly()
    { total_income_sum = 0.00;
        total_expense_sum = 0.00;
        Income_amount_yearly();
        Expense_amount_yearly();
        Balance_amount();




       yearly_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                total_income_sum = 0.00;
                total_expense_sum = 0.00;
                avgexpense.setText("0.00");
                avgincome.setText("0.00");
                Income_amount_yearly();
                Expense_amount_yearly();
                Balance_amount();

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    */
    public  void filter_overview_monthly_down()
    {
        total_income_sum = 0.00;
        total_expense_sum = 0.00;
        if(monthly_txt.getText().toString().equals("Month")||yearly_txt.getText().toString().equals("Year"))
        {

        }
        else
            {
                avgexpense.setVisibility(View.VISIBLE);
                avgincome.setVisibility(View.VISIBLE);

            Income_amount_monthly_down();
            Expense_amount_monthly_down();
            Balance_amount();
        }




    }
    /*public  void filter_overview_monthly()
    {
        total_income_sum = 0.00;
        total_expense_sum = 0.00;
        Income_amount_monthly();
        Expense_amount_monthly();
        Balance_amount();

        monthly_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                total_income_sum = 0.00;
                total_expense_sum = 0.00;
                Income_amount_monthly();
                Expense_amount_monthly();
                Balance_amount();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        yearly_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                total_income_sum = 0.00;
                total_expense_sum = 0.00;
                Income_amount_monthly();
                Expense_amount_monthly();
                Balance_amount();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


     */


   /* public String makestartdateyearly(){

        String s = yearly_spinner.getSelectedItem().toString();
        String startDate = "1"+"/"+"1"+"/"+s;
        return startDate;

    }

    */
    public String make_startdate_yearly(){

        String s = yearly_txt.getText().toString();
        String startDate = "1"+"/"+"1"+"/"+s;
        return startDate;

    }
    public String make_start_date(){
        String[] s = new String[2];
        String month=null;
        if(monthly_txt.getText().toString().equals("January"))
            month="1";
        if(monthly_txt.getText().toString().equals("February"))
            month="2";
        if(monthly_txt.getText().toString().equals("March"))
            month="3";
        if(monthly_txt.getText().toString().equals("April"))
            month="4";
        if(monthly_txt.getText().toString().equals("May"))
            month="5";
        if(monthly_txt.getText().toString().equals("June"))
            month="6";
        if(monthly_txt.getText().toString().equals("July"))
            month="7";
        if(monthly_txt.getText().toString().equals("August"))
            month="8";
        if(monthly_txt.getText().toString().equals("September"))
            month="9";
        if(monthly_txt.getText().toString().equals("October"))
            month="10";
        if(monthly_txt.getText().toString().equals("November"))
            month="11";
        if(monthly_txt.getText().toString().equals("December"))
            month="12";



        s[0] = month;
        s[1] = yearly_txt.getText().toString();
        String startDate = "1"+"/"+s[0]+"/"+s[1];
        return startDate;

    }
   /* public String makestartdate(){
        String[] s = new String[2];
        String month=null;
        if(monthly_spinner.getSelectedItem().toString().equals("January"))
            month="1";
        if(monthly_spinner.getSelectedItem().toString().equals("February"))
            month="2";
        if(monthly_spinner.getSelectedItem().toString().equals("March"))
            month="3";
        if(monthly_spinner.getSelectedItem().toString().equals("April"))
            month="4";
        if(monthly_spinner.getSelectedItem().toString().equals("May"))
            month="5";
        if(monthly_spinner.getSelectedItem().toString().equals("June"))
            month="6";
        if(monthly_spinner.getSelectedItem().toString().equals("July"))
            month="7";
        if(monthly_spinner.getSelectedItem().toString().equals("August"))
            month="8";
        if(monthly_spinner.getSelectedItem().toString().equals("September"))
            month="9";
        if(monthly_spinner.getSelectedItem().toString().equals("October"))
            month="10";
        if(monthly_spinner.getSelectedItem().toString().equals("November"))
            month="11";
        if(monthly_spinner.getSelectedItem().toString().equals("December"))//
            month="12";



        s[0] = month;
        s[1] = yearly_spinner.getSelectedItem().toString();
        String startDate = "1"+"/"+s[0]+"/"+s[1];
        return startDate;

    }

    */


 /*   public void spinner()

    {
        ArrayAdapter<String> my_spinner_adapter_filter_spin = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Overview));
        my_spinner_adapter_filter_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(my_spinner_adapter_filter_spin);

        ArrayAdapter<String> my_spinner_adapter_year_spin = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Years));
        my_spinner_adapter_year_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearly_spinner.setAdapter(my_spinner_adapter_year_spin);

        ArrayAdapter<String> my_spinner_adapter_month_spin = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Months));
        my_spinner_adapter_month_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthly_spinner.setAdapter(my_spinner_adapter_month_spin);


        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView,View selectedItemView, int position, long id) {
                if ("TOTAL".equals(type_spinner.getSelectedItem().toString().toUpperCase())) {

                   monthly_spinner.setVisibility(View.INVISIBLE);
                   yearly_spinner.setVisibility(View.INVISIBLE);
                   avgexpense.setVisibility(View.INVISIBLE);
                   avgincome.setVisibility(View.INVISIBLE);
                    filter_overview_total();


                } else if ("YEARLY".equals(type_spinner.getSelectedItem().toString().toUpperCase())) {

                    monthly_spinner.setVisibility(View.INVISIBLE);
                    yearly_spinner.setVisibility(View.VISIBLE);
                    filter_overview_yearly();

                }

                else if ("MONTHLY".equals(type_spinner.getSelectedItem().toString().toUpperCase())) {

                    monthly_spinner.setVisibility(View.VISIBLE);
                    yearly_spinner.setVisibility(View.VISIBLE);
                    avgexpense.setVisibility(View.INVISIBLE);
                    avgincome.setVisibility(View.INVISIBLE);
                    filter_overview_monthly();

                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
    }

  */


    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @SuppressLint("ResourceAsColor")
    public void bottom_navigation_helper() {
        bottomNav.setItemIconTintList(null);
        MenuItem menuItem = bottomNav.getMenu().findItem(R.id.Nav_Dashboard);
        menuItem.setIcon(R.drawable.ic_dashboard_white_24dp);
        SpannableString s = new SpannableString(menuItem.getTitle());
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), 0);
        menuItem.setTitle(s);

    }



    @SuppressLint("SetTextI18n")
    public void Income_amount() {

        ArrayList<ExpenseIncome> arrayList = myDB.getallData_Income(user[0],user[1]);
        ExpenseIncome obj;
        String default_currency = myDB.getDataSettings(user[0],user[1]);
        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj = arrayList.get(i);
                String obj_currencyname = obj.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj_currencyname);
                String obj_currencycode = currencyset.getCode();

                to3 = obj_currencycode;
                moneyValue3 = obj.getAmount();
                rateValue = myDB.getcurrency_rate_date(to3,obj.getDate());
                resultVal3 = moneyValue3 / rateValue;

                total_money_default_income = resultVal3;
                if (default_currency != null) {
                    ExtendedCurrency currencyset1 = ExtendedCurrency.getCurrencyByName(default_currency);
                    currency_code = currencyset1.getCode();
                    money_income = resultVal3;
                    Double rateValue1 = myDB.getcurrency_rate_date(currency_code,obj.getDate());
                    total_money_default_income = money_income * rateValue1;
                }
                total_income_sum=total_income_sum+total_money_default_income;
                i = i + 1;
            }
            int totalnumberofmonths=0;
            totalnumberofmonths=myDB.getTotal_Month(user[0],user[1]);
            if(totalnumberofmonths!=0){
                Float avg =0f;
                avg= Float.parseFloat(String.valueOf(total_income_sum/totalnumberofmonths));
                // avgincome.setVisibility(View.VISIBLE);
                avgincome.setText(avg.toString());

            }

            Float income_float = 0f;
            income_float = Float.parseFloat(String.valueOf((total_income_sum)));
            total_income.setText(income_float.toString());


        } else {
            Float income_float = 0f;
            income_float = Float.parseFloat(String.valueOf((total_income_sum)));
            total_income.setText("0.00");
        }

    }
    @SuppressLint("SetTextI18n")
    public void Income_amount_monthly_down() {
        avgincome.setVisibility(View.VISIBLE);
        avgexpense.setVisibility(View.VISIBLE);
        String startdate= make_start_date();

        ArrayList<ExpenseIncome> arrayList = myDB.get_OverviewIncome_monthly(startdate,user[0],user[1]);
        ExpenseIncome obj;
        String default_currency = myDB.getDataSettings(user[0],user[1]);
        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj = arrayList.get(i);
                String obj_currencyname = obj.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj_currencyname);
                String obj_currencycode = currencyset.getCode();

                to3 = obj_currencycode;
                moneyValue3 = obj.getAmount();
                rateValue = myDB.getcurrency_rate_date(to3,obj.getDate());
                resultVal3 = moneyValue3 / rateValue;

                total_money_default_income = resultVal3;
                if (default_currency != null) {
                    ExtendedCurrency currencyset1 = ExtendedCurrency.getCurrencyByName(default_currency);
                    currency_code = currencyset1.getCode();
                    money_income = resultVal3;
                    Double rateValue1 = myDB.getcurrency_rate_date(currency_code,obj.getDate());
                    total_money_default_income = money_income * rateValue1;
                }
                total_income_sum=total_income_sum+total_money_default_income;
                i = i + 1;
            }
           Double total = average_monthly_income();
            int totalnumberofmonths=0;
            totalnumberofmonths=myDB.getTotal_Month(user[0],user[1]);
            if(totalnumberofmonths!=0){
                Float avg =0f;
                avg= Float.parseFloat(String.valueOf(total/totalnumberofmonths));
                // avgincome.setVisibility(View.VISIBLE);
                avgincome.setText(avg.toString());

            }
            Float income_float = 0f;
            income_float = Float.parseFloat(String.valueOf((total_income_sum)));
            total_income.setText(income_float.toString());


        } else {
            Float income_float = 0f;
            income_float = Float.parseFloat(String.valueOf((total_income_sum)));
            total_income.setText("0.00");
        }

    }

  /*  @SuppressLint("SetTextI18n")
    public void Income_amount_monthly() {
        String startdate= makestartdate();

        ArrayList<ExpenseIncome> arrayList = myDB.get_OverviewIncome_monthly(startdate,user[0],user[1]);
        ExpenseIncome obj;
        String default_currency = myDB.getDataSettings(user[0],user[1]);
        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj = arrayList.get(i);
                String obj_currencyname = obj.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj_currencyname);
                String obj_currencycode = currencyset.getCode();

                to3 = obj_currencycode;
                moneyValue3 = obj.getAmount();
                 rateValue = myDB.getcurrency_rate_date(to3,obj.getDate());
                resultVal3 = moneyValue3 / rateValue;

                total_money_default_income = resultVal3;
                if (default_currency != null) {
                    ExtendedCurrency currencyset1 = ExtendedCurrency.getCurrencyByName(default_currency);
                    currency_code = currencyset1.getCode();
                    money_income = resultVal3;
                    Double rateValue1 = myDB.getcurrency_rate_date(currency_code,obj.getDate());
                    total_money_default_income = money_income * rateValue1;
                }
                total_income_sum=total_income_sum+total_money_default_income;
                i = i + 1;
            }

            Float income_float = 0f;
            income_float = Float.parseFloat(String.valueOf((total_income_sum)));
            total_income.setText(income_float.toString());


        } else {
            Float income_float = 0f;
            income_float = Float.parseFloat(String.valueOf((total_income_sum)));
            total_income.setText("0.00");
        }

    }


    @SuppressLint("SetTextI18n")
    public void Income_amount_yearly() {
        String startdate= makestartdateyearly();

        ArrayList<ExpenseIncome> arrayList = myDB.get_OverviewIncome_yearly(startdate,user[0],user[1]);
        ExpenseIncome obj;
        String default_currency = myDB.getDataSettings(user[0],user[1]);
        monthcount[0]=0;  monthcount[1]=0;monthcount[2]=0;monthcount[3]=0;monthcount[4]=0;monthcount[5]=0;monthcount[6]=0;
        monthcount[7]=0;monthcount[8]=0;monthcount[9]=0;monthcount[10]=0;monthcount[11]=0;monthcount[12]=0;

        int l;
        int i = 0;
        l = arrayList.size();
        int month =1;
        if (arrayList != null) {
            while (i < l) {
                obj = arrayList.get(i);
                String obj_currencyname = obj.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj_currencyname);
                String obj_currencycode = currencyset.getCode();

                String[] tokens = obj.getDate().split(Pattern.quote("/"));
                if(tokens[1].equals("1")) { month=1; }if(tokens[1].equals("7")) { month=7; }
                if(tokens[1].equals("2")) { month=2; }if(tokens[1].equals("8")) { month=8; }
                if(tokens[1].equals("3")) { month=3; }if(tokens[1].equals("9")) { month=9; }
                if(tokens[1].equals("4")) { month=4; }if(tokens[1].equals("10")) { month=10; }
                if(tokens[1].equals("5")) { month=5; }if(tokens[1].equals("11")) { month=11; }
                if(tokens[1].equals("6")) { month=6; }if(tokens[1].equals("12")) { month=12; }

               if(monthcount[month]==0)
               {
                   monthcount[month] =1;
               }


                to3 = obj_currencycode;
                moneyValue3 = obj.getAmount();
               rateValue = myDB.getcurrency_rate_date(to3,obj.getDate());
                resultVal3 = moneyValue3 / rateValue;

                total_money_default_income = resultVal3;
                if (default_currency != null) {
                    ExtendedCurrency currencyset1 = ExtendedCurrency.getCurrencyByName(default_currency);
                    currency_code = currencyset1.getCode();
                    money_income = resultVal3;
                    Double rateValue1 = myDB.getcurrency_rate_date(currency_code,obj.getDate());
                    total_money_default_income = money_income * rateValue1;
                }
                total_income_sum=total_income_sum+total_money_default_income;
                i = i + 1;
            }

            int totalnumberofmonths=0;
            for(int k=0;k<13;k++)
            {
                totalnumberofmonths =totalnumberofmonths+ monthcount[k];
            }
            if(totalnumberofmonths!=0){
                Float avg =0f;
                avg= Float.parseFloat(String.valueOf(total_income_sum/totalnumberofmonths));
                avgincome.setVisibility(View.VISIBLE);
                avgincome.setText(avg.toString());

            }


            Float income_float = 0f;
            income_float = Float.parseFloat(String.valueOf((total_income_sum)));
            total_income.setText(income_float.toString());


        } else {
            Float income_float = 0f;
            income_float = Float.parseFloat(String.valueOf((total_income_sum)));
            total_income.setText("0.00");
        }

    }

    */
    @SuppressLint("SetTextI18n")
    public void Income_amount_yearly_down() {
        String startdate= make_startdate_yearly();

        ArrayList<ExpenseIncome> arrayList = myDB.get_OverviewIncome_yearly(startdate,user[0],user[1]);
        ExpenseIncome obj;
        String default_currency = myDB.getDataSettings(user[0],user[1]);
        monthcount[0]=0;  monthcount[1]=0;monthcount[2]=0;monthcount[3]=0;monthcount[4]=0;monthcount[5]=0;monthcount[6]=0;
        monthcount[7]=0;monthcount[8]=0;monthcount[9]=0;monthcount[10]=0;monthcount[11]=0;monthcount[12]=0;

        int l;
        int i = 0;
        l = arrayList.size();
        int month =1;
        if (arrayList != null) {
            while (i < l) {
                obj = arrayList.get(i);
                String obj_currencyname = obj.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj_currencyname);
                String obj_currencycode = currencyset.getCode();

                String[] tokens = obj.getDate().split(Pattern.quote("/"));
                if(tokens[1].equals("1")) { month=1; }if(tokens[1].equals("7")) { month=7; }
                if(tokens[1].equals("2")) { month=2; }if(tokens[1].equals("8")) { month=8; }
                if(tokens[1].equals("3")) { month=3; }if(tokens[1].equals("9")) { month=9; }
                if(tokens[1].equals("4")) { month=4; }if(tokens[1].equals("10")) { month=10; }
                if(tokens[1].equals("5")) { month=5; }if(tokens[1].equals("11")) { month=11; }
                if(tokens[1].equals("6")) { month=6; }if(tokens[1].equals("12")) { month=12; }

                if(monthcount[month]==0)
                {
                    monthcount[month] =1;
                }


                to3 = obj_currencycode;
                moneyValue3 = obj.getAmount();
                rateValue = myDB.getcurrency_rate_date(to3,obj.getDate());
                resultVal3 = moneyValue3 / rateValue;

                total_money_default_income = resultVal3;
                if (default_currency != null) {
                    ExtendedCurrency currencyset1 = ExtendedCurrency.getCurrencyByName(default_currency);
                    currency_code = currencyset1.getCode();
                    money_income = resultVal3;
                    Double rateValue1 = myDB.getcurrency_rate_date(currency_code,obj.getDate());
                    total_money_default_income = money_income * rateValue1;
                }
                total_income_sum=total_income_sum+total_money_default_income;
                i = i + 1;
            }

            int totalnumberofmonths=0;
            int totalnumberofyears=0;
            Double total = average_monthly_income();
            for(int k=0;k<13;k++)
            {
                totalnumberofmonths =totalnumberofmonths+ monthcount[k];
            }

            totalnumberofyears=myDB.getTotal_Year(user[0],user[1]);
            if(totalnumberofmonths!=0){
                Float avg =0f;
                avg= Float.parseFloat(String.valueOf(total/totalnumberofyears));
               // avgincome.setVisibility(View.VISIBLE);
                avgincome.setText(avg.toString());

            }


            Float income_float = 0f;
            income_float = Float.parseFloat(String.valueOf((total_income_sum)));
            total_income.setText(income_float.toString());


        } else {
            Float income_float = 0f;
            income_float = Float.parseFloat(String.valueOf((total_income_sum)));
            total_income.setText("0.00");
        }

    }

    public Double average_monthly_expense()
    {
        total_expense_sum=0.00;
        ArrayList<ExpenseIncome> arrayList = myDB.getallData_Expense(user[0],user[1]);
        ExpenseIncome obj;
        String default_currency = myDB.getDataSettings(user[0],user[1]);
        int l;
        int i = 0;
        Double total=0.0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj = arrayList.get(i);
                String obj_currencyname = obj.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj_currencyname);
                String obj_currencycode = currencyset.getCode();

                to4 = obj_currencycode;
                moneyValue4 = obj.getAmount();
                rateValue = myDB.getcurrency_rate_date(to4,obj.getDate());
                resultVal4 = moneyValue4 / rateValue;
                total_money_default_expense = resultVal4;

                if (default_currency != null) {
                    ExtendedCurrency currencyset1 = ExtendedCurrency.getCurrencyByName(default_currency);
                    currency_code = currencyset1.getCode();
                    money_expense = resultVal4;
                    Double rateValue1 = myDB.getcurrency_rate_date(currency_code,obj.getDate());
                    total_money_default_expense = money_expense * rateValue1;
                }
                total_expense_sum=total_expense_sum+total_money_default_expense;
                i = i + 1;
            }

            total= total_expense_sum;

        } else {
            total=0.0;

        }
        return total;
    }

    public Double average_monthly_income()
    {
        total_income_sum=0.00;
        ArrayList<ExpenseIncome> arrayList = myDB.getallData_Income(user[0],user[1]);
        ExpenseIncome obj;
        String default_currency = myDB.getDataSettings(user[0],user[1]);
        int l;
        int i = 0;
        Double total=0.0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj = arrayList.get(i);
                String obj_currencyname = obj.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj_currencyname);
                String obj_currencycode = currencyset.getCode();

                to3 = obj_currencycode;
                moneyValue3 = obj.getAmount();
                rateValue = myDB.getcurrency_rate_date(to3,obj.getDate());
                resultVal3 = moneyValue3 / rateValue;

                total_money_default_income = resultVal3;
                if (default_currency != null) {
                    ExtendedCurrency currencyset1 = ExtendedCurrency.getCurrencyByName(default_currency);
                    currency_code = currencyset1.getCode();
                    money_income = resultVal3;
                    Double rateValue1 = myDB.getcurrency_rate_date(currency_code,obj.getDate());
                    total_money_default_income = money_income * rateValue1;
                }
                total_income_sum=total_income_sum+total_money_default_income;
                i = i + 1;
            }

       total= total_income_sum;


        } else {
           total=0.0;
        }
        return total;
    }

    @SuppressLint("SetTextI18n")
    public void Expense_amount() {

        ArrayList<ExpenseIncome> arrayList = myDB.getallData_Expense(user[0],user[1]);
        ExpenseIncome obj;
        String default_currency = myDB.getDataSettings(user[0],user[1]);
        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj = arrayList.get(i);
                String obj_currencyname = obj.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj_currencyname);
                String obj_currencycode = currencyset.getCode();

                to4 = obj_currencycode;
                moneyValue4 = obj.getAmount();
              rateValue = myDB.getcurrency_rate_date(to4,obj.getDate());
               resultVal4 = moneyValue4 / rateValue;
                total_money_default_expense = resultVal4;

                if (default_currency != null) {
                    ExtendedCurrency currencyset1 = ExtendedCurrency.getCurrencyByName(default_currency);
                    currency_code = currencyset1.getCode();
                    money_expense = resultVal4;
                    Double rateValue1 = myDB.getcurrency_rate_date(currency_code,obj.getDate());
                    total_money_default_expense = money_expense * rateValue1;
                }
                total_expense_sum=total_expense_sum+total_money_default_expense;
                i = i + 1;
            }
            int totalnumberofmonths=0;
            totalnumberofmonths=myDB.getTotal_Month(user[0],user[1]);
            if(totalnumberofmonths!=0){
                Float avg =0f;
                avg= Float.parseFloat(String.valueOf(total_expense_sum/totalnumberofmonths));
                // avgincome.setVisibility(View.VISIBLE);
                avgexpense.setText(avg.toString());

            }
            Float expense_float = 0f;
            expense_float = Float.parseFloat(String.valueOf((total_expense_sum)));
            total_expense.setText(expense_float.toString().toString());

        } else {
            Float expense_float = 0f;
            expense_float = Float.parseFloat(String.valueOf((total_expense_sum)));
            total_expense.setText("0.00");
        }
    }
    @SuppressLint("SetTextI18n")
    public void Expense_amount_monthly_down() {
        avgexpense.setVisibility(View.VISIBLE);
        avgincome.setVisibility(View.VISIBLE);
        String startdate =make_start_date();
        ArrayList<ExpenseIncome> arrayList = myDB.get_OverviewExpense_monthly(startdate,user[0],user[1]);
        ExpenseIncome obj;
        String default_currency = myDB.getDataSettings(user[0],user[1]);
        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj = arrayList.get(i);
                String obj_currencyname = obj.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj_currencyname);
                String obj_currencycode = currencyset.getCode();

                to4 = obj_currencycode;
                moneyValue4 = obj.getAmount();
                rateValue = myDB.getcurrency_rate_date(to4,obj.date);
                resultVal4 = moneyValue4 / rateValue;
                total_money_default_expense = resultVal4;



                if (default_currency != null) {
                    ExtendedCurrency currencyset1 = ExtendedCurrency.getCurrencyByName(default_currency);
                    currency_code = currencyset1.getCode();
                    money_expense = resultVal4;
                    Double rateValue1 = myDB.getcurrency_rate_date(currency_code,obj.getDate());
                    total_money_default_expense = money_expense * rateValue1;
                }
                total_expense_sum=total_expense_sum+total_money_default_expense;
                i = i + 1;
            }

            int totalnumberofmonths=0;
            Double total = average_monthly_expense();
            totalnumberofmonths=myDB.getTotal_Month(user[0],user[1]);
            if(totalnumberofmonths!=0){
                Float avg =0f;
                avg= Float.parseFloat(String.valueOf(total/totalnumberofmonths));
                // avgincome.setVisibility(View.VISIBLE);
                avgexpense.setText(avg.toString());

            }
            Float expense_float = 0f;
            expense_float = Float.parseFloat(String.valueOf((total_expense_sum)));
            total_expense.setText(expense_float.toString().toString());

        } else {
            Float expense_float = 0f;
            expense_float = Float.parseFloat(String.valueOf((total_expense_sum)));
            total_expense.setText("0.00");
        }

    }
  /*  @SuppressLint("SetTextI18n")
    public void Expense_amount_monthly() {
        String startdate =makestartdate();
        ArrayList<ExpenseIncome> arrayList = myDB.get_OverviewExpense_monthly(startdate,user[0],user[1]);
        ExpenseIncome obj;
        String default_currency = myDB.getDataSettings(user[0],user[1]);
        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj = arrayList.get(i);
                String obj_currencyname = obj.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj_currencyname);
                String obj_currencycode = currencyset.getCode();

                to4 = obj_currencycode;
                moneyValue4 = obj.getAmount();
               rateValue = myDB.getcurrency_rate_date(to4,obj.date);
                resultVal4 = moneyValue4 / rateValue;
                total_money_default_expense = resultVal4;



                if (default_currency != null) {
                    ExtendedCurrency currencyset1 = ExtendedCurrency.getCurrencyByName(default_currency);
                    currency_code = currencyset1.getCode();
                    money_expense = resultVal4;
                    Double rateValue1 = myDB.getcurrency_rate_date(currency_code,obj.getDate());
                    total_money_default_expense = money_expense * rateValue1;
                }
                total_expense_sum=total_expense_sum+total_money_default_expense;
                i = i + 1;
            }
            Float expense_float = 0f;
            expense_float = Float.parseFloat(String.valueOf((total_expense_sum)));
            total_expense.setText(expense_float.toString().toString());

        } else {
            Float expense_float = 0f;
            expense_float = Float.parseFloat(String.valueOf((total_expense_sum)));
            total_expense.setText("0.00");
        }

    }

   */
    @SuppressLint("SetTextI18n")
    public void Expense_amount_yearly_down() {
        String startdate =make_startdate_yearly();

        ArrayList<ExpenseIncome> arrayList = myDB.get_OverviewExpense_yearly(startdate,user[0],user[1]);
        ExpenseIncome obj;
        String default_currency = myDB.getDataSettings(user[0],user[1]);

        monthcount[0]=0;  monthcount[1]=0;monthcount[2]=0;monthcount[3]=0;monthcount[4]=0;monthcount[5]=0;monthcount[6]=0;
        monthcount[7]=0;monthcount[8]=0;monthcount[9]=0;monthcount[10]=0;monthcount[11]=0;monthcount[12]=0;
        int l;
        int i = 0;
        l = arrayList.size();
        int month =1;
        if (arrayList != null) {
            while (i < l) {
                obj = arrayList.get(i);
                String obj_currencyname = obj.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj_currencyname);
                String obj_currencycode = currencyset.getCode();

                String[] tokens = obj.getDate().split(Pattern.quote("/"));
                if(tokens[1].equals("1")) { month=1; }if(tokens[1].equals("7")) { month=7; }
                if(tokens[1].equals("2")) { month=2; }if(tokens[1].equals("8")) { month=8; }
                if(tokens[1].equals("3")) { month=3; }if(tokens[1].equals("9")) { month=9; }
                if(tokens[1].equals("4")) { month=4; }if(tokens[1].equals("10")) { month=10; }
                if(tokens[1].equals("5")) { month=5; }if(tokens[1].equals("11")) { month=11; }
                if(tokens[1].equals("6")) { month=6; }if(tokens[1].equals("12")) { month=12; }

                if(monthcount[month]==0)
                {
                    monthcount[month] =1;
                }


                to4 = obj_currencycode;
                moneyValue4 = obj.getAmount();
                rateValue = myDB.getcurrency_rate_date(to4,obj.getDate());
                resultVal4 = moneyValue4 / rateValue;
                total_money_default_expense = resultVal4;

                if (default_currency != null) {
                    ExtendedCurrency currencyset1 = ExtendedCurrency.getCurrencyByName(default_currency);
                    currency_code = currencyset1.getCode();
                    money_expense = resultVal4;
                    Double rateValue1 = myDB.getcurrency_rate_date(currency_code,obj.getDate());
                    total_money_default_expense = money_expense * rateValue1;
                }
                total_expense_sum=total_expense_sum+total_money_default_expense;
                i = i + 1;
            }

            int totalnumberofmonths=0;
            int totalnumberofyears=0;
            Double total = average_monthly_expense();
            for(int k=0;k<13;k++)
            {
                totalnumberofmonths =totalnumberofmonths+ monthcount[k];
            }

            totalnumberofyears=myDB.getTotal_Year(user[0],user[1]);
            if(totalnumberofyears!=0){
                Float avg =0f;
                avg= Float.parseFloat(String.valueOf(total/totalnumberofyears));
                // avgincome.setVisibility(View.VISIBLE);
                avgexpense.setText(avg.toString());

            }

            Float expense_float = 0f;
            expense_float = Float.parseFloat(String.valueOf((total_expense_sum)));
            total_expense.setText(expense_float.toString().toString());

        } else {
            Float expense_float = 0f;
            expense_float = Float.parseFloat(String.valueOf((total_expense_sum)));
            total_expense.setText("0.00");
        }
    }
  /*  @SuppressLint("SetTextI18n")
    public void Expense_amount_yearly() {
        String startdate =makestartdateyearly();

        ArrayList<ExpenseIncome> arrayList = myDB.get_OverviewExpense_yearly(startdate,user[0],user[1]);
        ExpenseIncome obj;
        String default_currency = myDB.getDataSettings(user[0],user[1]);

        monthcount[0]=0;  monthcount[1]=0;monthcount[2]=0;monthcount[3]=0;monthcount[4]=0;monthcount[5]=0;monthcount[6]=0;
        monthcount[7]=0;monthcount[8]=0;monthcount[9]=0;monthcount[10]=0;monthcount[11]=0;monthcount[12]=0;
        int l;
        int i = 0;
        l = arrayList.size();
        int month =1;
        if (arrayList != null) {
            while (i < l) {
                obj = arrayList.get(i);
                String obj_currencyname = obj.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj_currencyname);
                String obj_currencycode = currencyset.getCode();

                String[] tokens = obj.getDate().split(Pattern.quote("/"));
                if(tokens[1].equals("1")) { month=1; }if(tokens[1].equals("7")) { month=7; }
                if(tokens[1].equals("2")) { month=2; }if(tokens[1].equals("8")) { month=8; }
                if(tokens[1].equals("3")) { month=3; }if(tokens[1].equals("9")) { month=9; }
                if(tokens[1].equals("4")) { month=4; }if(tokens[1].equals("10")) { month=10; }
                if(tokens[1].equals("5")) { month=5; }if(tokens[1].equals("11")) { month=11; }
                if(tokens[1].equals("6")) { month=6; }if(tokens[1].equals("12")) { month=12; }

                if(monthcount[month]==0)
                {
                    monthcount[month] =1;
                }


                to4 = obj_currencycode;
                moneyValue4 = obj.getAmount();
                rateValue = myDB.getcurrency_rate_date(to4,obj.getDate());
                resultVal4 = moneyValue4 / rateValue;
                total_money_default_expense = resultVal4;

                if (default_currency != null) {
                    ExtendedCurrency currencyset1 = ExtendedCurrency.getCurrencyByName(default_currency);
                    currency_code = currencyset1.getCode();
                    money_expense = resultVal4;
                    Double rateValue1 = myDB.getcurrency_rate_date(currency_code,obj.getDate());
                    total_money_default_expense = money_expense * rateValue1;
                }
                total_expense_sum=total_expense_sum+total_money_default_expense;
                i = i + 1;
            }

            int totalnumberofmonths=0;
            for(int k=0;k<13;k++)
            {
                totalnumberofmonths =totalnumberofmonths+ monthcount[k];
            }
            if(totalnumberofmonths!=0){
                Float avg =0f;
                avg= Float.parseFloat(String.valueOf(total_expense_sum/totalnumberofmonths));
                avgexpense.setVisibility(View.VISIBLE);
                avgexpense.setText(avg.toString());

            }

            Float expense_float = 0f;
            expense_float = Float.parseFloat(String.valueOf((total_expense_sum)));
            total_expense.setText(expense_float.toString().toString());

        } else {
            Float expense_float = 0f;
            expense_float = Float.parseFloat(String.valueOf((total_expense_sum)));
            total_expense.setText("0.00");
        }
    }

   */
    @SuppressLint("SetTextI18n")
    public void Balance_amount() {

        Float balance_float = 0f;

        balance_float = Float.parseFloat(String.valueOf((total_income_sum - total_expense_sum)));

        total_amount.setText(balance_float.toString());
    }
    public void pie_chart_yearly() {
        categories.setVisibility(View.VISIBLE);
        shopping_yearly = 0.0f;
        car_yearly = 0.0f;
        entertainment_yearly = 0.0f;
        education_yearly = 0.0f;
        food_yearly = 0.0f;
        grocery_yearly = 0.0f;
        health_yearly = 0.0f;
        travel_yearly = 0.0f;
        other_yearly = 0.0f;



            String startDate = make_startdate_yearly();
            shoppingList_yearly = myDB.get_Overview_Category_yearly("Shopping", startDate, user[0], user[1]);

            shopping_yearly = getConverted(shoppingList_yearly);
            if(shopping_yearly > 0)
            {
                rl_shopping.setVisibility(View.VISIBLE);
                shopping_ex.setText(String.valueOf(shopping_yearly));
            }
            else
            {
                rl_shopping.setVisibility(View.GONE);
            }
            carList_yearly = myDB.get_Overview_Category_yearly("Car", startDate, user[0], user[1]);
            car_yearly = getConvertedCar(carList_yearly);
        if(car_yearly > 0)
        {
            rl_car.setVisibility(View.VISIBLE);
            car_ex.setText(String.valueOf(car_yearly));
        }
        else
        {
            rl_car.setVisibility(View.GONE);
        }
            entertainmentList_yearly = myDB.get_Overview_Category_yearly("Entertainment", startDate, user[0], user[1]);
            entertainment_yearly = getConvertedEntertainment(entertainmentList_yearly);
        if(entertainment_yearly > 0)
        {
            rl_entertainment.setVisibility(View.VISIBLE);
            enterntainment_ex.setText(String.valueOf(entertainment_yearly));
        }
        else
        {
            rl_entertainment.setVisibility(View.GONE);
        }

            educationList_yearly = myDB.get_Overview_Category_yearly("Education", startDate, user[0], user[1]);
            education_yearly = getConvertedEducation(educationList_yearly);
        if(education_yearly > 0)
        {
            rl_education.setVisibility(View.VISIBLE);
            education_ex.setText(String.valueOf(education_yearly));
        }
        else
        {
            rl_education.setVisibility(View.GONE);
        }
            foodList_yearly = myDB.get_Overview_Category_yearly("Food n Drink", startDate, user[0], user[1]);
            food_yearly = getConvertedFood(foodList_yearly);
        if(food_yearly > 0)
        {
            rl_food.setVisibility(View.VISIBLE);
            food_ex.setText(String.valueOf(food_yearly));
        }
        else
        {
            rl_food.setVisibility(View.GONE);
        }
            groceryList_yearly = myDB.get_Overview_Category_yearly("Grocery", startDate, user[0], user[1]);
            grocery_yearly = getConvertedGrocery(groceryList_yearly);
        if(grocery_yearly > 0)
        {
            rl_grocery.setVisibility(View.VISIBLE);
            grocery_ex.setText(String.valueOf(grocery_yearly));
        }
        else
        {
            rl_grocery.setVisibility(View.GONE);
        }
            healthList_yearly = myDB.get_Overview_Category_yearly("Health", startDate, user[0], user[1]);
            health_yearly = getConvertedHealth(healthList_yearly);
        if(health_yearly > 0)
        {
            rl_health.setVisibility(View.VISIBLE);
            health_ex.setText(String.valueOf(health_yearly));
        }
        else
        {
            rl_health.setVisibility(View.GONE);
        }
            travelList_yearly = myDB.get_Overview_Category_yearly("Travel", startDate, user[0], user[1]);
            travel_yearly = getConvertedTravel(travelList_yearly);
        if(travel_yearly > 0)
        {
            rl_travel.setVisibility(View.VISIBLE);
            travel_ex.setText(String.valueOf(travel_yearly));
        }
        else
        {
            rl_travel.setVisibility(View.GONE);
        }
            otherList_yearly = myDB.get_Overview_Category_yearly("Other", startDate, user[0], user[1]);
            other_yearly = getConvertedOther(otherList_yearly);
        if(other_yearly > 0)
        {
            rl_other.setVisibility(View.VISIBLE);
            other_ex.setText(String.valueOf(other_yearly));
        }
        else
        {
            rl_other.setVisibility(View.GONE);
        }


            ArrayList<PieEntry> values = new ArrayList<>();
            if (shopping_yearly > 0) {
                values.add(new PieEntry(shopping_yearly, "Shopping"));

            }
            if (car_yearly > 0) {
                values.add(new PieEntry(car_yearly, "Car"));

            }
            if (entertainment_yearly > 0) {
                values.add(new PieEntry(entertainment_yearly, "Entertainment"));
            }
            if (education_yearly > 0) {
                values.add(new PieEntry(education_yearly, "Education"));
            }
            if (food_yearly > 0) {
                values.add(new PieEntry(food_yearly, "Food n Drink"));

            }
            if (grocery_yearly > 0) {
                values.add(new PieEntry(grocery_yearly, "Grocery"));
            }
            if (health_yearly > 0) {
                values.add(new PieEntry(health_yearly, "Health"));
            }
            if (travel_yearly > 0) {
                values.add(new PieEntry(travel_yearly, "Travel"));

            }
            if (other_yearly > 0) {
                values.add(new PieEntry(other_yearly, "Other"));
            }


            pieChart.animateY(2000, Easing.EaseInOutCubic);
            PieDataSet dataSet = new PieDataSet(values, "Categories");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

            PieData data = new PieData(dataSet);
            data.setValueTextSize(10f);
            data.setValueFormatter(new PercentFormatter(pieChart));
            data.setValueTextColor(Color.BLACK);

        pieChart.setVisibility(View.VISIBLE);
        graph_not.setVisibility(View.INVISIBLE);

        pieChart.setData(data);

        if(shopping_yearly ==0 && car_yearly==0 && entertainment_yearly==0 && education_yearly==0 && food_yearly==0 && grocery_yearly==0 && health_yearly==0 && travel_yearly==0 && other_yearly==0)
        {
            categories.setVisibility(View.GONE);
            pieChart.setVisibility(View.INVISIBLE);
            graph_not.setVisibility(View.VISIBLE);
        }
        }

    public void pie_chart_monthly() {
        if(monthly_txt.getText().toString().equals("Month")||yearly_txt.getText().toString().equals("Year"))
        {

        }
        else
            {
                categories.setVisibility(View.VISIBLE);

               shopping_monthly = 0.0f;
                car_monthly = 0.0f;
                entertainment_monthly = 0.0f;
                education_monthly = 0.0f;
                food_monthly = 0.0f;
                grocery_monthly = 0.0f;
                health_monthly = 0.0f;
                travel_monthly = 0.0f;
                other_monthly = 0.0f;



            String startDate = make_start_date();
            shoppingList_monthly = myDB.get_Overview_Category_monthly("Shopping", startDate, user[0], user[1]);
            shopping_monthly = getConverted(shoppingList_monthly);
                if(shopping_monthly > 0)
                {
                    rl_shopping.setVisibility(View.VISIBLE);
                    shopping_ex.setText(String.valueOf(shopping_monthly));
                }
                else
                {
                    rl_shopping.setVisibility(View.GONE);
                }
            carList_monthly = myDB.get_Overview_Category_monthly("Car", startDate, user[0], user[1]);
            car_monthly = getConvertedCar(carList_monthly);
                if(car_monthly > 0)
                {
                    rl_car.setVisibility(View.VISIBLE);
                    car_ex.setText(String.valueOf(car_monthly));
                }
                else
                {
                    rl_car.setVisibility(View.GONE);
                }
            entertainmentList_monthly = myDB.get_Overview_Category_monthly("Entertainment", startDate, user[0], user[1]);
            entertainment_monthly = getConvertedEntertainment(entertainmentList_monthly);
                if(entertainment_monthly > 0)
                {
                    rl_entertainment.setVisibility(View.VISIBLE);
                    enterntainment_ex.setText(String.valueOf(entertainment_monthly));
                }
                else
                {
                    rl_entertainment.setVisibility(View.GONE);
                }
            educationList_monthly = myDB.get_Overview_Category_monthly("Education", startDate, user[0], user[1]);
            education_monthly = getConvertedEducation(educationList_monthly);
                if(education_monthly > 0)
                {
                    rl_education.setVisibility(View.VISIBLE);
                    education_ex.setText(String.valueOf(education_monthly));
                }
                else
                {
                    rl_education.setVisibility(View.GONE);
                }
            foodList_monthly = myDB.get_Overview_Category_monthly("Food n Drink", startDate, user[0], user[1]);
            food_monthly = getConvertedFood(foodList_monthly);
                if(food_monthly > 0)
                {
                    rl_food.setVisibility(View.VISIBLE);
                    food_ex.setText(String.valueOf(food_monthly));
                }
                else
                {
                    rl_food.setVisibility(View.GONE);
                }
            groceryList_monthly = myDB.get_Overview_Category_monthly("Grocery", startDate, user[0], user[1]);
            grocery_monthly = getConvertedGrocery(groceryList_monthly);
                if(grocery_monthly > 0)
                {
                    rl_grocery.setVisibility(View.VISIBLE);
                    grocery_ex.setText(String.valueOf(grocery_monthly));
                }
                else
                {
                    rl_grocery.setVisibility(View.GONE);
                }
            healthList_monthly = myDB.get_Overview_Category_monthly("Health", startDate, user[0], user[1]);
            health_monthly = getConvertedHealth(healthList_monthly);
                if(health_monthly > 0)
                {
                    rl_health.setVisibility(View.VISIBLE);
                    health_ex.setText(String.valueOf(health_monthly));
                }
                else
                {
                    rl_health.setVisibility(View.GONE);
                }
            travelList_monthly = myDB.get_Overview_Category_monthly("Travel", startDate, user[0], user[1]);
            travel_monthly = getConvertedTravel(travelList_monthly);

                if(travel_monthly > 0)
                {
                    rl_travel.setVisibility(View.VISIBLE);
                    travel_ex.setText(String.valueOf(travel_monthly));
                }
                else
                {
                    rl_travel.setVisibility(View.GONE);
                }
            otherList_monthly = myDB.get_Overview_Category_monthly("Other", startDate, user[0], user[1]);
            other_monthly = getConvertedOther(otherList_monthly);
                if(other_monthly > 0)
                {
                    rl_other.setVisibility(View.VISIBLE);
                    other_ex.setText(String.valueOf(other_monthly));
                }
                else
                {
                    rl_other.setVisibility(View.GONE);
                }


            ArrayList<PieEntry> values = new ArrayList<>();
            if (shopping_monthly > 0) {
                values.add(new PieEntry(shopping_monthly, "Shopping"));

            }
            if (car_monthly > 0) {
                values.add(new PieEntry(car_monthly, "Car"));

            }
            if (entertainment_monthly > 0) {
                values.add(new PieEntry(entertainment_monthly, "Entertainment"));
            }
            if (education_monthly > 0) {
                values.add(new PieEntry(education_monthly, "Education"));
            }
            if (food_monthly > 0) {
                values.add(new PieEntry(food_monthly, "Food n Drink"));

            }
            if (grocery_monthly > 0) {
                values.add(new PieEntry(grocery_monthly, "Grocery"));
            }
            if (health_monthly > 0) {
                values.add(new PieEntry(health_monthly, "Health"));
            }
            if (travel_monthly > 0) {
                values.add(new PieEntry(travel_monthly, "Travel"));

            }
            if (other_monthly > 0) {
                values.add(new PieEntry(other_monthly, "Other"));
            }

                pieChart.setVisibility(View.VISIBLE);
                graph_not.setVisibility(View.INVISIBLE);


                pieChart.animateY(2000, Easing.EaseInOutCubic);
            PieDataSet dataSet = new PieDataSet(values, "Categories");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

            PieData data = new PieData(dataSet);
            data.setValueTextSize(10f);
            data.setValueFormatter(new PercentFormatter(pieChart));
            data.setValueTextColor(Color.BLACK);

                pieChart.setData(data);
                if(shopping_monthly ==0 && car_monthly==0 && entertainment_monthly==0 && education_monthly==0 && food_monthly==0 && grocery_monthly==0 && health_monthly==0 && travel_monthly==0 && other_monthly==0)
                {
                    categories.setVisibility(View.GONE);
                    pieChart.setVisibility(View.INVISIBLE);
                    graph_not.setVisibility(View.VISIBLE);
                }
        }
    }

    public void pie_chart() {
        categories.setVisibility(View.VISIBLE);
        shopping = 0.0f;
        car = 0.0f;
        entertainment = 0.0f;
        education = 0.0f;
        food = 0.0f;
        grocery = 0.0f;
        health = 0.0f;
        travel = 0.0f;
        other = 0.0f;


        shoppingList = myDB.getallData_Category_Expense("Shopping",user[0],user[1]);
        shopping = getConverted(shoppingList);
        if(shopping > 0)
        {
            rl_shopping.setVisibility(View.VISIBLE);
            shopping_ex.setText(String.valueOf(shopping));
        }
        else
        {
            rl_shopping.setVisibility(View.GONE);
        }
        carList = myDB.getallData_Category_Expense("Car",user[0],user[1]);
        car = getConvertedCar(carList);
        if(car > 0)
        {
            rl_car.setVisibility(View.VISIBLE);
            car_ex.setText(String.valueOf(car));
        }
        else
        {
            rl_car.setVisibility(View.GONE);
        }
        entertainmentList = myDB.getallData_Category_Expense("Entertainment",user[0],user[1]);
        entertainment = getConvertedEntertainment(entertainmentList);
        if(entertainment > 0)
        {
            rl_entertainment.setVisibility(View.VISIBLE);
            enterntainment_ex.setText(String.valueOf(entertainment));
        }
        else
        {
            rl_entertainment.setVisibility(View.GONE);
        }
        educationList = myDB.getallData_Category_Expense("Education",user[0],user[1]);
        education = getConvertedEducation(educationList);

        if(education > 0)
        {
            rl_education.setVisibility(View.VISIBLE);
            education_ex.setText(String.valueOf(education));
        }
        else
        {
            rl_education.setVisibility(View.GONE);
        }
        foodList = myDB.getallData_Category_Expense("Food n Drink",user[0],user[1]);
        food = getConvertedFood(foodList);
        if(food > 0)
        {
            rl_food.setVisibility(View.VISIBLE);
            food_ex.setText(String.valueOf(food));
        }
        else
        {
            rl_food.setVisibility(View.GONE);
        }
        groceryList = myDB.getallData_Category_Expense("Grocery",user[0],user[1]);
        grocery = getConvertedGrocery(groceryList);

        if(grocery > 0)
        {
            rl_grocery.setVisibility(View.VISIBLE);
            grocery_ex.setText(String.valueOf(grocery));
        }
        else
        {
            rl_grocery.setVisibility(View.GONE);
        }
        healthList = myDB.getallData_Category_Expense("Health",user[0],user[1]);
        health = getConvertedHealth(healthList);
        if(health > 0)
        {
            rl_health.setVisibility(View.VISIBLE);
            health_ex.setText(String.valueOf(health));
        }
        else
        {
            rl_health.setVisibility(View.GONE);
        }
        travelList = myDB.getallData_Category_Expense("Travel",user[0],user[1]);
        travel = getConvertedTravel(travelList);
        if(travel > 0)
        {
            rl_travel.setVisibility(View.VISIBLE);
            travel_ex.setText(String.valueOf(travel));
        }
        else
        {
            rl_travel.setVisibility(View.GONE);
        }
        otherList = myDB.getallData_Category_Expense("Other",user[0],user[1]);
        other = getConvertedOther(otherList);
        if(other > 0)
        {
            rl_other.setVisibility(View.VISIBLE);
            other_ex.setText(String.valueOf(other));
        }
        else
        {
            rl_other.setVisibility(View.GONE);
        }
       /* borrowList = myDB.getallData_Category_Income("Borrow",user[0],user[1]);
        borrow = getConvertedOther(borrowList);
        bonusList = myDB.getallData_Category_Income("Bonus",user[0],user[1]);
        bonus = getConvertedOther(bonusList);
        salaryList = myDB.getallData_Category_Income("Salary",user[0],user[1]);
        salary = getConvertedOther(salaryList);
        allowanceList = myDB.getallData_Category_Income("Allowance",user[0],user[1]);
        allowance = getConvertedOther(allowanceList);

        */






        ArrayList<PieEntry> values = new ArrayList<>();
        if (shopping > 0) {
            values.add(new PieEntry(shopping, "Shopping"));

        }
        if (car > 0) {
            values.add(new PieEntry(car, "Car"));

        }
        if (entertainment > 0) {
            values.add(new PieEntry(entertainment, "Entertainment"));
        }
        if (education > 0) {
            values.add(new PieEntry(education, "Education"));
        }
        if (food > 0) {
            values.add(new PieEntry(food, "Food n Drink"));

        }
        if (grocery > 0) {
            values.add(new PieEntry(grocery, "Grocery"));
        }
        if (health > 0) {
            values.add(new PieEntry(health, "Health"));
        }
        if (travel > 0) {
            values.add(new PieEntry(travel, "Travel"));

        }
        if (other > 0) {
            values.add(new PieEntry(other, "Other"));
        }
      /*  if (borrow > 0) {
            values.add(new PieEntry(borrow, "Borrow"));
        }
        if (allowance > 0) {
            values.add(new PieEntry(allowance, "Allowance"));
        }
        if (bonus > 0) {
            values.add(new PieEntry(bonus, "Bonus"));
        }
        if (salary > 0) {
            values.add(new PieEntry(salary, "Salary"));
        }

       */


        pieChart.setVisibility(View.VISIBLE);
        graph_not.setVisibility(View.INVISIBLE);


        pieChart.animateY(2000, Easing.EaseInOutCubic);
        PieDataSet dataSet = new PieDataSet(values, "Categories");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        if(shopping ==0 && car==0 && entertainment==0 && education==0 && food==0 && grocery==0 && health==0 && travel==0 && other==0)
        {
            categories.setVisibility(View.GONE);
            pieChart.setVisibility(View.INVISIBLE);
            graph_not.setVisibility(View.VISIBLE);
        }
    }

    public void pie_chart_Income_yearly()
    {

        categories_in.setVisibility(View.VISIBLE);
            borrow = 0.0f;
            bonus = 0.0f;
            salary = 0.0f;
            allowance = 0.0f;

            String startDate = make_startdate_yearly();


            borrowList = myDB.get_OverviewIncome_Category_yearly("Borrow", startDate, user[0], user[1]);
            borrow = getConvertedBorrow(borrowList);
        if(borrow > 0)
        {
            rl_borrow.setVisibility(View.VISIBLE);
            borrow_in.setText(String.valueOf(borrow));
        }
        else
        {
            rl_borrow.setVisibility(View.GONE);
        }
            bonusList = myDB.get_OverviewIncome_Category_yearly("Bonus", startDate, user[0], user[1]);
            bonus = getConvertedBonus(bonusList);
        if(bonus > 0)
        {
            rl_bonus.setVisibility(View.VISIBLE);
            bonus_in.setText(String.valueOf(bonus));
        }
        else
        {
            rl_bonus.setVisibility(View.GONE);
        }
            salaryList = myDB.get_OverviewIncome_Category_yearly("Salary", startDate, user[0], user[1]);
            salary = getConvertedSalary(salaryList);
        if(salary > 0)
        {
            rl_salary.setVisibility(View.VISIBLE);
            salary_in.setText(String.valueOf(salary));
        }
        else
        {
            rl_salary.setVisibility(View.GONE);
        }
            allowanceList = myDB.get_OverviewIncome_Category_yearly("Allowance", startDate, user[0], user[1]);
            allowance = getConvertedAllowance(allowanceList);
        if(allowance > 0)
        {
            rl_allowance.setVisibility(View.VISIBLE);
            allowance_in.setText(String.valueOf(allowance));
        }
        else
        {
            rl_allowance.setVisibility(View.GONE);
        }


            ArrayList<PieEntry> values = new ArrayList<>();

            if (borrow > 0) {
                values.add(new PieEntry(borrow, "Borrow"));
            }
            if (allowance > 0) {
                values.add(new PieEntry(allowance, "Allowance"));
            }
            if (bonus > 0) {
                values.add(new PieEntry(bonus, "Bonus"));
            }
            if (salary > 0) {
                values.add(new PieEntry(salary, "Salary"));
            }

        pieChart_monthly.setVisibility(View.VISIBLE);
        graph_not_income.setVisibility(View.INVISIBLE);
            pieChart_monthly.animateY(2000, Easing.EaseInOutCubic);
            PieDataSet dataSet = new PieDataSet(values, "Categories");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

            PieData data = new PieData(dataSet);
            data.setValueTextSize(10f);
            data.setValueFormatter(new PercentFormatter(pieChart_monthly));
            data.setValueTextColor(Color.BLACK);

            pieChart_monthly.setData(data);

            if(borrow == 0 && allowance==0 && bonus == 0 && salary==0)
            {
                categories_in.setVisibility(View.GONE);
                pieChart_monthly.setVisibility(View.INVISIBLE);
                graph_not_income.setVisibility(View.VISIBLE);
            }
        }

    public void pie_chart_Income_monthly() {

        if(monthly_txt.getText().toString().equals("Month")||yearly_txt.getText().toString().equals("Year"))
        {

        }
        else
            {
                categories_in.setVisibility(View.VISIBLE);
            borrow = 0.0f;
            bonus = 0.0f;
            salary = 0.0f;
            allowance = 0.0f;

            String startDate = make_start_date();


            borrowList = myDB.get_OverviewIncome_Category_monthly("Borrow", startDate, user[0], user[1]);
            borrow = getConvertedBorrow(borrowList);

                if(borrow > 0)
                {
                    rl_borrow.setVisibility(View.VISIBLE);
                    borrow_in.setText(String.valueOf(borrow));
                }
                else
                {
                    rl_borrow.setVisibility(View.GONE);
                }
            bonusList = myDB.get_OverviewIncome_Category_monthly("Bonus", startDate, user[0], user[1]);
            bonus = getConvertedBonus(bonusList);
                if(bonus > 0)
                {
                    rl_bonus.setVisibility(View.VISIBLE);
                    bonus_in.setText(String.valueOf(bonus));
                }
                else
                {
                    rl_bonus.setVisibility(View.GONE);
                }

            salaryList = myDB.get_OverviewIncome_Category_monthly("Salary", startDate, user[0], user[1]);
            salary = getConvertedSalary(salaryList);
                if(salary > 0)
                {
                    rl_salary.setVisibility(View.VISIBLE);
                    salary_in.setText(String.valueOf(salary));
                }
                else
                {
                    rl_salary.setVisibility(View.GONE);
                }
            allowanceList = myDB.get_OverviewIncome_Category_monthly("Allowance", startDate, user[0], user[1]);
            allowance = getConvertedAllowance(allowanceList);
                if(allowance > 0)
                {
                    rl_allowance.setVisibility(View.VISIBLE);
                    allowance_in.setText(String.valueOf(allowance));
                }
                else
                {
                    rl_allowance.setVisibility(View.GONE);
                }

            ArrayList<PieEntry> values = new ArrayList<>();

            if (borrow > 0) {
                values.add(new PieEntry(borrow, "Borrow"));
            }
            if (allowance > 0) {
                values.add(new PieEntry(allowance, "Allowance"));
            }
            if (bonus > 0) {
                values.add(new PieEntry(bonus, "Bonus"));
            }
            if (salary > 0) {
                values.add(new PieEntry(salary, "Salary"));
            }

                pieChart_monthly.setVisibility(View.VISIBLE);
                graph_not_income.setVisibility(View.INVISIBLE);

            pieChart_monthly.animateY(2000, Easing.EaseInOutCubic);
            PieDataSet dataSet = new PieDataSet(values, "Categories");
            dataSet.setSliceSpace(3f);
            dataSet.setSelectionShift(5f);
            dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

            PieData data = new PieData(dataSet);
            data.setValueTextSize(10f);
            data.setValueFormatter(new PercentFormatter(pieChart_monthly));
            data.setValueTextColor(Color.BLACK);

            pieChart_monthly.setData(data);
                if(borrow == 0 && allowance==0 && bonus == 0 && salary==0)
                {
                    categories_in.setVisibility(View.GONE);
                    pieChart_monthly.setVisibility(View.INVISIBLE);
                    graph_not_income.setVisibility(View.VISIBLE);
                }
        }
    }

    public void pie_chart_Income() {
        categories_in.setVisibility(View.VISIBLE);
        borrow = 0.0f;
        bonus = 0.0f;
        salary = 0.0f;
        allowance = 0.0f;



        borrowList = myDB.getallData_Category_Income("Borrow",user[0],user[1]);
        borrow = getConvertedBorrow(borrowList);

        if(borrow > 0)
        {
            rl_borrow.setVisibility(View.VISIBLE);
            borrow_in.setText(String.valueOf(borrow));
        }
        else
        {
            rl_borrow.setVisibility(View.GONE);
        }
        bonusList = myDB.getallData_Category_Income("Bonus",user[0],user[1]);
        bonus = getConvertedBonus(bonusList);
        if(bonus > 0)
        {
            rl_bonus.setVisibility(View.VISIBLE);
            bonus_in.setText(String.valueOf(bonus));
        }
        else
        {
            rl_bonus.setVisibility(View.GONE);
        }
        salaryList = myDB.getallData_Category_Income("Salary",user[0],user[1]);
        salary = getConvertedSalary(salaryList);
        if(salary > 0)
        {
            rl_salary.setVisibility(View.VISIBLE);
            salary_in.setText(String.valueOf(salary));
        }
        else
        {
            rl_salary.setVisibility(View.GONE);
        }
        allowanceList = myDB.getallData_Category_Income("Allowance",user[0],user[1]);
        allowance = getConvertedAllowance(allowanceList);
        if(allowance > 0)
        {
            rl_allowance.setVisibility(View.VISIBLE);
            allowance_in.setText(String.valueOf(allowance));
        }
        else
        {
            rl_allowance.setVisibility(View.GONE);
        }







        ArrayList<PieEntry> values = new ArrayList<>();

       if (borrow > 0) {
            values.add(new PieEntry(borrow, "Borrow"));
        }
        if (allowance > 0) {
            values.add(new PieEntry(allowance, "Allowance"));
        }
        if (bonus > 0) {
            values.add(new PieEntry(bonus, "Bonus"));
        }
        if (salary > 0) {
            values.add(new PieEntry(salary, "Salary"));
        }





        pieChart_monthly.setVisibility(View.VISIBLE);
        graph_not_income.setVisibility(View.INVISIBLE);


        pieChart_monthly.animateY(2000, Easing.EaseInOutCubic);
        PieDataSet dataSet = new PieDataSet(values, "Categories");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueFormatter(new PercentFormatter(pieChart_monthly));
        data.setValueTextColor(Color.BLACK);

        pieChart_monthly.setData(data);
        if(borrow == 0 && allowance==0 && bonus == 0 && salary==0)
        {
            categories_in.setVisibility(View.GONE);
            pieChart_monthly.setVisibility(View.INVISIBLE);
            graph_not_income.setVisibility(View.VISIBLE);
        }
    }

    public Float getConverted(ArrayList<ExpenseIncome> arrayList) {
        ExpenseIncome obj1;

        moneyValue_category = 0.00;
        resultVal_category=0.00;
        total_money_euro_category=0.00;
        total_money_default_category=0.00;
        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj1 = arrayList.get(i);
                String obj1_currencyname = obj1.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj1_currencyname);
                String obj1_currencycode = currencyset.getCode();

                to5 = obj1_currencycode;
                moneyValue_category = obj1.getAmount();


                Double rateValue = myDB.getcurrency_rate_date(to5,obj1.getDate());
                resultVal_category = moneyValue_category / rateValue;


                total_money_euro_category = total_money_euro_category + resultVal_category;

                i = i + 1;

            }

           // total_income.setText(total_money_euro_income.toString());
           // return total_money_euro_income.floatValue();

            Float category_float = 0f;
           // total_money_default_expense = total_expense_sum;
            total_money_default_category = total_money_euro_category;

            category_float = Float.parseFloat(String.valueOf(total_money_default_category));
            return category_float;


        } else {
           // total_income.setText("0.00");
            return 0.0f;
        }

    }

    public Float getConvertedCar(ArrayList<ExpenseIncome> arrayList) {
        ExpenseIncome obj1;
        moneyValue_car_category=0.00;
        resultVal_car_category=0.00;
        total_money_euro_car_category=0.00;
        total_money_default_car_category=0.00;
        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj1 = arrayList.get(i);
                String obj1_currencyname = obj1.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj1_currencyname);
                String obj1_currencycode = currencyset.getCode();

                to6 = obj1_currencycode;
                moneyValue_car_category = obj1.getAmount();

                                Double rateValue = myDB.getcurrency_rate_date(to6,obj1.getDate());
                                resultVal_car_category = moneyValue_car_category / rateValue;




                    total_money_euro_car_category = total_money_euro_car_category + resultVal_car_category;


                i = i + 1;

            }

            // total_income.setText(total_money_euro_income.toString());
            // return total_money_euro_income.floatValue();

            Float category_float = 0f;
            // total_money_default_expense = total_expense_sum;
            total_money_default_car_category = total_money_euro_car_category;


            category_float = Float.parseFloat(String.valueOf(total_money_default_car_category));
            return category_float;


        } else {
            // total_income.setText("0.00");
            return 0.0f;
        }

    }
    public Float getConvertedEntertainment(ArrayList<ExpenseIncome> arrayList) {
        ExpenseIncome obj1;
        moneyValue_entertainment_category=0.00;
        resultVal_entertainment_category=0.00;
        total_money_euro_entertainment_category=0.00;
        total_money_default_entertainment_category=0.00;

        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj1 = arrayList.get(i);
                String obj1_currencyname = obj1.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj1_currencyname);
                String obj1_currencycode = currencyset.getCode();

                to7 = obj1_currencycode;
                moneyValue_entertainment_category = obj1.getAmount();


                                Double rateValue = myDB.getcurrency_rate_date(to7,obj1.getDate());
                                resultVal_entertainment_category = moneyValue_entertainment_category / rateValue;


                    total_money_euro_entertainment_category = total_money_euro_entertainment_category + resultVal_entertainment_category;

                i = i + 1;

            }

            // total_income.setText(total_money_euro_income.toString());
            // return total_money_euro_income.floatValue();

            Float category_float = 0f;
            // total_money_default_expense = total_expense_sum;
            total_money_default_entertainment_category = total_money_euro_entertainment_category;

            category_float = Float.parseFloat(String.valueOf(total_money_default_entertainment_category));
            return category_float;


        } else {
            // total_income.setText("0.00");
            return 0.0f;
        }

    }
    public Float getConvertedEducation(ArrayList<ExpenseIncome> arrayList) {
        ExpenseIncome obj1;
        moneyValue_education_category=0.00;
        resultVal_education_category=0.00;
        total_money_euro_education_category=0.00;
        total_money_default_education_category=0.00;

        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj1 = arrayList.get(i);
                String obj1_currencyname = obj1.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj1_currencyname);
                String obj1_currencycode = currencyset.getCode();

                to8 = obj1_currencycode;
                moneyValue_education_category = obj1.getAmount();

                Double rateValue = myDB.getcurrency_rate_date(to8,obj1.getDate());
                resultVal_education_category = moneyValue_education_category / rateValue;

                total_money_euro_education_category = total_money_euro_education_category + resultVal_education_category;


                i = i + 1;

            }

            // total_income.setText(total_money_euro_income.toString());
            // return total_money_euro_income.floatValue();

            Float category_float = 0f;
            // total_money_default_expense = total_expense_sum;
            total_money_default_education_category = total_money_euro_education_category;

            category_float = Float.parseFloat(String.valueOf(total_money_default_education_category));
            return category_float;


        } else {
            // total_income.setText("0.00");
            return 0.0f;
        }
    }



    public Float getConvertedFood(ArrayList<ExpenseIncome> arrayList) {
        ExpenseIncome obj1;
        moneyValue_food_category=0.00;
        resultVal_food_category=0.00;
        total_money_default_food_category=0.00;
        total_money_euro_food_category=0.00;
        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj1 = arrayList.get(i);
                String obj1_currencyname = obj1.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj1_currencyname);
                String obj1_currencycode = currencyset.getCode();

                to9 = obj1_currencycode;
                moneyValue_food_category = obj1.getAmount();

                                Double rateValue = myDB.getcurrency_rate_date(to9,obj1.getDate());
                                resultVal_food_category = moneyValue_food_category / rateValue;


                    total_money_euro_food_category = total_money_euro_food_category + resultVal_food_category;

                i = i + 1;

            }

            // total_income.setText(total_money_euro_income.toString());
            // return total_money_euro_income.floatValue();

            Float category_float = 0f;
            // total_money_default_expense = total_expense_sum;
            total_money_default_food_category = total_money_euro_food_category;


            category_float = Float.parseFloat(String.valueOf(total_money_default_food_category));
            return category_float;


        } else {
            // total_income.setText("0.00");
            return 0.0f;
        }

    }

    public Float getConvertedGrocery(ArrayList<ExpenseIncome> arrayList) {
        ExpenseIncome obj1;
        moneyValue_grocery_category=0.00;
        resultVal_grocery_category=0.00;
        total_money_euro_grocery_category=0.00;
        total_money_default_grocery_category=0.00;

        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj1 = arrayList.get(i);
                String obj1_currencyname = obj1.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj1_currencyname);
                String obj1_currencycode = currencyset.getCode();

                to10 = obj1_currencycode;
                moneyValue_grocery_category = obj1.getAmount();

                                Double rateValue = myDB.getcurrency_rate_date(to10,obj1.getDate());
                                resultVal_grocery_category = moneyValue_grocery_category / rateValue;

                    total_money_euro_grocery_category = total_money_euro_grocery_category + resultVal_grocery_category;


                i = i + 1;

            }

            // total_income.setText(total_money_euro_income.toString());
            // return total_money_euro_income.floatValue();

            Float category_float = 0f;
            // total_money_default_expense = total_expense_sum;
            total_money_default_grocery_category = total_money_euro_grocery_category;

            category_float = Float.parseFloat(String.valueOf(total_money_default_grocery_category));
            return category_float;


        } else {
            // total_income.setText("0.00");
            return 0.0f;
        }
    }

        public Float getConvertedHealth(ArrayList<ExpenseIncome> arrayList){
            ExpenseIncome obj1;
            moneyValue_health_category=0.00;
            resultVal_health_category=0.00;
            total_money_euro_health_category=0.00;
            total_money_default_health_category=0.00;

            int l;
            int i = 0;
            l = arrayList.size();
            if (arrayList != null) {
                while (i < l) {
                    obj1 = arrayList.get(i);
                    String obj1_currencyname = obj1.getCurrencyname();
                    ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj1_currencyname);
                    String obj1_currencycode = currencyset.getCode();

                    to11 = obj1_currencycode;
                    moneyValue_health_category = obj1.getAmount();


                                    Double rateValue = myDB.getcurrency_rate_date(to11,obj1.getDate());
                                    resultVal_health_category = moneyValue_health_category / rateValue;

                        total_money_euro_health_category = total_money_euro_health_category + resultVal_health_category;

                    i = i + 1;

                }

                // total_income.setText(total_money_euro_income.toString());
                // return total_money_euro_income.floatValue();

                Float category_float = 0f;
                // total_money_default_expense = total_expense_sum;
                total_money_default_health_category = total_money_euro_health_category;

                    category_float = Float.parseFloat(String.valueOf(total_money_default_health_category));
                    return category_float;


            } else {
                // total_income.setText("0.00");
                return 0.0f;
            }

    }

    public Float getConvertedTravel(ArrayList<ExpenseIncome> arrayList){
        ExpenseIncome obj1;
        moneyValue_travel_category=0.00;
        resultVal_travel_category=0.00;
        total_money_euro_travel_category=0.00;
        total_money_default_travel_category=0.00;
        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj1 = arrayList.get(i);
                String obj1_currencyname = obj1.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj1_currencyname);
                String obj1_currencycode = currencyset.getCode();

                to12 = obj1_currencycode;
                moneyValue_travel_category = obj1.getAmount();

                Double rateValue = myDB.getcurrency_rate_date(to12,obj1.getDate());
                resultVal_travel_category = moneyValue_travel_category / rateValue;


                total_money_euro_travel_category = total_money_euro_travel_category + resultVal_travel_category;


                i = i + 1;

            }

            // total_income.setText(total_money_euro_income.toString());
            // return total_money_euro_income.floatValue();

            Float category_float = 0f;
            // total_money_default_expense = total_expense_sum;
            total_money_default_travel_category = total_money_euro_travel_category;

                category_float = Float.parseFloat(String.valueOf(total_money_default_travel_category));
                return category_float;

        }
         else {
            // total_income.setText("0.00");
            return 0.0f;
        }

    }
    public Float getConvertedSalary(ArrayList<ExpenseIncome> arrayList) {
        ExpenseIncome obj1;
        double moneyValue_salary_category=0.00;
        double resultVal_salary_category=0.00;
        double total_money_euro_salary_category=0.00;
        double total_money_default_salary_category=0.00;

        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj1 = arrayList.get(i);
                String obj1_currencyname = obj1.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj1_currencyname);
                String obj1_currencycode = currencyset.getCode();

                to7 = obj1_currencycode;
                moneyValue_salary_category = obj1.getAmount();


                Double rateValue = myDB.getcurrency_rate_date(to7,obj1.getDate());
                resultVal_salary_category = moneyValue_salary_category / rateValue;


                total_money_euro_salary_category = total_money_euro_salary_category + resultVal_salary_category;

                i = i + 1;

            }

            // total_income.setText(total_money_euro_income.toString());
            // return total_money_euro_income.floatValue();

            Float category_float = 0f;
            // total_money_default_expense = total_expense_sum;
            total_money_default_salary_category = total_money_euro_salary_category;

            category_float = Float.parseFloat(String.valueOf(total_money_default_salary_category));
            return category_float;


        } else {
            // total_income.setText("0.00");
            return 0.0f;
        }

    }
    public Float getConvertedBorrow(ArrayList<ExpenseIncome> arrayList) {
        ExpenseIncome obj1;
        double moneyValue_borrow_category=0.00;
        double resultVal_borrow_category=0.00;
        double total_money_euro_borrow_category=0.00;
        double total_money_default_borrow_category=0.00;

        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj1 = arrayList.get(i);
                String obj1_currencyname = obj1.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj1_currencyname);
                String obj1_currencycode = currencyset.getCode();

                to7 = obj1_currencycode;
                moneyValue_borrow_category = obj1.getAmount();


                Double rateValue = myDB.getcurrency_rate_date(to7,obj1.getDate());
                resultVal_borrow_category = moneyValue_borrow_category / rateValue;


                total_money_euro_borrow_category = total_money_euro_borrow_category + resultVal_borrow_category;

                i = i + 1;

            }

            // total_income.setText(total_money_euro_income.toString());
            // return total_money_euro_income.floatValue();

            Float category_float = 0f;
            // total_money_default_expense = total_expense_sum;
            total_money_default_borrow_category = total_money_euro_borrow_category;

            category_float = Float.parseFloat(String.valueOf(total_money_default_borrow_category));
            return category_float;


        } else {
            // total_income.setText("0.00");
            return 0.0f;
        }

    }

    public Float getConvertedAllowance(ArrayList<ExpenseIncome> arrayList) {
        ExpenseIncome obj1;
        double moneyValue_allowance_category=0.00;
        double resultVal_allowance_category=0.00;
        double total_money_euro_allowance_category=0.00;
        double total_money_default_allowance_category=0.00;

        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj1 = arrayList.get(i);
                String obj1_currencyname = obj1.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj1_currencyname);
                String obj1_currencycode = currencyset.getCode();

                to7 = obj1_currencycode;
                moneyValue_allowance_category = obj1.getAmount();


                Double rateValue = myDB.getcurrency_rate_date(to7,obj1.getDate());
                resultVal_allowance_category = moneyValue_allowance_category / rateValue;


                total_money_euro_allowance_category = total_money_euro_allowance_category + resultVal_allowance_category;

                i = i + 1;

            }

            // total_income.setText(total_money_euro_income.toString());
            // return total_money_euro_income.floatValue();

            Float category_float = 0f;
            // total_money_default_expense = total_expense_sum;
            total_money_default_allowance_category = total_money_euro_allowance_category;

            category_float = Float.parseFloat(String.valueOf(total_money_default_allowance_category));
            return category_float;


        } else {
            // total_income.setText("0.00");
            return 0.0f;
        }

    }
    public Float getConvertedBonus(ArrayList<ExpenseIncome> arrayList) {
        ExpenseIncome obj1;
        double moneyValue_bonus_category=0.00;
        double resultVal_bonus_category=0.00;
        double total_money_euro_bonus_category=0.00;
        double total_money_default_bonus_category=0.00;

        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj1 = arrayList.get(i);
                String obj1_currencyname = obj1.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj1_currencyname);
                String obj1_currencycode = currencyset.getCode();

                to7 = obj1_currencycode;
                moneyValue_bonus_category = obj1.getAmount();


                Double rateValue = myDB.getcurrency_rate_date(to7,obj1.getDate());
                resultVal_bonus_category = moneyValue_bonus_category / rateValue;


                total_money_euro_bonus_category = total_money_euro_bonus_category + resultVal_bonus_category;

                i = i + 1;

            }

            // total_income.setText(total_money_euro_income.toString());
            // return total_money_euro_income.floatValue();

            Float category_float = 0f;
            // total_money_default_expense = total_expense_sum;
            total_money_default_bonus_category = total_money_euro_bonus_category;

            category_float = Float.parseFloat(String.valueOf(total_money_default_bonus_category));
            return category_float;


        } else {
            // total_income.setText("0.00");
            return 0.0f;
        }

    }



    public Float getConvertedOther(ArrayList<ExpenseIncome> arrayList){
        ExpenseIncome obj1;
        moneyValue_other_category=0.00;
        resultVal_other_category=0.00;
        total_money_euro_other_category=0.00;
        total_money_default_other_category=0.00;
        int l;
        int i = 0;
        l = arrayList.size();
        if (arrayList != null) {
            while (i < l) {
                obj1 = arrayList.get(i);
                String obj1_currencyname = obj1.getCurrencyname();
                ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(obj1_currencyname);
                String obj1_currencycode = currencyset.getCode();

                to13 = obj1_currencycode;
                moneyValue_other_category = obj1.getAmount();

                                Double rateValue = myDB.getcurrency_rate_date(to13,obj1.getDate());
                                resultVal_other_category = moneyValue_other_category / rateValue;


                    total_money_euro_other_category = total_money_euro_other_category + resultVal_other_category;

                i = i + 1;

            }

            // total_income.setText(total_money_euro_income.toString());
            // return total_money_euro_income.floatValue();

            Float category_float = 0f;
            // total_money_default_expense = total_expense_sum;
            total_money_default_other_category = total_money_euro_other_category;

            category_float = Float.parseFloat(String.valueOf(total_money_default_other_category));
            return category_float;


        } else {
            // total_income.setText("0.00");
            return 0.0f;
        }

    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent settings = new Intent(this, settings.class);
            startActivity(settings);
            finish();
        } else if (id == R.id.nav_gallery) {

            Intent budget = new Intent(this, budget_planning.class);
            startActivity(budget);
            finish();
        }

        else if (id == R.id.nav_change_profile) {
            Intent contact = new Intent(this, Change_Profile.class);
            startActivity(contact);
            finish();
        }

        else if (id == R.id.nav_export){

            Cursor exportData = myDB.exportingData(user[0],user[1]);
            StringBuilder data = new StringBuilder();
            data.append("ID,Account,Amount,Category,Date,Time,Notes,IncomeExpense,Checked,Currency,Email,Profile,Contact Name,Contact Number,Contact Email");

            while(exportData.moveToNext())
            {
                String exportNotes = exportData.getString(6);
                CharSequence Edit4 = Html.fromHtml(exportNotes,1);
                Log.d("notes"," "+Edit4);
                String output = Edit4.toString().trim().replaceAll("\\s+", " ");
                data.append("\n"+String.valueOf(exportData.getInt(0))+","+String.valueOf(exportData.getString(1))+","+String.valueOf(exportData.getDouble(2))+","+String.valueOf(exportData.getString(3))+","+String.valueOf(exportData.getString(4))+","+String.valueOf(exportData.getString(5))+","+output+","+String.valueOf(exportData.getString(7))+","+String.valueOf(exportData.getString(8))+","+String.valueOf(exportData.getString(9))+","+String.valueOf(exportData.getString(10))+","+String.valueOf(exportData.getString(11))+","+String.valueOf(exportData.getString(12))+","+String.valueOf(exportData.getString(13))+","+String.valueOf(exportData.getString(14)));
            }




            try {
                FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
                out.write(data.toString().getBytes());
                out.close();

                Context context = getApplicationContext();
                File fileLocation = new File(getFilesDir(),"data.csv");
                Uri path = FileProvider.getUriForFile(context,"com.example.properapp.fileprovider",fileLocation);
                Intent fileIntent = new Intent(Intent.ACTION_SEND);
                fileIntent.setType("text/csv");
                fileIntent.putExtra(Intent.EXTRA_SUBJECT,"Data");
                fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                startActivity(Intent.createChooser(fileIntent,"Send Email"));


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if (id== R.id.nav_import)
        {
            int PERMISSION_ALL = 1;
            String[] PERMISSIONS = {
                    android.Manifest.permission.READ_CONTACTS,
                    android.Manifest.permission.WRITE_CONTACTS,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,

            };
            if (! hasPermissions(MainActivity.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION_ALL);
            }
            String folderPath = Environment.getExternalStorageDirectory()+"/pathTo/folder";

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            Uri myUri = Uri.parse(folderPath);
            intent.setDataAndType(myUri , "file/*");
            startActivityForResult(intent,PICK_CONTACT);
           /* Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, PICK_CONTACT);

            */
        }

        else if(id == R.id.nav_contact)
        {
            TextView txtclose;
            getMyDialog.setContentView(R.layout.activity_contact_us);
            txtclose = (TextView)getMyDialog.findViewById(R.id.text_close);
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getMyDialog.dismiss();
                }
            });
            getMyDialog.show();
            //startActivity(new Intent(MainActivity.this,ContactUsActivity.class));
           // finish();
        }

        else if(id == R.id.nav_help)
        {
            Intent help = new Intent(this, Help_menu.class);
            startActivity(help);
        }

        else if (id == R.id.nav_logout) {
                    FirebaseAuth.getInstance().signOut();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("key", 0);
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
        }

        else  {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.draw_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.Nav_Dashboard:

                            startActivity(new Intent(MainActivity.this,MainActivity.class));
                            finish();

                            break;
                        case R.id.Nav_List:
                            Intent show1 = new Intent(MainActivity.this, ListView.class);
                            startActivity(show1);
                            finish();

                            break;

                        case R.id.Nav_Add:
                            Intent show2 = new Intent(MainActivity.this, ListView_Checked.class);
                            startActivity(show2);
                            finish();
                            break;
                    }
                    return true;
                }
            };

    public void setcurrencyrate(final String date)
    {
        Thread thread99 = new Thread(new Runnable() {

            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    try {
                        String mainUrl = "http://data.fixer.io/api/latest?access_key=1c28ba134bb0a7537ffd573c16f372be&format=1";
                        String updatedUrl = mainUrl;
                        URL url = new URL(updatedUrl);


                        urlConnection = (HttpURLConnection) url.openConnection();

                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader inReader = new BufferedReader(new InputStreamReader(in));
                        String inputLine = "";
                        String fullStr = "";
                        while ((inputLine = inReader.readLine()) != null) {
                            fullStr += inputLine;
                        }

                        JSONObject jsonObj = new JSONObject(fullStr);
                        JSONObject result = jsonObj.getJSONObject("rates");


                        ExtendedCurrency[] currencies = ExtendedCurrency.CURRENCIES;
                        int i=0,l=0;
                        l= currencies.length;
                        String[] codes = new String[l];
                        while(i<l)
                        {
                            codes[i]= currencies[i].getCode();
                            i=i+1;
                        }

                        for(i=0;i<l;i++) {
                            Double rateValue = Double.valueOf(result.getString(codes[i]));
                            boolean inserted= myDB.insertcurrency_rate_date(codes[i],rateValue, date);
                        }


                    } finally {
                        if (urlConnection != null)
                            urlConnection.disconnect();


                    }


                } catch (NumberFormatException e) {
                    //TODO: Alertbox ekle

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        double result = myDB.getcurrency_rate_date("EUR",date.toString());
        if(result == 0) {
            thread99.start();
            try {
                thread99.join();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *reads the excel file columns then rows. Stores data as ExcelUploadData object
     * @return
     */
    private void readExcelData(String filePath) {
        //  Log.d(TAG, "readExcelData: Reading Excel File.");

        //decarle input file
        File inputFile = new File(filePath);

        try {
            InputStream inputStream = new FileInputStream(inputFile);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowsCount = sheet.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
            StringBuilder sb = new StringBuilder();

            //outter loop, loops through rows
            for (int r = 1; r < rowsCount; r++) {
                Row row = sheet.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();
                //inner loop, loops through columns
                for (int c = 0; c < cellsCount; c++) {
                    //handles if there are to many columns on the excel sheet.
                    if(c>14){
                        Log.e(TAG, "readExcelData: ERROR. Excel File Format is incorrect! " );
                        toastMessage("ERROR: Excel File Format is incorrect!");
                        break;
                    }else{
                        String value = getCellAsString(row, c, formulaEvaluator);
                        String cellInfo = "r:" + r + "; c:" + c + "; v:" + value;
                        Log.d(TAG, "readExcelData: Data from row: " + cellInfo);
                        sb.append(value + ", ");
                    }
                }
                sb.append(":");
            }
            Log.d(TAG, "readExcelData: STRINGBUILDER: " + sb.toString());//

            parseStringBuilder(sb);

        }catch (FileNotFoundException e) {
            Log.e(TAG, "readExcelData: FileNotFoundException. " + e.getMessage() );
            Toast.makeText(MainActivity.this, " file not found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e(TAG, "readExcelData: Error reading inputstream. " + e.getMessage() );
        }
    }

    /**
     * Method for parsing imported data and storing in ArrayList<XYValue>
     */
    public void parseStringBuilder(StringBuilder mStringBuilder){
        Toast.makeText(MainActivity.this, " parse", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "parseStringBuilder: Started parsing.");

        // splits the sb into rows.
        String[] rows = mStringBuilder.toString().split(":");
        int flag=0;
        //Add to the ArrayList<XYValue> row by row
        for(int i=0; i<rows.length; i++) {
            //Split the columns of the rows
            String[] columns = rows[i].split(",");

            //use try catch to make sure there are no "" that try to parse into doubles.
            try{
                String a = columns[0].toString();
                String b = columns[1].toString();
                String c = columns[2].toString();
                String d = columns[3].toString();
                String e = columns[4].toString();
                String f = columns[5].toString();
                String g = columns[6].toString();
                String h = columns[7].toString();
                String i2 = columns[8].toString();
                String j = columns[9].toString();
                String k = columns[10].toString();
                String l = columns[11].toString();
                String m = columns[12].toString();
                String n = columns[13].toString();
                String o = columns[14].toString();

                String cellInfo = "(row): (" + a + "," + b + ","+c + "," + d + ","+e + "," + f + ","+g +
                        "," + h + ","+i2 + "," + j + ","+k + "," + l + ","+m + "," + n + ","+o +   ")";
                Log.d(TAG, "ParseStringBuilder: Data from row: " + cellInfo);

                //add the the uploadData ArrayList
                // uploadData.add(new expenseincome2(Integer.parseInt(a),b,Double.parseDouble(c),d,e,f,g,h,i2,j,k,l,m,n,o));
                boolean inserted=false;
                boolean insert=false;
                setcurrencyrate(e.trim().toString());
                if(myDB.getcurrency_rate_date("EUR",e.trim()) != 0) {
                    insert =myDB.insertuserprofile_details(k.trim(),l.trim());
                    inserted = myDB.insertData(b.trim(), Double.parseDouble(c), d.trim(), e.trim(), f.trim(), g.trim(), h.trim(), i2.trim(), j.trim(), k.trim(), l.trim(), m.trim(), n.trim(), o.trim());

                }if(inserted){ Toast.makeText(MainActivity.this, " inserted", Toast.LENGTH_SHORT).show();}

                if(inserted){flag=1;}

            }catch (NumberFormatException e){

                Log.e(TAG, "parseStringBuilder: NumberFormatException: " + e.getMessage());

            }
        }
        if(flag ==1){
            Toast.makeText(getApplicationContext(), " inserted", Toast.LENGTH_SHORT).show();

            Intent show1 = new Intent(MainActivity.this, ListView.class);
            startActivity(show1);
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(), "not inserted", Toast.LENGTH_SHORT).show();

            Intent show1 = new Intent(MainActivity.this, ListView.class);
            startActivity(show1);
            finish();
        }
        printDataToLog();
    }

    private void printDataToLog() {
        Log.d(TAG, "printDataToLog: Printing data to log...");

        for(int i = 0; i< uploadData.size(); i++){

            Integer a = uploadData.get(i).getID();
            String b = uploadData.get(i).getAccount();
            Double c= uploadData.get(i).getAmount();
            String d = uploadData.get(i).getCategory();
            String e = uploadData.get(i).getDate();
            String f = uploadData.get(i).getTime();
            String g = uploadData.get(i).getNotes();
            String h = uploadData.get(i).getIncomeexpense();
            String i2 = uploadData.get(i).getChecked();
            String j = uploadData.get(i).getCurrencyname();
            String k = uploadData.get(i).getEmail();
            String l = uploadData.get(i).getProfile();
            String m = uploadData.get(i).getContactname();
            String n = uploadData.get(i).getContactphonenumbers();
            String o = uploadData.get(i).getContactemails();
            Log.d(TAG, "printDataToLog: (x,y): (" + a + "," + b + ","+c + "," + d + ","+e + "," + f + ","+g +
                    "," + h + ","+i2 + "," + j + ","+k + "," + l + ","+m + "," + n + ","+o + ")");
        }
    }

    /**
     * Returns the cell as a string from the excel file
     * @param row
     * @param c
     * @param formulaEvaluator
     * @return
     */
    private String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        try {
            Cell cell = row.getCell(c);
            CellValue cellValue = formulaEvaluator.evaluate(cell);
            switch (cellValue.getCellType()) {
                case Cell.CELL_TYPE_BOOLEAN:
                    value = ""+cellValue.getBooleanValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cellValue.getNumberValue();
                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                        double date = cellValue.getNumberValue();
                        SimpleDateFormat formatter =
                                new SimpleDateFormat("MM/dd/yy");
                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
                    } else {
                        value = ""+numericValue;
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    value = ""+cellValue.getStringValue();
                    break;
                default:
            }
        } catch (NullPointerException e) {

            // Log.e(TAG, "getCellAsString: NullPointerException: " + e.getMessage() );
        }
        return value;
    }

    private void checkInternalStorage() {
        // Log.d(TAG, "checkInternalStorage: Started.");
        try{
            if (!Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                toastMessage("No SD card found.");
            }
            else{
                // Locate the image folder in your SD Car;d
                file = new File(pathHistory.get(count));
                Log.d(TAG, "checkInternalStorage: directory path: " + pathHistory.get(count));
            }

            listFile = file.listFiles();

            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];

            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
            }

            for (int i = 0; i < listFile.length; i++)
            {
                Log.d("Files", "FileName:" + listFile[i].getName());
            }



        }catch(NullPointerException e){
            //Log.e(TAG, "checkInternalStorage: NULLPOINTEREXCEPTION " + e.getMessage() );
        }
    }

    private void checkFilePermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        }else{
            //Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = data.getData();
                    String path = getRealPathFromURI_API19(MainActivity.this, uri);
                    // String path = uri.getPath();
                    //Toast.makeText(import_activity.this, path, Toast.LENGTH_LONG).show();
                    readExcelData(path);

                }
        }

    }


    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API19(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }







    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }



}