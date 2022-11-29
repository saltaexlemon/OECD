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

public class UserHomeActivity extends AppCompatActivity {

    Button viewInformation,viewRequest;
    ImageView profile;
    CardView service,emergency;
    TextView txtEmail;
    FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        viewInformation = findViewById(R.id.BtnViewInformation);
        viewRequest = findViewById(R.id.BtnViewRequest);
        profile = findViewById(R.id.user_BtnProfile);
        service = findViewById(R.id.BtnService);
        emergency = findViewById(R.id.BtnEmergency);
        txtEmail = findViewById(R.id.txtEmail);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String emailName = user.getEmail();
        txtEmail.setText(emailName);

        viewInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHomeActivity.this, UserViewInformationActivity.class));
            }
        });

        viewRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHomeActivity.this, UserViewRequestActivity.class));
            }
        });

        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHomeActivity.this, UserViewServiceActivity.class));
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHomeActivity.this, UserViewEmergencyActivity.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserHomeActivity.this, UserProfileActivity.class));
            }
        });

    }
}