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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText nameSignUp = findViewById(R.id.name_signup);
        EditText emailSignUp = findViewById(R.id.email_signup);
        EditText passwordSignUp = findViewById(R.id.password_signup);
        EditText phoneNumberSignUp = findViewById(R.id.phone_number_signup);
        Button submitSignUp = findViewById(R.id.submit_signup);
        TextView signUpToLogin = findViewById(R.id.signup_to_login);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        signUpToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
        submitSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameSignUp.getText().toString().equals("")||emailSignUp.getText().toString().equals("")||passwordSignUp.getText().toString().equals("")||phoneNumberSignUp.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Fill all the required fields!", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User(nameSignUp.getText().toString(), emailSignUp.getText().toString(), passwordSignUp.getText().toString(), phoneNumberSignUp.getText().toString());
                addDataToFirestore(user);
                firebaseAuth.createUserWithEmailAndPassword(emailSignUp.getText().toString(),passwordSignUp.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(nameSignUp.getText().toString())
                                        .build();
                                assert currentUser != null;
                                currentUser.updateProfile(profileUpdates);
                                Toast.makeText(SignUpActivity.this, "Account creation successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUpActivity.this, "Error! Try again later.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            }
        });
    }

    private void addDataToFirestore(User user) {
        CollectionReference usersCollectionRef = firestore.collection("/users");
        usersCollectionRef.document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SignUpActivity.this, "Sign Up successful!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Error! Try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}