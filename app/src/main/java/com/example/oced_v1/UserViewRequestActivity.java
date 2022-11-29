package com.example.oced_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

public class UserViewRequestActivity extends AppCompatActivity {

    Button viewAll,pending,complete,canceled;
    FirebaseAuth auth ;
    RecyclerView recyclerView;
    UserRequestAdapter userRequestAdapter;
    List<RequestModel> requestModels;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_request);

        back = findViewById(R.id.back_UserViewRequest);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        viewAll = findViewById(R.id.user_BtnViewAll);
        pending = findViewById(R.id.user_BtnPending);
        complete = findViewById(R.id.user_BtnComplete);
        canceled = findViewById(R.id.user_BtnCancel);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String emailName = user.getEmail();

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        requestModels = new ArrayList<>();

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

        canceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCanceled();
            }
        });

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAll();
            }
        });

        all();

    }

    private void loadCanceled() {

        String status="Canceled";
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String getStatus=email+status;
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApStatusUid").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    userRequestAdapter = new UserRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(userRequestAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void all() {

        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        String email = user.getEmail();
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApEmail").equalTo(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    userRequestAdapter = new UserRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(userRequestAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadAll() {

        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        String email = user.getEmail();
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApEmail").equalTo(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    userRequestAdapter = new UserRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(userRequestAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadComplete() {

        String status="Approved";
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String getStatus=email+status;
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApStatusUid").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    userRequestAdapter = new UserRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(userRequestAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadPending() {

        String status="Pending";
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String getStatus=email+status;
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .orderByChild("ApStatusUid").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                requestModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestModel requestModel = ds.getValue(RequestModel.class);
                    requestModels.add(requestModel);
                    userRequestAdapter = new UserRequestAdapter(getApplicationContext(), requestModels);
                    recyclerView.setAdapter(userRequestAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}