package com.example.oecd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminViewRequestActivity extends AppCompatActivity {

    Button viewAll,pending,complete;
    RecyclerView recyclerView;
    AdminRequestAdapter adminRequestAdapter;
    List<RequestModel> requestModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_request);

        Toolbar toolbar = findViewById(R.id.AdminViewRequesrPageToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });


        viewAll = findViewById(R.id.user_BtnViewAll);
        pending = findViewById(R.id.user_BtnPending);
        complete = findViewById(R.id.user_BtnComplete);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        requestModels = new ArrayList<>();

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAll();
            }
        });

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPending();
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadComplete();
            }
        });

    }

    private void loadComplete() {

        String status = "Approved";
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApStatus").equalTo(status);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    adminRequestAdapter = new AdminRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(adminRequestAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadAll() {

        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Request");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    adminRequestAdapter = new AdminRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(adminRequestAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadPending() {

        String status = "Pending";
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApStatus").equalTo(status);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    adminRequestAdapter = new AdminRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(adminRequestAdapter);
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