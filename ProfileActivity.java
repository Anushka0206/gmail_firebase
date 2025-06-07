package com.example.assignment_5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class ProfileActivity extends AppCompatActivity {

    private EditText etName, etBloodType, etCity, etPhone;
    private Button btnUpdate, btnLogout;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etName = findViewById(R.id.etName);
        etBloodType = findViewById(R.id.etBloodType);
        etCity = findViewById(R.id.etCity);
        etPhone = findViewById(R.id.etPhone);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnLogout = findViewById(R.id.btnLogout);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        if (currentUser != null) {
            loadUserProfile();
        }

        btnUpdate.setOnClickListener(view -> updateProfile());

        btnLogout.setOnClickListener(view -> {
            auth.signOut();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void loadUserProfile() {
        String userId = currentUser.getUid();

        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    etName.setText(snapshot.child("name").getValue(String.class));
                    etBloodType.setText(snapshot.child("bloodType").getValue(String.class));
                    etCity.setText(snapshot.child("city").getValue(String.class));
                    etPhone.setText(snapshot.child("phone").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateProfile() {
        String userId = currentUser.getUid();
        String updatedCity = etCity.getText().toString();
        String updatedPhone = etPhone.getText().toString();

        databaseReference.child(userId).child("city").setValue(updatedCity);
        databaseReference.child(userId).child("phone").setValue(updatedPhone);

        Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
    }
}
