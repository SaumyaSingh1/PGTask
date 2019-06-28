package com.saumya.pgtask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class ManagerData extends AppCompatActivity {

    String Name, Phone, PgName, pinCode, DateCreated , userId;
    private static final String TAG= "ManagerData";
    EditText etName, etPhone, etPgName,etPinCode , etDateCreated;
    Button btnSave;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_data);

        findallView();
        FirebaseApp.initializeApp(this);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

      //  userId= firebaseUser.getUid();
      //  Log.d(TAG, "Manager Id:"+ firebaseUser.getEmail());
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name=etName.getText().toString();
                Phone=etPhone.getText().toString();
                PgName=etPgName.getText().toString();
                pinCode=etPinCode.getText().toString();
                DateCreated=etDateCreated.getText().toString();
                verifyDetails();
                databaseReference.child("PG").child("PgDetails").child("Name").push().setValue(Name);
                databaseReference.child("PG").child("PgDetails").child("Phone").push().setValue(Phone);
                databaseReference.child("PG").child("PgDetails").child("PGName").push().setValue(PgName);
                databaseReference.child("PG").child("PgDetails").child("Pincode").push().setValue(pinCode);
                databaseReference.child("PG").child("PgDetails").child("DateCreated").push().setValue(DateCreated);
                Toast.makeText(getBaseContext(),"Data Saved Succesfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext() , AddTenantActivity.class));
            }
        });
    }
    private void verifyDetails(){
        if (Phone.length()!=10){
            etPhone.setError("Enter valid Phone Number");
        }else if (Phone.isEmpty()){
            etPhone.setError("Phone Number Required");
        }
    }

    private void findallView(){
        etName=findViewById(R.id.tvName);
        etPhone=findViewById(R.id.tvPhone);
        etPgName=findViewById(R.id.tvPg);
        etPinCode=findViewById(R.id.tvCode);
        etDateCreated=findViewById(R.id.tvDate);
        btnSave=findViewById(R.id.btnSave);
    }
}
