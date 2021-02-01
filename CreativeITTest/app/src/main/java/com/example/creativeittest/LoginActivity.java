package com.example.creativeittest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText usernm, passwrd;
    private Button login;
    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernm = findViewById(R.id.Myusername);
        passwrd = findViewById(R.id.Mypassword);

        login = findViewById(R.id.cirLoginButton);

        findViewById(R.id.forget).setOnClickListener(this);
        findViewById(R.id.registerMe).setOnClickListener(this);

        //Button Action
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin(usernm.getText().toString().trim(),passwrd.getText().toString());
            }
        });
    }

    private boolean isValid(String username, String password){
        if (username.isEmpty()){
            usernm.setError("Username Required");
            usernm.requestFocus();
            return true;
        }
        if (password.isEmpty()){
            passwrd.setError("Password Required");
            passwrd.requestFocus();
            return true;
        }
        return false;
    }
    private void checkLogin(String user, String pass){
        if(user.isEmpty()||pass.isEmpty()){
            Toast.makeText(getApplicationContext(),"Enter Username, Password first",Toast.LENGTH_SHORT).show();
        }
        else {
            databaseHelper = new DatabaseHelper(this);
            //Toast.makeText(getApplicationContext(),"Will login soon!",Toast.LENGTH_SHORT).show();
            if(databaseHelper.checkUser(user, pass)){
                Intent intent = new Intent(this,ProfileActivity.class);
                intent.putExtra("name",user);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(),"Login failed!!",Toast.LENGTH_SHORT).show();
            }
        }
        //
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forget:
                Toast.makeText(getApplicationContext(),"Contact Developer",Toast.LENGTH_SHORT).show();
                break;
            case R.id.registerMe:
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                //finish();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
