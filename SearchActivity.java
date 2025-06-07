package com.example.assignment_5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText etCity;
    private Spinner spinnerBloodType;
    private Button btnSearch;
    private ListView listViewDonors;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> donorList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etCity = findViewById(R.id.etCity);
        spinnerBloodType = findViewById(R.id.spinnerBloodType);
        btnSearch = findViewById(R.id.btnSearch);
        listViewDonors = findViewById(R.id.listViewDonors);

        // Blood Type Options
        String[] bloodTypes = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bloodTypes);
        spinnerBloodType.setAdapter(spinnerAdapter);

        donorList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, donorList);
        listViewDonors.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        btnSearch.setOnClickListener(view -> searchDonors());

        listViewDonors.setOnItemClickListener((parent, view, position, id) -> {
            String selectedDonor = donorList.get(position);
            String phoneNumber = selectedDonor.split("\n")[1]; // Extract phone number
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
        });
    }

    private void searchDonors() {
        String city = etCity.getText().toString().trim();
        String bloodType = spinnerBloodType.getSelectedItem().toString();

        if (city.isEmpty()) {
            Toast.makeText(this, "Please enter a city", Toast.LENGTH_SHORT).show();
            return;
        }

        Query query = databaseReference.orderByChild("city").equalTo(city);
        donorList.clear();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String name = data.child("name").getValue(String.class);
                    String phone = data.child("phone").getValue(String.class);
                    String userBloodType = data.child("bloodType").getValue(String.class);

                    if (userBloodType.equals(bloodType)) {
                        donorList.add(name + "\n" + phone);
                    }
                }
                adapter.notifyDataSetChanged();
                if (donorList.isEmpty()) {
                    Toast.makeText(SearchActivity.this, "No donors found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
