package com.example.registrationandprofile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private EditText etRegisterEmail, etRegisterPassword,
            etPostalCode, etState, etAddressLine2,
            etFullName, etAddressLine1;
    private TextView loginNow;
    private Button registerbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        registerbtn = (Button) findViewById(R.id.registerbtn);
        registerbtn.setOnClickListener(this);

        loginNow = (TextView) findViewById(R.id.loginNow);
        loginNow.setOnClickListener(this);

        etRegisterEmail = (EditText) findViewById(R.id.etRegisterEmail);
        etRegisterPassword = (EditText) findViewById(R.id.etRegisterEmail);
        etPostalCode = (EditText) findViewById(R.id.etPostalCode);
        etState = (EditText) findViewById(R.id.etState);
        etAddressLine1 = (EditText) findViewById(R.id.etAddressLine1);
        etAddressLine2 = (EditText) findViewById(R.id.etAddressLine2);
        etFullName = (EditText) findViewById(R.id.etFullName);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.registerbtn:
                registerUser();
                break;

            case R.id.loginNow:
                Intent intent = new Intent (this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void registerUser() {
        String email = etRegisterEmail.getText().toString().trim();
        String password = etRegisterPassword.getText().toString().trim();
        String fullname =etFullName.getText().toString().trim();
        String add1 = etAddressLine1.getText().toString().trim();
        String add2 = etAddressLine2.getText().toString().trim();
        String state = etState.getText().toString().trim();
        String postal = etPostalCode.getText().toString().trim();

        if (fullname.isEmpty()){
            etFullName.setError("Full name is required!");
            etFullName.requestFocus();
            return;
        }

        if(email.isEmpty()) {
            etRegisterEmail.setError("is required!");
            etRegisterEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etRegisterEmail.setError("Provide valid email address.");
        }

        if(password.isEmpty()) {
            etRegisterPassword.setError("is required!");
            etRegisterPassword.requestFocus();
            return;
        }

        if(add1.isEmpty()) {
            etAddressLine1.setError("is required!");
            etAddressLine1.requestFocus();
            return;
        }

        if(add2.isEmpty()) {
            etAddressLine2.setError("is required!");
            etAddressLine2.requestFocus();
            return;
        }

        if(state.isEmpty()) {
            etState.setError("is required!");
            etState.requestFocus();
            return;
        }

        if(postal.isEmpty()) {
            etPostalCode.setError("is required!");
            etPostalCode.requestFocus();
            return;
        }

        if(password.length() < 6) {
            etRegisterPassword.setError("Password is too short!");
            etRegisterPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(email, fullname, add1, add2, state, postal);

                            FirebaseDatabase.getInstance("https://software-development-4e56c-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterUser.this, "User has been registered", Toast.LENGTH_LONG).show();
                                                finish();
                                            } else {
                                                Toast.makeText(RegisterUser.this, "Registration has been failed!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            Toast.makeText(RegisterUser.this, "Registration has been failed!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
