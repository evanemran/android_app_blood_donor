package com.example.mymap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Trace;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFeedActivity extends AppCompatActivity {
    EditText txtName,txtPhone;
    Spinner spinnerBlood,spinnerBag;
    Button btnPost;
    DatabaseReference databaseReference;
    Spinner spinneraddarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);

        getSupportActionBar().setTitle("Add Request");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("feed");


        txtName = findViewById(R.id.txt_feedname);
        spinneraddarea = findViewById(R.id.txt_feedaddress);
        txtPhone = findViewById(R.id.txt_feedphone);
        spinnerBag = findViewById(R.id.spinnerbloodbag);
        spinnerBlood = findViewById(R.id.spinnerbloodgrp);
        btnPost = findViewById(R.id.btnpost);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddFeedActivity.this,R.layout.custom_spinner,getResources().getStringArray(R.array.blood_area));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneraddarea.setAdapter(myAdapter);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFeed();

            }
        });


    }

    private void addFeed()
    {
        String name = txtName.getText().toString().trim();
        String address = spinneraddarea.getSelectedItem().toString();
        String phone = txtPhone.getText().toString().trim();
        String bloodgrp = spinnerBlood.getSelectedItem().toString();
        String bloodbag = spinnerBag.getSelectedItem().toString();
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(address))
        {
            String id = databaseReference.push().getKey();
            Feed feed = new Feed(id,name,address,bloodgrp,phone,bloodbag);
            databaseReference.child(id).setValue(feed);
            Toast.makeText(this, "Posted", Toast.LENGTH_SHORT).show();
            AddFeedActivity.this.finish();

        }
        else
        {
            Toast.makeText(this, "Fields are Empty", Toast.LENGTH_SHORT).show();
        }
    }
}