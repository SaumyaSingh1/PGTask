package com.saumya.pgtask.Tenant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saumya.pgtask.R;

public class TenantProfileEdit extends AppCompatActivity {

    DatabaseReference databaseReference;
    private TextView tvName, tvPhone;
    private EditText etName , etPhone ;
    private FirebaseUser firebaseUser;
    private ImageView imageOne,imageTwo;
    private Button btnMove , Save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_profile_edit);
        tvName = findViewById(R.id.tvShowName);
        tvPhone = findViewById(R.id.tvShowPhone);
        Save = findViewById(R.id.btn);
        btnMove = findViewById(R.id.btnMove);
        imageOne = findViewById(R.id.imgName);
        imageTwo = findViewById(R.id.imgPhone);
        etName = findViewById(R.id.etNewName);
        etPhone = findViewById(R.id.etNewPhone);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
        String number = firebaseUser.getPhoneNumber().toString();
        tvPhone.setText(number);
        }
        databaseReference= FirebaseDatabase.getInstance().getReference().child("PG").child("aRYBPHaMsPh1XeMoXHv4QuuALOs1").child("NotOnBoardedTenants").child("Tenant1");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                dataSnapshot.getValue();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }) ;
        imageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvName.setVisibility(View.GONE);
                etName.setVisibility(View.VISIBLE);
                imageOne.setVisibility(View.GONE);
            }
        });
        imageTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPhone.setVisibility(View.GONE);
                etPhone.setVisibility(View.VISIBLE);
                imageTwo.setVisibility(View.GONE);
            }
        });
        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), UploadPicture.class));
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext() , "Details Updated" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
