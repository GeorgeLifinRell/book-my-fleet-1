package com.example.townbus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailLogin = findViewById(R.id.email_login);
        EditText passwordLogin = findViewById(R.id.password_login);
        Button submitLogin = findViewById(R.id.submit_login);
        TextView loginToSignUp = findViewById(R.id.login_to_signup);
        TextView otpLogin = findViewById(R.id.login_with_otp);
        firebaseAuth = FirebaseAuth.getInstance();

        loginToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
                finish();
            }
        });

        otpLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), OTPLoginActivity.class));
                finish();
            }
        });

        submitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailLogin.getText().toString().equals("")||passwordLogin.getText().toString().equals("")) {
                    Toast.makeText(LoginActivity.this, "Fill all the credentials", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(emailLogin.getText().toString(), passwordLogin.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                assert currentUser != null;
                                Toast.makeText(LoginActivity.this, "Login successful! Welcome, " + currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "INVALID CREDENTIALS!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}