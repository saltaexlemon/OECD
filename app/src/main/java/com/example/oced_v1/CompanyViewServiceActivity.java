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

public class CompanyViewServiceActivity extends AppCompatActivity {

    ImageView back;
    Button add;
    RecyclerView recyclerView;
    CompanyServiceAdapter companyServiceAdapter;
    List<ServiceModal> serviceModalList;
    FirebaseAuth auth ;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_view_service);

        back = findViewById(R.id.back_ViewService);

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
        serviceModalList = new ArrayList<>();
        
        LoadAll();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CompanyViewServiceActivity.this, CompanyAddServiceActivity.class));
            }
        });
        
        
    }

    private void LoadAll() {

        FirebaseUser user = auth.getCurrentUser();
        String email = user.getEmail();
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Service")
                .orderByChild("ApEmail").equalTo(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                serviceModalList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    ServiceModal serviceModal = ds.getValue(ServiceModal.class);
                    serviceModalList.add(serviceModal);
                    companyServiceAdapter = new CompanyServiceAdapter(getApplicationContext(), serviceModalList);
                    recyclerView.setAdapter(companyServiceAdapter);
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