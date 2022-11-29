package com.example.oced_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CompanyViewEmergencyActivity extends AppCompatActivity {

    ImageView back;
    Button add;
    RecyclerView recyclerView;
    CompanyEmergencyAdapter companyEmergencyAdapter;
    List<EmergencyModal> emergencyModalList;
    FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_view_emergency);

        back = findViewById(R.id.back_ViewEmergency);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        add = findViewById(R.id.btnAdd);
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        emergencyModalList = new ArrayList<>();

        LoadAll();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CompanyViewEmergencyActivity.this, CompanyAddEmergencyServiceActivity.class));
            }
        });

    }

    private void LoadAll() {

        FirebaseUser user = auth.getCurrentUser();
        String email = user.getEmail();
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Emergency")
                .orderByChild("ApEmail").equalTo(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                emergencyModalList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    EmergencyModal emergencyModal = ds.getValue(EmergencyModal.class);
                    emergencyModalList.add(emergencyModal);
                    companyEmergencyAdapter = new CompanyEmergencyAdapter(getApplicationContext(), emergencyModalList);
                    recyclerView.setAdapter(companyEmergencyAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}