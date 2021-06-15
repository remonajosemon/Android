package com.example.properapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mynameismidori.currencypicker.CurrencyPicker;
import com.mynameismidori.currencypicker.CurrencyPickerListener;
import com.mynameismidori.currencypicker.ExtendedCurrency;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Income_fragment extends Fragment {
    DatabaseHelper myDB;
    EditText date_text;
    EditText time_text;
    String check;
    String email;
    String contactNumber;
    EditText contact_name;
    EditText contact_number;
    //abhijith >>>> EditText name_text;
    Spinner type_spinner;
    EditText amount_text;
    EditText notes_text;
    CheckBox checkBox;
    Spinner category_spinner;
    Calendar calendar;
    public static final int PICK_CONTACT = 0;
    //ImageView star;
    ArrayList<ExpenseIncome> arrayList;
    Button insert_button,addContacts;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    MyAdapter myAdapter;
    CategoryAdapter categoryAdapter;
    ArrayList<CategoryItem> categoryItemArrayList;
    String clickedCategoryName;
    Button bold,italics,underline,cross,exp_inc;
    BottomNavigationView bottomNav;
    ImageView currency;
    String currencyname=null;
    String[] user = new String[2];
    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;
    StringBuilder sb = new StringBuilder();
    StringBuilder sb1 = new StringBuilder();
    String contactName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_income,container,false);
        date_text = (EditText)v.findViewById(R.id.date_edittext);
        //abhijith >>>> name_text = (EditText)v.findViewById(R.id.name_edittext);
        //star = (ImageView)v.findViewById(R.id.imageEncomeStarred);
        type_spinner = (Spinner) v.findViewById(R.id.spinner_category2);
        amount_text = (EditText)v.findViewById(R.id.amount_edittext);
        notes_text = (EditText)v.findViewById(R.id.notes_editText);
        category_spinner = (Spinner)v.findViewById(R.id.spinner_category);
        time_text=(EditText)v.findViewById(R.id.time_edittext);
        insert_button = (Button)v.findViewById(R.id.insert_button);
        addContacts = (Button) v.findViewById(R.id.insert_contact_button);
        contact_name=(EditText)v.findViewById(R.id.name_edittext);
        checkBox=(CheckBox)v.findViewById(R.id.checkBox);
        myDB = new DatabaseHelper(getContext());
        amount_text.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        bold =(Button)v.findViewById(R.id.bold_button);
        italics =(Button)v.findViewById(R.id.italics_button);
        underline =(Button)v.findViewById(R.id.underline_button);
        cross =(Button)v.findViewById(R.id.deselect_button);
        currency=(ImageView)v.findViewById(R.id.currency);
        user=myDB.get_currentuser();

        date();
        initList();
        spinner();
        notes();
        checkBox_check();
        insertData();
        currency();
        bottomNav = v.findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);
        bottom_navigation_helper();

        addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int PERMISSION_ALL = 1;
                String[] PERMISSIONS = {
                        android.Manifest.permission.READ_CONTACTS,
                        android.Manifest.permission.WRITE_CONTACTS,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_SMS,
                        android.Manifest.permission.CAMERA
                };
                if (! hasPermissions(getActivity(), PERMISSIONS)) {
                    ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
                }
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });
        return v;
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
    public void currency()
    {
        String default_currency= "Euro";
        ExtendedCurrency currencyset = ExtendedCurrency.getCurrencyByName(default_currency);
        currency.setImageResource(currencyset.getFlag());
        currencyname= default_currency;

        default_currency = myDB.getDataSettings(user[0],user[1]);
        if(default_currency!=null) {
            currencyset = ExtendedCurrency.getCurrencyByName(default_currency);
            currency.setImageResource(currencyset.getFlag());
            currencyname=default_currency;
        }

        currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");  // dialog title
                picker.setListener(new CurrencyPickerListener() {
                    @Override
                    public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {
                        currency.setImageResource(flagDrawableResID);
                        currencyname=name;
                        picker.dismiss();

                    }
                });
                picker.show(getActivity().getSupportFragmentManager(), "CURRENCY_PICKER");
            }
        });

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
    public void initList() {
        categoryItemArrayList = new ArrayList<CategoryItem>();
        categoryItemArrayList.add(new CategoryItem("Food", R.drawable.food_60x60));
        categoryItemArrayList.add(new CategoryItem("Entertainment", R.drawable.entertainment_60x60));
        categoryItemArrayList.add(new CategoryItem("Education", R.drawable.education_60x60));
        categoryItemArrayList.add(new CategoryItem("Travel", R.drawable.travel_60x60));
    }

    public void notes(){
        bold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int startSelection=notes_text.getSelectionStart();
                int endSelection=notes_text.getSelectionEnd();
                Spannable ss = new SpannableString(notes_text.getText());
                CharSequence normalBefore= ss.subSequence(0,startSelection);
                CharSequence normalAfter= ss.subSequence(endSelection,notes_text.length());
                CharSequence normalBOLD= ss.subSequence(startSelection,endSelection);
                SpannableStringBuilder longDescription = new SpannableStringBuilder();
                longDescription.append(normalBefore);
                int start = longDescription.length();


                longDescription.append(normalBOLD);
                longDescription.setSpan(new ForegroundColorSpan(Color.BLACK), start, longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                longDescription.setSpan(new StyleSpan(Typeface.BOLD), start, longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                longDescription.append(normalAfter);

                notes_text.setText(longDescription);

            }
        });

        italics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startSelection=notes_text.getSelectionStart();
                int endSelection=notes_text.getSelectionEnd();

                Spannable ss = new SpannableString(notes_text.getText());
                CharSequence normalBefore= ss.subSequence(0,startSelection);
                CharSequence normalAfter= ss.subSequence(endSelection,notes_text.length());
                CharSequence normalITALIC= ss.subSequence(startSelection,endSelection);
                SpannableStringBuilder longDescription = new SpannableStringBuilder();
                longDescription.append(normalBefore);
                int start = longDescription.length();
                longDescription.append(normalITALIC);
                longDescription.setSpan(new ForegroundColorSpan(Color.BLACK), start, longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                longDescription.setSpan(new StyleSpan(Typeface.ITALIC), start, longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                longDescription.append(normalAfter);

                notes_text.setText(longDescription);

            }
        });

        underline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startSelection=notes_text.getSelectionStart();
                int endSelection=notes_text.getSelectionEnd();

                Spannable ss = new SpannableString(notes_text.getText());
                CharSequence normalBefore= ss.subSequence(0,startSelection);
                CharSequence normalAfter= ss.subSequence(endSelection,notes_text.length());
                CharSequence normalUNDERLINE= ss.subSequence(startSelection,endSelection);
                SpannableStringBuilder longDescription = new SpannableStringBuilder();
                longDescription.append(normalBefore);
                int start = longDescription.length();
                longDescription.append(normalUNDERLINE);
                longDescription.setSpan(new ForegroundColorSpan(0xFFCC5500), start, longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                longDescription.setSpan(new UnderlineSpan(), start, longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                longDescription.append(normalAfter);

                notes_text.setText(longDescription);

            }
        });
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startSelection=notes_text.getSelectionStart();
                int endSelection=notes_text.getSelectionEnd();

                Spannable ss = new SpannableString(notes_text.getText());
                CharSequence normalBefore= ss.subSequence(0,startSelection);
                CharSequence normalAfter= ss.subSequence(endSelection,notes_text.length());
                CharSequence normalDeselect= ss.subSequence(startSelection,endSelection);
                SpannableStringBuilder longDescription = new SpannableStringBuilder();
                longDescription.append(normalBefore);

                longDescription.append(normalDeselect.toString());

                longDescription.append(normalAfter);

                notes_text.setText(longDescription);

            }
        });


    }
    public void checkBox_check()
    {
        check = "NORMAL";
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked())
                {
                    check = "CHECKED";
                }
                else
                {
                    check = "NORMAL";
                }
            }
        });
    }

    public void date()
    {

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d/M/yyyy");
        String formattedDate = dateFormat.format(date);
        date_text.setText(formattedDate);

        date_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                // month = month + 1;
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        date_text.setText(dayOfMonth+"/"+(month+1)+"/"+year);

                    }

                },year,month,day);
                datePickerDialog.show();
            }
        });
        Calendar c = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String formattedTime = timeFormat.format(c.getTime());
        time_text.setText(formattedTime);
        time_text.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timepicker, int hourOfDay, int minute) {
                        time_text.setText(hourOfDay+":"+minute);
                    }
                },hour,minute, DateFormat.is24HourFormat(getActivity()));
                timePickerDialog.show();
            }
        });

    }

    public void spinner()
    {

        ArrayAdapter<String> my_spinner_adapter_filter_spin = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Account_Income));
        my_spinner_adapter_filter_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(my_spinner_adapter_filter_spin);

        ArrayAdapter<String>  stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.Category_Income));
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(stringArrayAdapter);


    }
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

        double result = myDB.getcurrency_rate_date("EUR",date_text.getText().toString());
        if(result == 0) {
            java.text.DateFormat formatter;
            Date dat = Calendar.getInstance().getTime();;
            formatter = new SimpleDateFormat("d/M/yyyy");
            try {
                dat = formatter.parse(date_text.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            myDB.insertTotal(user[0],user[1],dat);

            thread99.start();
            try {
                thread99.join();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public void insertData()
    {
        insert_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                boolean inserted =false;
                setcurrencyrate(date_text.getText().toString());

                if (amount_text.length() == 0) {
                    amount_text.setError("Enter Amount");
                    i++;
                }
                if(amount_text.length() > 7)
                {
                    amount_text.setError("Amount too big");
                    i++;
                }
                if (date_text.length() == 0) {
                    date_text.setError("Enter Date");
                    i++;
                }
                if (i == 0 ) {
                    String notes = Html.toHtml(notes_text.getText(),1);
                    if(myDB.getcurrency_rate_date("EUR",date_text.getText().toString()) != 0) {
                        inserted = myDB.insertData(type_spinner.getSelectedItem().toString(), Double.parseDouble(amount_text.getText().toString()), category_spinner.getSelectedItem().toString(), date_text.getText().toString(), time_text.getText().toString(), notes, "income", check, currencyname, user[0],user[1],contact_name.getText().toString(),sb.toString(),sb1.toString());
                    }
                    if (inserted) {
                        Toast.makeText(getContext(), "Value is Inserted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Value not Inserted or Check internet connectivity", Toast.LENGTH_LONG).show();
                    }

                    startActivity(new Intent(getActivity(), com.example.properapp.ListView.class));
                    getActivity().finish();

                }
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
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            getActivity().finish();
                            break;

                        case R.id.Nav_List:
                            Intent show1 = new Intent(getActivity(), ListView.class);
                            startActivity(show1);
                            getActivity().finish();
                            break;

                        case R.id.Nav_Add:
                            Intent show2 = new Intent(getActivity(), ListView_Checked.class);
                            startActivity(show2);
                            getActivity().finish();
                            break;
                    }
                    return true;
                }
            };


    //Variables







    //methods for contacts
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getContext().getContentResolver().query(contactData, null, null, null, null);
                   /* if (c.moveToFirst()) {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        try {
                            if (hasPhone.equalsIgnoreCase("1")) {
                                Cursor phones = getContentResolver().query(
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                        null, null);
                                phones.moveToFirst();
                                String cNumber = phones.getString(phones.getColumnIndex("data1"));
                                System.out.println("number is:" + cNumber);
                                Log.d(TAG, "number is:" + cNumber);
                                //txtphno.setText("Phone Number is: "+cNumber);
                            }
                            String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            //txtname.setText("Name is: "+name);
                            System.out.println("name is:" + name);
                            Log.d(TAG, "name is:" + name);
                        }*/

                    while(c.moveToNext())
                    {
                        String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        contactName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        try{
                            //the below cursor will give you details for multiple contacts
                            Cursor emailCursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                    new String[]{id}, null);
                            // continue till this cursor reaches to all emails  which are associated with a contact in the contact list
                            if (emailCursor.moveToFirst())
                            {
                                //int emailType         = emailCursor.getInt(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                                //String isStarred        = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED));
                                email    = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                                //you will get all emails according to it's type as below switch case.
                                //Logs.e will print the phone number along with the name in DDMS. you can use these details where ever you want.

                              /*  switch (emailType)
                                {
                                    case ContactsContract.CommonDataKinds.Email.TYPE_MOBILE:
                                        sb1.append(email+ " , ");
                                        Log.e(  " TYPE_MOBILE", " TYPE_MOBILE" + email + " ,");
                                        break;
                                    case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                        sb1.append(email+ " , ");
                                        Log.e(" TYPE_HOME", "TYPE_HOME " + email+ " ,");
                                        break;
                                    case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                                        sb1.append(email+ " , ");
                                        Log.e(" TYPE_WORK", " TYPE_WORK" + email+ " ,");
                                        break;
                                    case ContactsContract.CommonDataKinds.Email.TYPE_OTHER:
                                        sb1.append(email+ " , ");
                                        Log.e(" TYPE_OTHER", " TYPE_OTHER" + email+ " ,");
                                        break;
                                    case ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM:
                                        sb1.append(email+ " , ");
                                        Log.e(" TYPE_CUSTOM", " TYPE_CUSTOM" + email+ " ,");
                                        break;

                                }

                               */

                            }


                            emailCursor.close();
                        }

                        catch (Exception ex)
                        {
                            // Log.d(TAG, "onActivityResult: inside catch block ," + ex.getMessage());
                            //st.getMessage();
                        }
                        Log.d("allnumbers", " " + sb.toString());
                        Log.d("contact name is: ", " "+ contactName );


                        if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                            try{
                                //the below cursor will give you details for multiple contacts
                                Cursor pCursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                        new String[]{id}, null);
                                // continue till this cursor reaches to all phone numbers which are associated with a contact in the contact list
                                if (pCursor.moveToFirst())
                                {
                                    //  int phoneType         = pCursor.getInt(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                                    //String isStarred        = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED));
                                    contactNumber    = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    //you will get all phone numbers according to it's type as below switch case.
                                    //Logs.e will print the phone number along with the name in DDMS. you can use these details where ever you want.

                                   /* switch (phoneType)
                                    {
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(  " TYPE_MOBILE", " TYPE_MOBILE" + phoneNo + " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_HOME", "TYPE_HOME " + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_WORK", " TYPE_WORK" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_WORK_MOBILE", " TYPE_WORK_MOBILE" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_OTHER", " TYPE_OTHER" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_RADIO:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_Radio", " TYPE_Radio" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_TTY_TDD:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_TTY_TDD", " TYPE_TTY_TDD" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_PAGER:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_WORK_PAGER", " TYPE_WORK_PAGER" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_FAX_WORK", " TYPE_FAX_WORK" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_FAX_HOME", " TYPE_FAX_HOME" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_PAGER:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_PAGER", " TYPE_PAGER" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_CUSTOM", " TYPE_CUSTOM" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_CALLBACK:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_CALLBACK", " TYPE_CALLBACK" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_CAR:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_CAR", " TYPE_CAR" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_COMPANY_MAIN", " TYPE_COMPANY_MAIN" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_ISDN:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_ISDN", " TYPE_ISDN" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_MAIN:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_MAIN", " TYPE_MAIN" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_MMS:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_MMS", " TYPE_MMS" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER_FAX:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_OTHER_FAX", " TYPE_OTHER_FAX" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_TELEX:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_TELEX", " TYPE_TELEX" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_ASSISTANT:
                                            sb.append(phoneNo+ " , ");
                                            Log.e(" TYPE_ASSISTANT", " TYPE_ASSISTANT" + phoneNo+ " ,");
                                            break;
                                        default:
                                            break;
                                    }

                                    */

                                }


                                pCursor.close();
                            }

                            catch (Exception ex)
                            {
                                //Log.d(TAG, "onActivityResult: inside catch block ," + ex.getMessage());
                                //st.getMessage();
                            }

                        Log.d("contact name is: ", " "+ contactName );
                        contact_name.setText(contactName.toString());
                        Log.d("allnumbers", " " + contactNumber);

                        Log.d("allemails", " " + email);

                    }
                }
                break;
        }
    }



    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
