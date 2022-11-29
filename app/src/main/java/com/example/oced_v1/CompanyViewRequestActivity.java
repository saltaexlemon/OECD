package com.example.oced_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class CompanyViewRequestActivity extends AppCompatActivity {

    Button viewAll,pending;
    FirebaseAuth auth ;
    RecyclerView recyclerView;
    CompanyRequestAdapter companyRequestAdapter;
    List<RequestModel> requestModels;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_view_request);

        back = findViewById(R.id.back_ViewRequest);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        viewAll = findViewById(R.id.user_BtnViewAll);
        pending = findViewById(R.id.user_BtnPending);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String emailName = user.getEmail();

        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        requestModels = new ArrayList<>();

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAllAppointment();
            }
        });
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPendingAppointment();
            }
        });

    }

    private void loadAllAppointment() {

        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        String email = user.getEmail();
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApCompanyEmail").equalTo(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    companyRequestAdapter = new CompanyRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(companyRequestAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadPendingAppointment() {

        String status="Pending";
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getEmail();
        String getStatus=email+status;
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApCompanyStatus").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    companyRequestAdapter = new CompanyRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(companyRequestAdapter);
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