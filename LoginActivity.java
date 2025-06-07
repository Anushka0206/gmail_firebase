package com.example.assignment_5;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword, etName, etPhone, etCity;
    private Spinner spinnerBloodType;
    ArrayList<String> arr;
    ArrayAdapter<String> adapter;
    private Button btnLogin;
    private TextView tvRegister;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private boolean isRegistering = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etCity = findViewById(R.id.etCity);
        etPassword = findViewById(R.id.etPassword);
        spinnerBloodType = findViewById(R.id.spinnerBloodType);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        arr=new ArrayList<>();
        arr.add("A+");
        arr.add("A-");
        arr.add("B+");
        arr.add("B-");
        arr.add("AB+");
        arr.add("AB-");
        arr.add("O+");
        arr.add("O-");
        adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,arr);
        spinnerBloodType.setAdapter(adapter);
        tvRegister.setOnClickListener(view -> toggleRegister());

        btnLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Enter all details", Toast.LENGTH_SHORT).show();
                return;
            }

            if (isRegistering) {
                registerUser();
            } else {
                loginUser(email, password);
            }
        });

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }

    private void toggleRegister() {
        isRegistering = !isRegistering;
        int visibility = isRegistering ? View.VISIBLE : View.GONE;
        etName.setVisibility(visibility);
        etPhone.setVisibility(visibility);
        etCity.setVisibility(visibility);
        spinnerBloodType.setVisibility(visibility);
        btnLogin.setText(isRegistering ? "Register" : "Login");
        tvRegister.setText(isRegistering ? "Already a user? Login here" : "New User? Register Here");
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String bloodType = spinnerBloodType.getSelectedItem().toString();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(city) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                        User user = new User(userId, name, email, phone, bloodType, city);
                        databaseReference.child(userId).setValue(user);
                        Toast.makeText(LoginActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
