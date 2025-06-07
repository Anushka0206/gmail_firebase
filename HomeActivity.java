package com.example.assignment_5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Button btnFindDonors, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnFindDonors = findViewById(R.id.btnFindDonors);
        btnProfile = findViewById(R.id.btnProfile);

        btnFindDonors.setOnClickListener(view ->
                startActivity(new Intent(HomeActivity.this, SearchActivity.class))
        );

        btnProfile.setOnClickListener(view ->
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class))
        );
    }
}
