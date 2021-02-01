package com.example.creativeittest;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    TextView usernm;
    String nm;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        usernm = findViewById(R.id.userName);

        Bundle extras;
        extras = getIntent().getExtras();
        if(!extras.isEmpty()){
            nm = extras.getString("name");
        }
        //DatabaseHelper databaseHelper = new DatabaseHelper(this);
        //UserModel userModel = new UserModel();
        //databaseHelper.getSingleUser(nm);
        usernm.setText(nm);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
