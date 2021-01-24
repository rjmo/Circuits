package com.form.circuits;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Intent i = new Intent(AccueilActivity.this, MainActivity.class);

        final Button button = findViewById(R.id.buttonAccu);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccueilActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}