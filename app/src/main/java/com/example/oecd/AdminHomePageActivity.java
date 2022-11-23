package com.example.oecd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminHomePageActivity extends AppCompatActivity {

    Button Service,Emergency,request;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        Emergency = findViewById(R.id.openEmergency);
        Service = findViewById(R.id.openService);
        logout = findViewById(R.id.Admin_BtnLogout);
        request = findViewById(R.id.gotoRequest);

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePageActivity.this, AdminViewRequestActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomePageActivity.this, StartingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });


        Emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePageActivity.this, AdminViewEmergencyActivity.class));
            }
        });

        Service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePageActivity.this, AdminViewServiceActivity.class));
            }
        });

    }
}