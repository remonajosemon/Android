package com.example.properapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText ed_email,ed_password;
    Button login;
    FirebaseAuth mAuth;
    DatabaseHelper mydb;
    ProgressDialog mProgress;
    private long backPressedTime;
    private Toast backToast;
    SharedPreferences sharedpreferences;
    TextView forgot_password;

    int autoSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        login = findViewById(R.id.btn_login);
        forgot_password = findViewById(R.id.forgot);
        mAuth = FirebaseAuth.getInstance();
        mydb =new DatabaseHelper(getApplicationContext());
        mProgress =new ProgressDialog(this);
        String titleId="Signing in...";
        mProgress.setTitle(titleId);
        mProgress.setMessage("Please Wait...");
        sharedpreferences = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        int j = sharedpreferences.getInt("key", 0);
        if(j > 0){
            Intent activity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(activity);
            finish();
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_email = ed_email.getText().toString();

                boolean inserted = mydb.insertuserprofile_details(txt_email, "Primary");
                boolean insert =mydb.insert_currentuser(txt_email,"Primary");
                String txt_password = ed_password.getText().toString();
                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password))
                {
                    Toast.makeText(LoginActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                }
                else {
                    mProgress.show();
                    userLogin(txt_email, txt_password);
                }

            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ResetPassword.class));
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
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    private void userLogin(String email, String password) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    if(mAuth.getCurrentUser().isEmailVerified())
                    {

                        autoSave = 1;
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putInt("key", autoSave);
                        editor.apply();
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        finish();
                      //  mProgress.dismiss();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Please Verify Your Email Address", Toast.LENGTH_LONG).show();

                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });


    }


    public void moveToRegistration(View view) {
        startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
        finish();
    }
}
