package com.example.oecd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class UserHomePageActivity extends AppCompatActivity {

    ImageView logout;
    FirebaseAuth mAuth;
    Button Service,Emergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        logout = findViewById(R.id.user_BtnLogout);
        mAuth = FirebaseAuth.getInstance();
        Emergency = findViewById(R.id.openEmergency);
        Service = findViewById(R.id.openService);

        Emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHomePageActivity.this, UserViewEmergencyActivity.class));
            }
        });

        Service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHomePageActivity.this, UserViewServiceActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserHomePageActivity.this, MainActivity.class);
                startActivity(intent);
                mAuth.signOut();
                finish();

            }
        });

    }
}