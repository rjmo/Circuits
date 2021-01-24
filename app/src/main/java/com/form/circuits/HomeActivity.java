package com.form.circuits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class HomeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        new Handler().postDelayed(new Runnable() {
            @Override

            public void run() {
                Intent i = new Intent(HomeActivity.this, AccueilActivity.class);
                startActivity(i);
                finish();

            }
        }, 2*1000);
    }
}