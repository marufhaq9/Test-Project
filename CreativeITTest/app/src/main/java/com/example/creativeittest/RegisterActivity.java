package com.example.creativeittest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {
    private EditText uname, upass, uemail, uphone;
    private DatabaseHelper databaseHelper;
    ImageView imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        uname = findViewById(R.id.regName);
        upass = findViewById(R.id.regPassword);
        uemail = findViewById(R.id.regNEmail);
        uphone = findViewById(R.id.regPhone);
        ImageButton register = findViewById(R.id.register);
        register.setOnClickListener(v -> registerMe(uname.getText().toString().trim(), uemail.getText().toString().trim(),
                uphone.getText().toString().trim(), upass.getText().toString()));
        //
        imageButton = findViewById(R.id.backImg);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.super.onBackPressed();
            }
        });

    }

    private void registerMe(String username, String email, String phone, String pass) {
        if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Fill up all the forms first", Toast.LENGTH_SHORT).show();
        } else {
            RelativeLayout relativeLayout = findViewById(R.id.relLay);
            databaseHelper = new DatabaseHelper(this);
            int l = databaseHelper.addUser(username, email, phone, pass);
            if (l == 1) {
                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Error in Registration!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
