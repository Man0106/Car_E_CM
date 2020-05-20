package com.example.car_e_cm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class DashboardActivity extends AppCompatActivity {
    ImageButton Nota, Chart, Exit, Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Nota    = findViewById(R.id.imgbtnNota);
        Chart   = findViewById(R.id.imgbtnChart);
        Exit    = findViewById(R.id.imgbtnLogout);
        Profile = findViewById(R.id.imgbtnProfile);

        Nota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(DashboardActivity.this, NotaBidangActivity.class);
                startActivity(a);
            }
        });

        Chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent();
            }
        });

    }
}
