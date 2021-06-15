package com.example.properapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class UpdateActivity_expense extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText date_text;
    EditText time_text;
    // EditText name_text;
    Spinner account_spinner;
    TextView account_textview;
    TextView category_textview;
    EditText amount_text;
    EditText notes_text;
    Spinner category_spinner;
    Calendar calendar;
    ImageView date_icon;
    private long backPressedTime;
    private Toast backToast;
    ArrayList<ExpenseIncome> arrayList;
    Button insert_button;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    MyAdapter myAdapter;
    CategoryAdapter categoryAdapter;
    ArrayList<CategoryItem> categoryItemArrayList;
    String clickedCategoryName;
    Button bold,italics,underline,cross,update_button,delete_button;
    ExpenseIncome olditem;
    ImageView currencyset;
    String currencyname;
    String [] user = new String[2];
    EditText contact_name;
    Button contact_button;
    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;
    StringBuilder sb = new StringBuilder();
    StringBuilder sb1 = new StringBuilder();
    String contactName;
    Uri uriContact;
    String contactID;
    TextView contact;
    public static final int PICK_CONTACT = 0;
    String email;
    String contactNumber;
    EditText contact_number;
    int flag=0;
    CheckBox checkBox;
    String check;

    @SuppressLint("SetTextI18n")
    @Nullable

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        myDB = new DatabaseHelper(this);
        account_textview =  findViewById(R.id.account_textview_update);
        category_textview =  findViewById(R.id.category_textview_update);
        date_text = (EditText) findViewById(R.id.date_edittext_update);
        time_text = (EditText) findViewById(R.id.time_edittext_update);
        contact_name=findViewById(R.id.name_edittext);
        contact_button =findViewById(R.id.insert_contact_button);
        account_spinner = (Spinner) findViewById(R.id.spinner_category2);
        amount_text = (EditText) findViewById(R.id.amount_edittext_update);
        notes_text = (EditText) findViewById(R.id.notes_editText_update);
        category_spinner = (Spinner) findViewById(R.id.spinner_update);
        currencyset=(ImageView) findViewById(R.id.currency);
        update_button = (Button) findViewById(R.id.update_button_update);
        delete_button = (Button) findViewById(R.id.delete_button);
        amount_text.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        bold =(Button)findViewById(R.id.bold_button_update);
        italics =(Button)findViewById(R.id.italics_button_update);
        underline =(Button)findViewById(R.id.underline_button_update);
        cross =(Button)findViewById(R.id.deselect_button_update);
        checkBox =(CheckBox)findViewById(R.id.checkBox);
        myDB = new DatabaseHelper(getApplicationContext());
        user=myDB.get_currentuser();
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListner);

        date();
        initList();
        spinner();
        notes();
        update();
        delete();
        currency();
        checkBox_check();

        Bundle extras = getIntent().getExtras();
        if(extras !=null)

        {
            olditem = (ExpenseIncome) getIntent().getSerializableExtra("serialize_data"); //Obtaining data

        }

        ArrayAdapter<String> my_spinner_adapter2 = new ArrayAdapter<String>(UpdateActivity_expense.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Account_Expense));
        my_spinner_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int SelectedPosition = my_spinner_adapter2.getPosition(olditem.getAccount());

        ArrayAdapter<String> my_spinner_adapter = new ArrayAdapter<String>(UpdateActivity_expense.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Category));
        my_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int selectionPosition = my_spinner_adapter.getPosition(olditem.category);

        assert olditem !=null;
        account_spinner.setAdapter(my_spinner_adapter2);
        account_spinner.setSelection(SelectedPosition);
        amount_text.setText(olditem.amount.toString());
        date_text.setText(olditem.date);
        time_text.setText(olditem.time);
        ExtendedCurrency currency = ExtendedCurrency.getCurrencyByName(olditem.currencyname);
        currencyset.setImageResource(currency.getFlag());
        currencyname=currency.getName();
        CharSequence Edit4 = Html.fromHtml(olditem.notes,1);
        notes_text.setText(Edit4);
        categoryAdapter = new CategoryAdapter(getApplicationContext(),categoryItemArrayList);
        category_spinner.setAdapter(categoryAdapter);
        category_spinner.setSelection(selectionPosition);
        contact_name.setText(olditem.contactname);
        if(olditem.checked.equals("CHECKED")) {
            checkBox.setChecked(true);
        }


        contact_button.setOnClickListener(new View.OnClickListener() {
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
                if (! hasPermissions(UpdateActivity_expense.this, PERMISSIONS)) {
                    ActivityCompat.requestPermissions(UpdateActivity_expense.this, PERMISSIONS, PERMISSION_ALL);
                }
                sb.setLength(0);
                sb1.setLength(0);
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
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
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (PICK_CONTACT):
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = getApplicationContext().getContentResolver().query(contactData, null, null, null, null);
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
                            Cursor emailCursor = getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                    new String[]{id}, null);

                            // continue till this cursor reaches to all emails  which are associated with a contact in the contact list


                            while (emailCursor.moveToNext())
                            {
                                int emailType         = emailCursor.getInt(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                                String isStarred        = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED));
                                email    = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                                //you will get all emails according to it's type as below switch case.
                                //Logs.e will print the phone number along with the name in DDMS. you can use these details where ever you want.

                                switch (emailType)
                                {
                                    case ContactsContract.CommonDataKinds.Email.TYPE_MOBILE:
                                        // emailSelected=email;
                                        sb1.append(email+" ");
                                        Log.e(  " TYPE_MOBILE", " TYPE_MOBILE" + email + "  ");
                                        break;
                                    case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                        sb1.append(email+" ");

                                        //emailSelected=email;
                                        Log.e(" TYPE_HOME", "TYPE_HOME " + email+ "  ");
                                        break;
                                    case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                                        sb1.append(email+" ");
                                        // emailSelected=email;
                                        Log.e(" TYPE_WORK", " TYPE_WORK" + email+ "  ");
                                        break;
                                    case ContactsContract.CommonDataKinds.Email.TYPE_OTHER:
                                        sb1.append(email+" ");
                                        // emailSelected=email;
                                        Log.e(" TYPE_OTHER", " TYPE_OTHER" + email+ "  ");
                                        break;
                                    case ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM:
                                        sb1.append(email+" ");
                                        // emailSelected=email;
                                        Log.e(" TYPE_CUSTOM", " TYPE_CUSTOM" + email+ "  ");
                                        break;

                                }



                            }




                            emailCursor.close();
                        }

                        catch (Exception ex)
                        {
                            // Log.d(TAG, "onActivityResult: inside catch block ," + ex.getMessage());
                            //st.getMessage();
                        }
                        Log.d("allnumbers", " " +contactNumber);
                        Log.d("contact name is: ", " "+ contactName );
                        Log.d("email is: ",""+sb1);


                        if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                            try{
                                //the below cursor will give you details for multiple contacts
                                Cursor pCursor = getApplicationContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                        new String[]{id}, null);
                            /*    if (pCursor.moveToFirst()) {
                                    contactNumber = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                }

                             */
                                // continue till this cursor reaches to all phone numbers which are associated with a contact in the contact list
                                while (pCursor.moveToNext())
                                {
                                    int phoneType         = pCursor.getInt(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                                    //String isStarred        = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.STARRED));
                                    String phoneNo    = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    //you will get all phone numbers according to it's type as below switch case.
                                    //Logs.e will print the phone number along with the name in DDMS. you can use these details where ever you want.

                                    switch (phoneType)
                                    {
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                            sb.append(phoneNo+ " ");
                                            Log.e(  " TYPE_MOBILE", " TYPE_MOBILE" + phoneNo + " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_HOME", "TYPE_HOME " + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_WORK", " TYPE_WORK" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_MOBILE:
                                            sb.append(phoneNo+"/n");
                                            Log.e(" TYPE_WORK_MOBILE", " TYPE_WORK_MOBILE" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_OTHER", " TYPE_OTHER" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_RADIO:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_Radio", " TYPE_Radio" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_TTY_TDD:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_TTY_TDD", " TYPE_TTY_TDD" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK_PAGER:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_WORK_PAGER", " TYPE_WORK_PAGER" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_FAX_WORK", " TYPE_FAX_WORK" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_FAX_HOME", " TYPE_FAX_HOME" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_PAGER:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_PAGER", " TYPE_PAGER" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_CUSTOM", " TYPE_CUSTOM" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_CALLBACK:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_CALLBACK", " TYPE_CALLBACK" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_CAR:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_CAR", " TYPE_CAR" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_COMPANY_MAIN", " TYPE_COMPANY_MAIN" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_ISDN:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_ISDN", " TYPE_ISDN" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_MAIN:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_MAIN", " TYPE_MAIN" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_MMS:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_MMS", " TYPE_MMS" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER_FAX:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_OTHER_FAX", " TYPE_OTHER_FAX" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_TELEX:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_TELEX", " TYPE_TELEX" + phoneNo+ " ,");
                                            break;
                                        case ContactsContract.CommonDataKinds.Phone.TYPE_ASSISTANT:
                                            sb.append(phoneNo+" ");
                                            Log.e(" TYPE_ASSISTANT", " TYPE_ASSISTANT" + phoneNo+ " ,");
                                            break;
                                        default:
                                            break;
                                    }

                                }




                                pCursor.close();
                            }

                            catch (Exception ex)
                            {
                                //Log.d(TAG, "onActivityResult: inside catch block ," + ex.getMessage());
                                //st.getMessage();
                            }

                        Log.d("contact name is: ", " "+ contactName );
                        contact_name.setText(contactName);

                        Log.d("allnumbers", " " + sb.toString());
                        //contact_number.setText(sb.toString());
                        Log.d("allemails", " " + sb1.toString());
                        flag=1;

                    }
                }
                break;
        }
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
            startActivity(new Intent(UpdateActivity_expense.this,ListView.class));

        }
        backPressedTime = System.currentTimeMillis();
    }

    public void currency()
    {
        currencyset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");  // dialog title
                picker.setListener(new CurrencyPickerListener() {
                    @Override
                    public void onSelectCurrency(String name, String code, String symbol, int flagDrawableResID) {
                        currencyset.setImageResource(flagDrawableResID);
                        currencyname=name;
                        picker.dismiss();

                    }
                });
                picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");
            }
        });

    }
    public void initList() {
        categoryItemArrayList = new ArrayList<CategoryItem>();
        categoryItemArrayList.add(new CategoryItem("Shopping", R.drawable.gift_bag));
        categoryItemArrayList.add(new CategoryItem("Car", R.drawable.pickup_car));
        categoryItemArrayList.add(new CategoryItem("Entertainment", R.drawable.entertainment_60x60));
        categoryItemArrayList.add(new CategoryItem("Education", R.drawable.education_60x60));
        categoryItemArrayList.add(new CategoryItem("Food n Drink", R.drawable.food_60x60));
        categoryItemArrayList.add(new CategoryItem("Grocery", R.drawable.grocery_60x60));
        categoryItemArrayList.add(new CategoryItem("Health", R.drawable.insurance));
        categoryItemArrayList.add(new CategoryItem("Travel", R.drawable.travel_60x60));
        categoryItemArrayList.add(new CategoryItem("Other", R.drawable.other));

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
                longDescription.setSpan(new ForegroundColorSpan(Color.BLACK), start, longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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

                datePickerDialog = new DatePickerDialog(UpdateActivity_expense.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        date_text.setText(dayOfMonth+"/"+(month+1)+"/"+year);

                    }

                },year,month,day);
                datePickerDialog.show();
            }
        });

        time_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(UpdateActivity_expense.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timepicker, int hourOfDay, int minute) {
                        time_text.setText(hourOfDay+":"+minute);
                    }
                },hour,minute, DateFormat.is24HourFormat(UpdateActivity_expense.this));
                timePickerDialog.show();
            }
        });

    }

    public void spinner()
    {
        categoryAdapter = new CategoryAdapter(getApplicationContext(),categoryItemArrayList);
        category_spinner.setAdapter(categoryAdapter);

        ArrayAdapter<String> my_spinner_adapter2 = new ArrayAdapter<String>(UpdateActivity_expense.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Account_Expense));
        my_spinner_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        account_spinner.setAdapter(my_spinner_adapter2);


        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategoryItem clickedItem = (CategoryItem)parent.getItemAtPosition(position);
                clickedCategoryName = clickedItem.getCategoryName();
                //  Toast.makeText(getActivity(), clickedCategoryName +" selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setcurrencyrate(final String date) {
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
                        int i = 0, l = 0;
                        l = currencies.length;
                        String[] codes = new String[l];
                        while (i < l) {
                            codes[i] = currencies[i].getCode();
                            i = i + 1;
                        }

                        for (i = 0; i < l; i++) {
                            Double rateValue = Double.valueOf(result.getString(codes[i]));
                            boolean inserted = myDB.insertcurrency_rate_date(codes[i], rateValue, date);
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
        double result = myDB.getcurrency_rate_date("EUR", date_text.getText().toString());
        if (result == 0) {

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


    public void update() {

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = 0;
                boolean inserted=false;
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
                if (i == 0) {//
                    String notes = Html.toHtml(notes_text.getText(), 1);
                    String phone=olditem.contactphonenumbers;
                    String email =olditem.contactemails;
                    if(flag==1)
                    {
                        phone= sb.toString();
                        email =sb1.toString();

                    }
                    if (myDB.getcurrency_rate_date("EUR", date_text.getText().toString()) != 0) {
                        inserted = myDB.updateData(olditem.ID.toString(), account_spinner.getSelectedItem().toString(), Double.parseDouble(amount_text.getText().toString()), clickedCategoryName, date_text.getText().toString(), time_text.getText().toString(), notes, olditem.incomeexpense, check, currencyname, user[0], user[1],contact_name.getText().toString(),phone,email);
                    }if (inserted) {
                        Toast.makeText(getApplicationContext(), "Value updated", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Value not updated or Check internet connectivity", Toast.LENGTH_LONG).show();
                    }
                    Intent update = new Intent(UpdateActivity_expense.this, ListView.class);
                    startActivity(update);
                    finish();
                }
            }
        });

    }
    public void delete() {

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean deleted = myDB.deleteDataExpense(olditem.ID.toString());
                if (deleted) {
                    Toast.makeText(getApplicationContext(), "Value deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Value not deleted", Toast.LENGTH_LONG).show();
                }
                Intent update = new Intent(UpdateActivity_expense.this, ListView.class);
                startActivity(update);
                finish();
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

                            startActivity(new Intent(UpdateActivity_expense.this,MainActivity.class));
                            finish();

                            break;
                        case R.id.Nav_List:
                            Intent show1 = new Intent(UpdateActivity_expense.this, ListView.class);
                            startActivity(show1);
                            finish();
                            break;

                        case R.id.Nav_Add:
                            Intent show2 = new Intent(UpdateActivity_expense.this, ListView_Checked.class);
                            startActivity(show2);
                            finish();
                            break;
                    }
                    return true;
                }
            };
}
