package com.saumya.pgtask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotOnBoardedTenants extends AppCompatActivity {

    private EditText etName, etPhone, etRoom, etAmount;
    private Button btnSave , btnNext;
    FirebaseUser firebaseUser;
    String UserId;
    FirebaseDatabase fbDatabase;
    DatabaseReference dbReference;
    String Name , Phone , Room , Amount ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_on_boarded_tenants);
        findAllViews();
        fbDatabase=FirebaseDatabase.getInstance();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        UserId=firebaseUser.getUid().toString();
        dbReference=fbDatabase.getReference().child("PG").child(UserId);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name=etName.getText().toString();
                Phone=etPhone.getText().toString();
                Room=etRoom.getText().toString();
                Amount=etAmount.getText().toString();
                dbReference.child("NotOnBoardedTenants").child("Name").push().setValue(Name);
                dbReference.child("NotOnBoardedTenants").child("Phone").push().setValue(Phone);
                dbReference.child("NotOnBoardedTenants").child("Room No").push().setValue(Room);
                dbReference.child("NotOnBoardedTenants").child("Rent Amount").push().setValue(Amount);
                Toast.makeText(getBaseContext() , "Tenant Added", Toast.LENGTH_SHORT).show();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , TenantListActivity.class));
            }
        });
    }
    private void findAllViews(){
        etName=findViewById(R.id.etName);
        etAmount=findViewById(R.id.etAmount);
        etPhone=findViewById(R.id.etPhone);
        etRoom=findViewById(R.id.etRoom);
        btnSave=findViewById(R.id.btSave);
        btnNext=findViewById(R.id.btnnext);
    }
}
