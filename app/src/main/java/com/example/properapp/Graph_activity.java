package com.example.properapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Graph_activity extends AppCompatActivity {
PieChart pieChart;
DatabaseHelper myDB;
float shopping;
float car;
float entertainment;
float education;
float food;
float grocery;
float health;
float travel;
float other;
String[] user = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_activity);
        pieChart = (PieChart)findViewById(R.id.graph);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        myDB = new DatabaseHelper(this);
       user =myDB.get_currentuser();
        shopping = myDB.getAmount_Category_Expense("Shopping",user[0],user[1]);
        car = myDB.getAmount_Category_Expense("Car",user[0],user[1]);
        entertainment = myDB.getAmount_Category_Expense("Entertainment",user[0],user[1]);
        education = myDB.getAmount_Category_Expense("Education",user[0],user[1]);
        food = myDB.getAmount_Category_Expense("Food n Drink",user[0],user[1]);
        grocery = myDB.getAmount_Category_Expense("Grocery",user[0],user[1]);
        health = myDB.getAmount_Category_Expense("Health",user[0],user[1]);
        travel = myDB.getAmount_Category_Expense("Travel",user[0],user[1]);
        other = myDB.getAmount_Category_Expense("Other",user[0],user[1]);



        ArrayList<PieEntry> values = new ArrayList<>();
        if(shopping > 0)
        {
            values.add(new PieEntry(shopping,"Shopping"));

        }
        if(car > 0)
        {
            values.add(new PieEntry(car,"Car"));

        }
        if(entertainment > 0)
        {
            values.add(new PieEntry(entertainment,"Entertainment"));
        }
        if(education > 0)
        {
            values.add(new PieEntry(education,"Education"));
        }
        if(food > 0)
        {
            values.add(new PieEntry(food,"Food n Drink"));

        }
        if(grocery > 0)
        {
            values.add(new PieEntry(grocery,"Grocery"));
        }
        if(health > 0)
        {
            values.add(new PieEntry(health,"Health"));
        }
        if(travel > 0)
        {
            values.add(new PieEntry(travel,"Travel"));

        }
        if(other > 0)
        {
            values.add(new PieEntry(other,"Other"));
        }


        pieChart.animateY(2000, Easing.EaseInOutCubic);
        PieDataSet dataSet = new PieDataSet(values,"Categories");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);

    }
}
