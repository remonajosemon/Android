package com.example.properapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Change_Profile extends AppCompatActivity {

    Spinner profile_spinner;
    Button profile_button, setprofile_button,deleteprofile_button;
    EditText setprofileedittext;
    private long backPressedTime;
    private Toast backToast;
    DatabaseHelper mydb;
    String[] user= new String[2];
    ImageView addnewprofileimageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__profile);
        mydb = new DatabaseHelper(this);
        user =mydb.get_currentuser();

        profile_spinner= findViewById(R.id.profile_spinner);
        profile_button=findViewById(R.id.profile_button);
        deleteprofile_button =findViewById(R.id.delete_profile_button);
        addnewprofileimageview =findViewById(R.id.addnewprofile_imageView);
        setprofile_button=findViewById(R.id.set_profile_button);
        setprofileedittext=findViewById(R.id.setprofile_editText);
        setprofileedittext.setVisibility(View.INVISIBLE);
        addnewprofileimageview.setVisibility(View.INVISIBLE);


        ArrayList<String> profiles= mydb.getuserprofile_details(user[0]);

        ArrayAdapter<String> my_spinner_adapter_filter_spin = new ArrayAdapter<String>(Change_Profile.this,
                android.R.layout.simple_list_item_1, profiles);
        my_spinner_adapter_filter_spin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profile_spinner.setAdapter(my_spinner_adapter_filter_spin);
        int SelectedPosition = my_spinner_adapter_filter_spin.getPosition(user[1]);
        profile_spinner.setSelection(SelectedPosition);

        setprofile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setprofileedittext.setVisibility(View.VISIBLE);//
                addnewprofileimageview.setVisibility(View.VISIBLE);

            }
        });

        addnewprofileimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean insert= mydb.insertuserprofile_details(user[0],setprofileedittext.getText().toString());
                if(insert)
                {
                    Toast.makeText(Change_Profile.this, "Profile added", Toast.LENGTH_LONG).show();
                    Intent contact = new Intent(Change_Profile.this, Change_Profile.class);
                    startActivity(contact);
                    finish();
                }
            }
        });
        deleteprofile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user[1].equals(profile_spinner.getSelectedItem().toString())) {
                    if (!profile_spinner.getSelectedItem().toString().equals("Primary")) {
                        boolean inserted = mydb.insert_currentuser(user[0], "Primary");

                        if (inserted) {
                            boolean deleted = mydb.deleteuserprofile_details(user[0], profile_spinner.getSelectedItem().toString());
                            if (deleted) {
                                Toast.makeText(Change_Profile.this, "Profile deleted", Toast.LENGTH_LONG).show();

                                Intent contact = new Intent(Change_Profile.this, Change_Profile.class);
                                startActivity(contact);
                                finish();
                            }
                        }
                    } else {
                        Toast.makeText(Change_Profile.this, "Primary account cannot be deleted", Toast.LENGTH_LONG).show();
                    }
                } else {
                    boolean deleted = mydb.deleteuserprofile_details(user[0], profile_spinner.getSelectedItem().toString());
                    if (deleted) {
                        Toast.makeText(Change_Profile.this, "Profile deleted", Toast.LENGTH_LONG).show();

                        Intent contact = new Intent(Change_Profile.this, Change_Profile.class);
                        startActivity(contact);
                        finish();
                    }


                }
            }
        });
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean inserted = mydb.insert_currentuser(user[0],profile_spinner.getSelectedItem().toString());
                if (inserted) {
                    Toast.makeText(Change_Profile.this, "Profile has been set", Toast.LENGTH_LONG).show();

                    Intent contact = new Intent(Change_Profile.this, Change_Profile.class);
                    startActivity(contact);
                    finish();
                }

            }
        });


    }


    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            //backToast.show();
            startActivity(new Intent(Change_Profile.this,MainActivity.class));

        }
        backPressedTime = System.currentTimeMillis();
    }
}
