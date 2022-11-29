package com.example.oced_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserRequestDetailsActivity extends AppCompatActivity {

    EditText txtName,txtAddress,txtState,txtContactNumber,txtEmail,txtStatus,txtRemark,txtCompanyEmail;
    ImageView imageView;
    FirebaseAuth auth ;
    DatabaseReference ref;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request_details);

        back = findViewById(R.id.back_DetailsRequest);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        txtName = findViewById(R.id.show_etName);
        txtAddress = findViewById(R.id.show_etAddress);
        txtState = findViewById(R.id.show_etState);
        txtContactNumber = findViewById(R.id.show_etContactNumber);
        txtEmail = findViewById(R.id.show_etEmail);
        txtStatus = findViewById(R.id.show_etStatus);
        txtRemark = findViewById(R.id.show_etRemark);
        txtCompanyEmail = findViewById(R.id.show_etCompanyEmail);
        imageView = findViewById(R.id.show_ImageView);

        String postFID = getIntent().getExtras().getString("ApFid");

        ref = FirebaseDatabase.getInstance().getReference("Request")
                .child(postFID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    String pImage = dataSnapshot.child("ApImage").getValue().toString();
                    Glide.with(UserRequestDetailsActivity.this).load(pImage).into(imageView);
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
                    String DeEmail = dataSnapshot.child("ApEmail").getValue().toString();
                    txtEmail.setText(DeEmail);
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
                    String DeState = dataSnapshot.child("ApState").getValue().toString();
                    txtState.setText(DeState);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeStatus = dataSnapshot.child("ApStatus").getValue().toString();
                    txtStatus.setText(DeStatus);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeRemark = dataSnapshot.child("ApRemark").getValue().toString();
                    txtRemark.setText(DeRemark);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DeCompanyEmail = dataSnapshot.child("ApCompanyEmail").getValue().toString();
                    txtCompanyEmail.setText(DeCompanyEmail);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}