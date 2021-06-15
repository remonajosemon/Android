package com.example.properapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;


public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "expenseincome8.db", null, 1);
        //

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ExpenseInfo(ID INTEGER PRIMARY KEY AUTOINCREMENT ,ACCOUNT TEXT ,AMOUNT TEXT,CATEGORY TEXT,DATE TEXT,TIME TEXT,NOTES TEXT,INCOME_EXPENSE TEXT,CHECKED_DATA TEXT, CURRENCY_NAME TEXT,EMAIL_1 TEXT,PROFILE_1 TEXT,CONTACT_NAME TEXT,CONTACT_NUMBERS TEXT, CONTACT_EMAILS TEXT)");
        db.execSQL("CREATE TABLE SettingsInfo(ID2 INTEGER PRIMARY KEY AUTOINCREMENT,CURRENCY TEXT,EMAIL_2 TEXT,PROFILE_2 TEXT)");
        db.execSQL("CREATE TABLE BudgetInfo(ID3 INTEGER PRIMARY KEY AUTOINCREMENT,SHOPPING TEXT, CAR TEXT,EDUCATION TEXT, ENTERTAINMENT TEXT, FOOD_N_DRINK TEXT, GROCERY TEXT, HEALTH TEXT, TRAVEL TEXT, OTHER TEXT,EMAIL_3 TEXT,PROFILE_3 TEXT)");
        db.execSQL("CREATE TABLE CurrentUser(ID4 INTEGER PRIMARY KEY AUTOINCREMENT,EMAIL_0 TEXT,PROFILE_0 TEXT)");
        db.execSQL("CREATE TABLE CurrencyRateDate(ID6 INTEGER PRIMARY KEY AUTOINCREMENT,CODE_1 TEXT,RATE_1 TEXT,DATE_1 TEXT)");
        db.execSQL("CREATE TABLE Contacts(ID9 INTEGER PRIMARY KEY AUTOINCREMENT,CONTACT_NAME TEXT, CONTACT_NUMBER TEXT)");
        db.execSQL("CREATE TABLE Userprofiles(ID10 INTEGER PRIMARY KEY AUTOINCREMENT,USER_EMAIL TEXT, USER_PROFILE TEXT)");
        db.execSQL("CREATE TABLE Total(ID11 INTEGER PRIMARY KEY AUTOINCREMENT,MONTH TEXT, YEAR TEXT,EMAIL_11 TEXT, PROFILE_11 TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS ExpenseInfo");
        db.execSQL("DROP TABLE IF EXISTS SettingsInfo");
        db.execSQL("DROP TABLE IF EXISTS BudgetInfo");
        db.execSQL("DROP TABLE IF EXISTS CurrentUser");
        db.execSQL("DROP TABLE IF EXISTS CurrencyRateDate");
        db.execSQL("DROP TABLE IF EXISTS Contacts");
        db.execSQL("DROP TABLE IF EXISTS Userprofiles");
        db.execSQL("DROP TABLE IF EXISTS Total");
        onCreate(db);

    }
    public boolean insertTotal(String email, String profile, Date date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        long result = -1;

        Date dat = date;
        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
        String formattedDate = df.format(dat);
        final String[] tokens = formattedDate.split(Pattern.quote("/"));

        contentValues.put("EMAIL_11",email);
        contentValues.put("PROFILE_11", profile);
        contentValues.put("MONTH",tokens[1]);
        contentValues.put("YEAR", tokens[2]);


            result = db.insert("Total", null, contentValues);



        if (result == -1) return false;
        else return true;
    }
    public int getTotal_Month(String email, String profile) {
        SQLiteDatabase db = this.getWritableDatabase();

  Cursor cursor = db.rawQuery("SELECT MONTH FROM Total WHERE EMAIL_11 LIKE " + "'"+ email+"'"+"AND "+"PROFILE_11 LIKE " + "'"+ profile+"'", null);

        int monthcount = 0;
        int flag = 0;
        while (cursor.moveToNext()) {
            String month =cursor.getString(0);
            monthcount= monthcount+1;
            flag=1;
        }

        return monthcount;

    }

    public int getTotal_Year(String email, String profile) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT YEAR FROM Total WHERE EMAIL_11 LIKE " + "'"+ email+"'"+"AND "+"PROFILE_11 LIKE " + "'"+ profile+"'", null);

        int yearcount = 0;
         ArrayList<String> prevyear= new ArrayList<>();

        while (cursor.moveToNext()) {
            int i=0;
            int flag = 0;
            String year =cursor.getString(0);
           int l= prevyear.size();
           while(i<l)
           {
               if(year.equals(prevyear.get(i)))
               {
                   flag=1;
               }
               i=i+1;

           }
           if(flag==0)
           {
               yearcount=yearcount+1;
           }
            prevyear.add(year);
        }

        return yearcount;
    }

    public boolean insertuserprofile_details(String email,String profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        long result = -1;
        int flag1 =0;
        int flag2 =0;

            contentValues.put("USER_EMAIL",email);
            contentValues.put("USER_PROFILE", profile);

        Cursor cursor = db.rawQuery("SELECT USER_PROFILE FROM Userprofiles WHERE USER_EMAIL LIKE " + "'"+ email+"'", null);
        while (cursor.moveToNext()) {
            String profile1 =cursor.getString(0);
            if(profile1.equals("Primary") )
            {
                flag1=1;
            }
            if( profile1.equals(profile))
            {
                flag2=1;
            }

        }
            if (flag1==0 || (flag1==1 && profile != "Primary" && flag2==0))
        {
            result = db.insert("Userprofiles", null, contentValues);
        }


        if (result == -1) return false;
        else return true;
    }

    public ArrayList<String> getuserprofile_details(String email) {
        SQLiteDatabase db = this.getWritableDatabase();



        Cursor cursor = db.rawQuery("SELECT USER_PROFILE FROM Userprofiles WHERE USER_EMAIL LIKE " + "'"+ email+"'", null);

        ArrayList<String> arrayList = new ArrayList<>();
        int flag = 0;
        while (cursor.moveToNext()) {
            String profile =cursor.getString(0);
            arrayList.add(profile);
            flag=1;
        }

    return arrayList;

    }
    public boolean deleteuserprofile_details(String email,String profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        String id="";
        long result =-1;

        Cursor cursor = db.rawQuery("SELECT ID10 FROM Userprofiles WHERE USER_EMAIL LIKE " + "'"+ email+"'"+"AND "+"USER_PROFILE LIKE " + "'"+ profile+"'", null);


        while (cursor.moveToNext()) {
            id =cursor.getString(0);

        }

        if(!id.equals("")) {

            String TABLE_NAME = "Userprofiles";

            result = db.delete("Userprofiles", "ID10 =?", new String[]{id});
            db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");
        }

        if (result == -1) return false;
        else return true;



    }


    public boolean insertcurrency_rate_date(String code,Double rate,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CODE_1", code);
        contentValues.put("RATE_1", rate);
        contentValues.put("DATE_1", date);
        String codes =null;
        int id=0;
        long result=-1;
        Cursor cursor = db.rawQuery("SELECT * FROM CurrencyRateDate WHERE CODE_1 LIKE "+ "'"+ code+"'"+" AND DATE_1 LIKE"+ "'"+ date+"'", null);


        while (cursor.moveToNext()) {
            id= cursor.getInt(0);
            codes = cursor.getString(1);
        }
        if (id==0) {
            result = db.insert("CurrencyRateDate", null, contentValues);
        } else {
            result = db.update("CurrencyRateDate", contentValues, "CODE_1 =?", new String[]{code});
        }

        if (result == -1) return false;
        else return true;
    }

    public double getcurrency_rate_date(String code ,String date) {
        SQLiteDatabase db = this.getWritableDatabase();



        Cursor cursor = db.rawQuery("SELECT RATE_1 FROM CurrencyRateDate WHERE CODE_1 LIKE "+ "'"+ code+"'"+" AND DATE_1 LIKE"+ "'"+ date+"'", null);

        Double result = -1.00;
        while (cursor.moveToNext()) {
            result = cursor.getDouble(0);
        }

        if (result == -1) return 0;
        else return result;
    }

    public boolean insert_currentuser(String email,String profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL_0", email);
        contentValues.put("PROFILE_0", profile);
        Cursor cursor = db.rawQuery("SELECT * FROM CurrentUser", null);
        int id = 0;
        String login = "0";
        String Date = "0";
        long result = -1;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);

        }
        if (id == 0) {
            result = db.insert("CurrentUser", null, contentValues);
        } else {
            result = db.update("CurrentUser", contentValues, "ID4 =?", new String[]{"1"});
        }


        if (result == -1) return false;
        else return true;
    }

    public String[] get_currentuser() {

        SQLiteDatabase db = this.getReadableDatabase();
        String email = null;
        String profile = null;
        String[] a= new String[2];
        Cursor cursor = db.rawQuery("SELECT * FROM CurrentUser WHERE ID4 LIKE" + " 1", null);

        while (cursor.moveToNext()) {
            email = cursor.getString(1);
            profile = cursor.getString(2);


            a[0]=email;
            a[1]=profile;

        }
        return a;
    }

    public boolean insertDataBudget(Double shopping, Double car, Double education, Double entertainment, Double foodndrink, Double grocery, Double health, Double travel, Double other, String email,String profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SHOPPING", shopping);
        contentValues.put("CAR", car);
        contentValues.put("EDUCATION", education);
        contentValues.put("ENTERTAINMENT", entertainment);
        contentValues.put("FOOD_N_DRINK", foodndrink);
        contentValues.put("GROCERY", grocery);
        contentValues.put("HEALTH", health);
        contentValues.put("TRAVEL", travel);
        contentValues.put("OTHER", other);
        contentValues.put("EMAIL_3", email);
        contentValues.put("PROFILE_3", profile);
        Cursor cursor = db.rawQuery("SELECT * FROM BudgetInfo ", null);
        int id = 0,flag=0;
        String username=null;
        long result = -1;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            username = cursor.getString(10);
            if(username.equals(email))
            {
                flag=1;
            }
        }

       if (flag ==1){
            result = db.update("BudgetInfo",
                    contentValues,
                    "EMAIL_3" + " = ? AND " + "PROFILE_3" + " = ?",
                    new String[]{email, profile});
        }
        else {
           result = db.insert("BudgetInfo", null, contentValues);
        }


        if (result == -1) return false;
        else return true;
    }

    public Double[] getDataBudget(String email,String profile) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM BudgetInfo WHERE EMAIL_3 LIKE" + "'"+ email+"' AND  PROFILE_3 LIKE" + "'"+ profile+"'", null);

        Double[] budget = new Double[9];

        int flag = 0;
        while (cursor.moveToNext()) {
            flag = 1;
            budget[0] = cursor.getDouble(1);
            budget[1] = cursor.getDouble(2);
            budget[2] = cursor.getDouble(3);
            budget[3] = cursor.getDouble(4);
            budget[4] = cursor.getDouble(5);
            budget[5] = cursor.getDouble(6);
            budget[6] = cursor.getDouble(7);
            budget[7] = cursor.getDouble(8);
            budget[8] = cursor.getDouble(9);

        }
        if (flag == 1) {
            return budget;
        } else {
            return null;
        }
    }


    public boolean insertDataSettings(String currency,String email,String profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CURRENCY", currency);
        contentValues.put("EMAIL_2", email);
        contentValues.put("PROFILE_2", profile);
        Cursor cursor = db.rawQuery("SELECT * FROM SettingsInfo", null);
        int id = 0,flag=0;
        String useremail =null;
        String userprofile =null;
        long result = -1;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
            useremail = cursor.getString(2);
            userprofile = cursor.getString(3);
            if(useremail.equals(email) && userprofile.equals(profile))
            {
                flag=1;
            }
        }
        if (flag ==1){
            result = db.update("SettingsInfo",
                    contentValues,
                    "EMAIL_2" + " = ? AND " + "PROFILE_2" + " = ?",
                    new String[]{email, profile});
        }
        else {
            result = db.insert("SettingsInfo", null, contentValues);
        }


        if (result == -1) return false;
        else return true;
    }

    public String getDataSettings(String email,String profile) {

        SQLiteDatabase db = this.getReadableDatabase();
        String currencyname = null;
        Cursor cursor = db.rawQuery("SELECT * FROM SettingsInfo WHERE EMAIL_2 LIKE" + "'"+ email+"'AND  PROFILE_2 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            currencyname = cursor.getString(1);

        }
        return currencyname;
    }

    public boolean insertData(String account, Double amount, String category, String date, String time, String notes, String incomeexpense, String checked, String currency,String email,String profile,String contactname,String contactphone,String contactemail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("ACCOUNT", account);
        contentValues.put("AMOUNT", amount);
        contentValues.put("CATEGORY", category);
        contentValues.put("DATE", date);
        contentValues.put("TIME", time);
        contentValues.put("NOTES", notes);
        contentValues.put("INCOME_EXPENSE", incomeexpense);
        contentValues.put("CHECKED_DATA", checked);
        contentValues.put("CURRENCY_NAME", currency);
        contentValues.put("EMAIL_1", email);
        contentValues.put("PROFILE_1", profile);
        contentValues.put("CONTACT_NAME", contactname);
        contentValues.put("CONTACT_NUMBERS", contactphone);
        contentValues.put("CONTACT_EMAILS", contactemail);
        long result = db.insert("ExpenseInfo", null, contentValues);
        if (result == -1) return false;
        else return true;
    }


    public boolean updateData(String id, String account, Double amount, String category, String date, String time, String notes, String incomeexpense, String checked, String currency,String email,String profile,String contactname,String contactphone,String contactemail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("ACCOUNT", account);
        contentValues.put("AMOUNT", amount);
        contentValues.put("CATEGORY", category);
        contentValues.put("DATE", date);
        contentValues.put("TIME", time);
        contentValues.put("NOTES", notes);
        contentValues.put("INCOME_EXPENSE", incomeexpense);
        contentValues.put("CHECKED_DATA", checked);
        contentValues.put("CURRENCY_NAME", currency);
        contentValues.put("EMAIL_1", email);
        contentValues.put("PROFILE_1", profile);
        contentValues.put("CONTACT_NAME", contactname);
        contentValues.put("CONTACT_NUMBERS", contactphone);
        contentValues.put("CONTACT_EMAILS", contactemail);
        long result = db.update("ExpenseInfo", contentValues, "ID =?", new String[]{id});

        if (result == -1) return false;
        else return true;
    }

    public boolean deleteDataExpense(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        String TABLE_NAME = "ExpenseInfo";

        long result = db.delete("ExpenseInfo", "ID =?", new String[]{id});
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TABLE_NAME + "'");


        if (result == -1) return false;
        else return true;
    }

    public ArrayList<ExpenseIncome> getallData_Category_Expense(String category_s,String email,String profile) {
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE CATEGORY LIKE" + "'" + category_s + "'" + "AND INCOME_EXPENSE LIKE " + "'expense'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category = cursor.getString(3);
            String date = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category, date, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);

            arrayList.add(expenseIncome);

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> getallData_Category_Income(String category_s,String email,String profile) {
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE CATEGORY LIKE" + "'" + category_s + "'" + "AND INCOME_EXPENSE LIKE " + "'income'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category = cursor.getString(3);
            String date = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category, date, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> getallData_Account_Expense(String account_s,String email,String profile) {
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE ACCOUNT LIKE" + "'" + account_s + "'" + "AND INCOME_EXPENSE LIKE " + "'expense'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category = cursor.getString(3);
            String date = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category, date, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> getallData_Account_Income(String account_s,String email,String profile) {
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE ACCOUNT LIKE" + "'" + account_s + "'" + "AND INCOME_EXPENSE LIKE " + "'income'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category = cursor.getString(3);
            String date = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category, date, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> getallData_Date_category_Expense(Date_and_category date_and_category,String email,String profile) {
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Date> d;
        d = date_and_category.date;
        Iterator i = d.iterator();
        ExpenseIncome before = new ExpenseIncome();
        while (i.hasNext()) {
            Date dat = (Date) i.next();
            SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
            String formattedDate = df.format(dat);
            final String[] tokens = formattedDate.split(Pattern.quote("/"));

            Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE CATEGORY LIKE " + "'" + date_and_category.Category + "' AND DATE  LIKE " + "'%" + tokens[1] + "/" + tokens[2] + "%'" + "AND INCOME_EXPENSE LIKE " + "'expense'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

            while (cursor.moveToNext()) {
                Integer id = cursor.getInt(0);
                String account = cursor.getString(1);
                Double amount = cursor.getDouble(2);
                String category = cursor.getString(3);
                String date = cursor.getString(4);
                String time = cursor.getString(5);
                String notes = cursor.getString(6);
                String incomeexpense = cursor.getString(7);
                String checked = cursor.getString(8);
                String currency = cursor.getString(9);
                String contactname = cursor.getString(12);
                String contactphonenumbers = cursor.getString(13);
                String contactemails = cursor.getString(14);
                ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category, date, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


                arrayList.add(expenseIncome);
            }

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> getallData_Date_category_Income(Date_and_category date_and_category,String email,String profile) {
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Date> d;
        d = date_and_category.date;
        Iterator i = d.iterator();
        ExpenseIncome before = new ExpenseIncome();
        while (i.hasNext()) {
            Date dat = (Date) i.next();
            SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
            String formattedDate = df.format(dat);
            final String[] tokens = formattedDate.split(Pattern.quote("/"));

            Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE CATEGORY LIKE " + "'" + date_and_category.Category + "' AND DATE  LIKE " + "'%" + tokens[1] + "/" + tokens[2] + "%'" + "AND INCOME_EXPENSE LIKE " + "'income'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

            while (cursor.moveToNext()) {
                Integer id = cursor.getInt(0);
                String account = cursor.getString(1);
                Double amount = cursor.getDouble(2);
                String category = cursor.getString(3);
                String date = cursor.getString(4);
                String time = cursor.getString(5);
                String notes = cursor.getString(6);
                String incomeexpense = cursor.getString(7);
                String checked = cursor.getString(8);
                String currency = cursor.getString(9);
                String contactname = cursor.getString(12);
                String contactphonenumbers = cursor.getString(13);
                String contactemails = cursor.getString(14);
                ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category, date, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


                arrayList.add(expenseIncome);
            }

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> getallData_Date_Expense(ArrayList<Date> date_d,String email,String profile) {
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Iterator i = date_d.iterator();
        ExpenseIncome before = new ExpenseIncome();
        while (i.hasNext()) {
            Date dat = (Date) i.next();
            SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
            String formattedDate = df.format(dat);
            final String[] tokens = formattedDate.split(Pattern.quote("/"));

            Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE DATE LIKE " + "'%" + tokens[1] + "/" + tokens[2] + "%'" + "AND INCOME_EXPENSE LIKE " + "'expense'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

            while (cursor.moveToNext()) {
                Integer id = cursor.getInt(0);
                String account = cursor.getString(1);
                Double amount = cursor.getDouble(2);
                String category = cursor.getString(3);
                String date = cursor.getString(4);
                String time = cursor.getString(5);
                String notes = cursor.getString(6);
                String incomeexpense = cursor.getString(7);
                String checked = cursor.getString(8);
                String currency = cursor.getString(9);
                String contactname = cursor.getString(12);
                String contactphonenumbers = cursor.getString(13);
                String contactemails = cursor.getString(14);
                ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category, date, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


                arrayList.add(expenseIncome);
            }

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> getallData_Date_Income(ArrayList<Date> date_d,String email,String profile) {
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Iterator i = date_d.iterator();
        ExpenseIncome before = new ExpenseIncome();
        while (i.hasNext()) {
            Date dat = (Date) i.next();
            SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
            String formattedDate = df.format(dat);
            final String[] tokens = formattedDate.split(Pattern.quote("/"));

            Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE DATE LIKE " + "'%" + tokens[1] + "/" + tokens[2] + "%'" + "AND INCOME_EXPENSE LIKE " + "'income'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

            while (cursor.moveToNext()) {
                Integer id = cursor.getInt(0);
                String account = cursor.getString(1);
                Double amount = cursor.getDouble(2);
                String category = cursor.getString(3);
                String date = cursor.getString(4);
                String time = cursor.getString(5);
                String notes = cursor.getString(6);
                String incomeexpense = cursor.getString(7);
                String checked = cursor.getString(8);
                String currency = cursor.getString(9);
                String contactname = cursor.getString(12);
                String contactphonenumbers = cursor.getString(13);
                String contactemails = cursor.getString(14);
                ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category, date, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


                arrayList.add(expenseIncome);
            }

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> getallData_Expense(String email,String profile) {
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE INCOME_EXPENSE LIKE " + "'expense'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'AND  CHECKED_DATA  NOT LIKE" + "'PAID'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category = cursor.getString(3);
            String date = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category, date, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> getallData_Income(String email,String profile) {
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE INCOME_EXPENSE LIKE " + "'income'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"' AND  CHECKED_DATA NOT LIKE " + "'PAID'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category = cursor.getString(3);
            String date = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category, date, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }
    public ArrayList<ExpenseIncome> getallData_Expense_filter(String email,String profile) {
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE INCOME_EXPENSE LIKE " + "'expense'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category = cursor.getString(3);
            String date = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category, date, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> getallData_Income_filter(String email,String profile) {
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE INCOME_EXPENSE LIKE " + "'income'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category = cursor.getString(3);
            String date = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category, date, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> getallData(String email,String profile) {
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category = cursor.getString(3);
            String date = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category, date, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> getallData_checked(String email,String profile) {
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE CHECKED_DATA LIKE " + "'CHECKED'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category = cursor.getString(3);
            String date = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category, date, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }

    public Double get_Income() {
        SQLiteDatabase db = this.getReadableDatabase();
        Double amount = 0.00;


        Cursor cursor = db.rawQuery("SELECT AMOUNT FROM ExpenseInfo WHERE INCOME_EXPENSE LIKE " + "'income'", null);

        while (cursor.moveToNext()) {

            amount += cursor.getDouble(0);
        }

        return amount;
    }

    public Double get_Expense() {
        SQLiteDatabase db = this.getReadableDatabase();
        Double amount = 0.00;


        Cursor cursor = db.rawQuery("SELECT AMOUNT FROM ExpenseInfo WHERE INCOME_EXPENSE LIKE " + "'expense'", null);

        while (cursor.moveToNext()) {

            amount += cursor.getDouble(0);
        }

        return amount;
    }

    public ArrayList<ExpenseIncome> get_Expense_monthly(String date, String category,String email,String profile) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();

        final String[] tokens = date.split(Pattern.quote("/"));

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE CATEGORY LIKE " + "'" + category + "' AND DATE  LIKE " + "'%" + tokens[1] + "/" + tokens[2] + "%'" + "AND INCOME_EXPENSE LIKE " + "'expense'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category_1 = cursor.getString(3);
            String date_1 = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category, date, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }
    public ArrayList<ExpenseIncome> get_OverviewIncome_Category_yearly(String category,String date, String email,String profile) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();

        final String[] tokens = date.split(Pattern.quote("/"));

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE CATEGORY LIKE " + "'" +  category + "' AND  DATE  LIKE " + "'%" + tokens[2] + "%'" + "AND INCOME_EXPENSE LIKE " + "'income'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category_1 = cursor.getString(3);
            String date_1 = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category_1, date_1, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }
    public ArrayList<ExpenseIncome> get_Overview_Category_yearly(String category,String date, String email,String profile) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();

        final String[] tokens = date.split(Pattern.quote("/"));

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE CATEGORY LIKE " + "'" +  category + "' AND  DATE  LIKE " + "'%" + tokens[2] + "%'" + "AND INCOME_EXPENSE LIKE " + "'expense'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category_1 = cursor.getString(3);
            String date_1 = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category_1, date_1, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> get_OverviewExpense_yearly(String date, String email,String profile) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();

        final String[] tokens = date.split(Pattern.quote("/"));

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE  DATE  LIKE " + "'%" + tokens[2] + "%'" + "AND INCOME_EXPENSE LIKE " + "'expense'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category_1 = cursor.getString(3);
            String date_1 = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category_1, date_1, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> get_Overview_Category_monthly(String category, String date, String email,String profile) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();

        final String[] tokens = date.split(Pattern.quote("/"));

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE CATEGORY LIKE " + "'" +  category + "' AND  DATE  LIKE " + "'%/" + tokens[1] + "/" + tokens[2] + "%'" + "AND INCOME_EXPENSE LIKE " + "'expense'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category_1 = cursor.getString(3);
            String date_1 = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category_1, date_1, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> get_OverviewIncome_Category_monthly(String category, String date, String email,String profile) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();

        final String[] tokens = date.split(Pattern.quote("/"));

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE CATEGORY LIKE " + "'" +  category + "' AND  DATE  LIKE " + "'%/" + tokens[1] + "/" + tokens[2] + "%'" + "AND INCOME_EXPENSE LIKE " + "'income'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category_1 = cursor.getString(3);
            String date_1 = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category_1, date_1, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> get_OverviewExpense_monthly(String date, String email,String profile) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();

        final String[] tokens = date.split(Pattern.quote("/"));

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE  DATE  LIKE " + "'%/" + tokens[1] + "/" + tokens[2] + "%'" + "AND INCOME_EXPENSE LIKE " + "'expense'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category_1 = cursor.getString(3);
            String date_1 = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category_1, date_1, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }

    public ArrayList<ExpenseIncome> get_OverviewIncome_monthly(String date, String email,String profile) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();

        final String[] tokens = date.split(Pattern.quote("/"));

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE  DATE  LIKE " + "'%" + tokens[1] + "/" + tokens[2] + "%'" + "AND INCOME_EXPENSE LIKE " + "'income'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category_1 = cursor.getString(3);
            String date_1 = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category_1, date_1, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }
    public ArrayList<ExpenseIncome> get_OverviewIncome_yearly(String date, String email,String profile) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<ExpenseIncome> arrayList = new ArrayList<>();

        final String[] tokens = date.split(Pattern.quote("/"));

        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE  DATE  LIKE " + "'%" + tokens[2] + "%'" + "AND INCOME_EXPENSE LIKE " + "'income'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);

        while (cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String account = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String category_1 = cursor.getString(3);
            String date_1 = cursor.getString(4);
            String time = cursor.getString(5);
            String notes = cursor.getString(6);
            String incomeexpense = cursor.getString(7);
            String checked = cursor.getString(8);
            String currency = cursor.getString(9);
            String contactname = cursor.getString(12);
            String contactphonenumbers = cursor.getString(13);
            String contactemails = cursor.getString(14);
            ExpenseIncome expenseIncome = new ExpenseIncome(id, account, amount, category_1, date_1, time, notes, incomeexpense, checked, currency,contactname,contactphonenumbers,contactemails);


            arrayList.add(expenseIncome);

        }
        return arrayList;
    }
    public Float getAmount_Category_Expense(String category_s,String email,String profile) {
        SQLiteDatabase db = this.getReadableDatabase();
        Double amount = 0.00;

        Cursor cursor = db.rawQuery("SELECT AMOUNT FROM ExpenseInfo WHERE CATEGORY LIKE" + "'" + category_s + "'" + "AND INCOME_EXPENSE LIKE " + "'expense'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);
        while (cursor.moveToNext()) {

            amount += cursor.getDouble(0);
        }

        return amount.floatValue();
    }
    public Float getAmount_Category_Income(String category_s,String email,String profile) {
        SQLiteDatabase db = this.getReadableDatabase();
        Double amount = 0.00;

        Cursor cursor = db.rawQuery("SELECT AMOUNT FROM ExpenseInfo WHERE CATEGORY LIKE" + "'" + category_s + "'" + "AND INCOME_EXPENSE LIKE " + "'income'"+ "AND EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);
        while (cursor.moveToNext()) {

            amount += cursor.getDouble(0);
        }

        return amount.floatValue();
    }

    public Cursor exportingData(String email,String profile)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ExpenseInfo WHERE EMAIL_1 LIKE " + "'"+ email+"'AND  PROFILE_1 LIKE" + "'"+ profile+"'", null);
        return cursor;
    }
}



