package com.example.properapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    Toolbar toolbar;
    Button resetButton;
    EditText email_editText;
    FirebaseAuth mAuth;
    private long backPressedTime;
    private Toast backToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mAuth = FirebaseAuth.getInstance();
        resetButton = (Button)findViewById(R.id.btn_reset);
        email_editText=(EditText)findViewById(R.id.ed_email_edittext);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email_editText.getText().toString();
                if(TextUtils.isEmpty(userEmail))
                {
                    Toast.makeText(ResetPassword.this, "Please Enter Your Valid Email ID", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ResetPassword.this, "Please Check your Email to Reset Password", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPassword.this,LoginActivity.class));
                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(ResetPassword.this, "Error : "+message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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
           // backToast.show();
            startActivity(new Intent(ResetPassword.this,LoginActivity.class));
            finish();
        }
        backPressedTime = System.currentTimeMillis();
    }
}

