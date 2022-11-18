package com.example.oecd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDetailsEmergencyPostActivity extends AppCompatActivity {

    EditText txtName,txtOfficeNumber,txtContactNumber,txtAddress,txtState,txtWorkingDay,txtWorkingHour,txtDec;
    ImageView imageView;
    DatabaseReference ref;
    Button whatsApp,map,call;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_emergency_post);

        Toolbar toolbar = findViewById(R.id.UserViewEmergencyPageToolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        txtName = findViewById(R.id.show_etName);
        txtOfficeNumber = findViewById(R.id.show_etOfficeNumber);
        txtContactNumber = findViewById(R.id.show_etContactNumber);
        txtAddress = findViewById(R.id.show_etAddress);
        txtState = findViewById(R.id.show_etState);
        txtWorkingDay = findViewById(R.id.show_etWorkingDay);
        txtWorkingHour = findViewById(R.id.show_etWorkingHour);
        txtDec = findViewById(R.id.show_etDescription);
        imageView = findViewById(R.id.show_imageView);
        whatsApp = findViewById(R.id.whatsappBtn);
        map = findViewById(R.id.mapBtn);
        call = findViewById(R.id.callBtn);

        String postFID = getIntent().getExtras().getString("ApFid");

        ref = FirebaseDatabase.getInstance().getReference("Emergency")
                .child(postFID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    String pImage = dataSnapshot.child("ApImage").getValue().toString();
                    Glide.with(UserDetailsEmergencyPostActivity.this).load(pImage).into(imageView);
                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeName = dataSnapshot.child("ApName").getValue().toString();
                    txtName.setText(DeName);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeApOfficeNumber = dataSnapshot.child("ApOfficeNumber").getValue().toString();
                    txtOfficeNumber.setText(DeApOfficeNumber);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeContact = dataSnapshot.child("ApContactNumber").getValue().toString();
                    txtContactNumber.setText(DeContact);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeAddress = dataSnapshot.child("ApAddress").getValue().toString();
                    txtAddress.setText(DeAddress);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeApState = dataSnapshot.child("ApState").getValue().toString();
                    txtState.setText(DeApState);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeApWorkingDay= dataSnapshot.child("ApWorkingDay").getValue().toString();
                    txtWorkingDay.setText(DeApWorkingDay);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeApWorkingHour = dataSnapshot.child("ApWorkingHour").getValue().toString();
                    txtWorkingHour.setText(DeApWorkingHour);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeApDescription = dataSnapshot.child("ApDescription").getValue().toString();
                    txtDec.setText(DeApDescription);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        whatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number = txtContactNumber.getText().toString().trim();

                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + "" + number + "?body=" + ""));
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);

            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String map = txtAddress.getText().toString().trim();

                Uri uri = Uri.parse("http://maps.google.co.in/maps?q="+map);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);

            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String call = txtOfficeNumber.getText().toString().trim();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+call));
                startActivity(intent);

            }
        });


    }
}