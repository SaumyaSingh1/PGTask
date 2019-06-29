package com.saumya.pgtask.Manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saumya.pgtask.Manager.AddTenantActivity;
import com.saumya.pgtask.R;

public class ManagerData extends AppCompatActivity {

    String Name, Phone, PgName, pinCode, DateCreated , userId;
    private static final String TAG= "ManagerData";
    EditText etName, etPhone, etPgName,etPinCode , etDateCreated;
    Button btnSave;
    FirebaseUser firebaseUser;
    String UserId;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_data);

        findallView();
        FirebaseApp.initializeApp(this);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        userId=firebaseUser.getUid().toString();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("PG").child(userId);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name=etName.getText().toString();
                Phone=etPhone.getText().toString();
                PgName=etPgName.getText().toString();
                pinCode=etPinCode.getText().toString();
                DateCreated=etDateCreated.getText().toString();
                verifyDetails();
                databaseReference.child("PgDetails").child("Name").setValue(Name);
                databaseReference.child("PgDetails").child("Phone").setValue(Phone);
                databaseReference.child("PgDetails").child("PGName").setValue(PgName);
                databaseReference.child("PgDetails").child("Pincode").setValue(pinCode);
                databaseReference.child("PgDetails").child("DateCreated").setValue(DateCreated);
                Toast.makeText(getBaseContext(),"Data Saved Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext() , AddTenantActivity.class));
            }
        });
    }
    private void verifyDetails(){
        if (Phone.length()!=10){
            etPhone.setError("Enter valid Phone Number");
        }else if (Phone.isEmpty()){
            etPhone.setError("Phone Number Required");
        }else if (!Patterns.PHONE.matcher(Phone).matches()){
            etPhone.setError("Enter valid Phone Number");
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
