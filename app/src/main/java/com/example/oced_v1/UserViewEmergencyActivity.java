package com.example.oced_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserViewEmergencyActivity extends AppCompatActivity {

    ImageView back;
    RecyclerView recyclerView;
    UserEmergencyAdapter userEmergencyAdapter;
    List<EmergencyModal> emergencyModalList;
    ImageView search;
    EditText txtState;
    String [] state = {"Select State","View All","Kuala Lumpur","Labuan","Putrajaya","Johor","Kedah",
            "Kelantan","Melaka","Negeri Sembilan","Pahang","Perak","Perlis","Penang","Sabah"
            ,"Sarawak","Selangor","Terengganu"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_emergency);

        back = findViewById(R.id.back_UserViewEmergency);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        search = findViewById(R.id.BtnSearch);
        txtState = findViewById(R.id.etState);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        emergencyModalList = new ArrayList<>();

        loadAll();

        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserViewEmergencyActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,state);
        adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).equals("Select State")){
                    //empty
                }
                else {
                    String value = adapterView.getItemAtPosition(i).toString();
                    txtState.setText(value);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = txtState.getText().toString();
                Search(searchText);

                String viewAll = "View All";

                if (searchText.equals(viewAll)){
                    loadAll();
                }
            }
        });

    }

    private void Search(String searchText) {

        Toast.makeText(UserViewEmergencyActivity.this, "Searching", Toast.LENGTH_SHORT).show();

        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Emergency")
                .orderByChild("ApState").startAt(searchText).endAt(searchText + "\uf8ff");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                emergencyModalList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    EmergencyModal emergencyModal = ds.getValue(EmergencyModal.class);
                    emergencyModalList.add(emergencyModal);
                    userEmergencyAdapter = new UserEmergencyAdapter(getApplicationContext(), emergencyModalList);
                    recyclerView.setAdapter(userEmergencyAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void loadAll() {

        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Emergency");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                emergencyModalList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    EmergencyModal emergencyModal = ds.getValue(EmergencyModal.class);
                    emergencyModalList.add(emergencyModal);
                    userEmergencyAdapter = new UserEmergencyAdapter(getApplicationContext(), emergencyModalList);
                    recyclerView.setAdapter(userEmergencyAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}