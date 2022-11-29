package com.example.oced_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CompanyHomeActivity extends AppCompatActivity {

    ImageView logout;
    CardView service,emergency;
    Button viewRequest;
    TextView txtEmail;
    FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home);

        logout = findViewById(R.id.user_BtnLogout);
        service = findViewById(R.id.BtnService);
        emergency = findViewById(R.id.BtnEmergency);
        viewRequest = findViewById(R.id.BtnViewRequest);
        txtEmail = findViewById(R.id.txtEmail);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String emailName = user.getEmail();
        txtEmail.setText(emailName);

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CompanyHomeActivity.this, CompanyViewServiceActivity.class));
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CompanyHomeActivity.this, CompanyViewEmergencyActivity.class));
            }
        });


        viewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CompanyHomeActivity.this, CompanyViewRequestActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CompanyHomeActivity.this, MainActivity.class);
                startActivity(intent);
                auth.signOut();
                finish();
            }
        });


    }
}