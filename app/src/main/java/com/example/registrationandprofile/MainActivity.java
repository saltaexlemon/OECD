package com.example.registrationandprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //Firebase
    private FirebaseAuth mAuth;

    //Login Button
    private Button btnLogin;

    //Navigate to register
    private TextView register;

    //For login page
    private EditText etEmail, etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        register = (TextView) findViewById(R.id.registerText);
        register.setOnClickListener(this);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        etEmail = (EditText) findViewById(R.id.etLoginMail);
        etPass = (EditText) findViewById(R.id.etLoginPass);
    }

    @Override
    public void onClick(View v) {
         switch(v.getId()){
             case R.id.registerText:
                 Intent intent = new Intent (this, RegisterUser.class);
                 startActivity(intent);
                 break;

             case R.id.btnLogin:
                 userLogin();
                 break;
         }
    }

    private void userLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPass.getText().toString().trim();

        if(email.isEmpty()) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            etPass.setError("Password is required!");
            etPass.requestFocus();
            return;
        }

        if(password.length()<6) {
            etPass.setError("Password is too short!");
            etPass.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    startActivity(new Intent(MainActivity.this, UserProfile.class));
                } else {
                    Toast.makeText(MainActivity.this, "Failed to login!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
