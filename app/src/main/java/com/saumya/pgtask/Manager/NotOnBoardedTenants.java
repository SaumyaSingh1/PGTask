package com.saumya.pgtask.Manager;

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
import com.saumya.pgtask.R;

public class NotOnBoardedTenants extends AppCompatActivity {

    private EditText etName, etPhone, etRoom, etAmount;
    private Button btnSave , btnNext , btnAdd;
    private Integer i=1;
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
        btnAdd.setVisibility(View.INVISIBLE);
        fbDatabase=FirebaseDatabase.getInstance();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        UserId=firebaseUser.getUid().toString();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbReference=fbDatabase.getReference().child("PG").child(UserId).child("NotOnBoardedTenants").child("Tenant"+ i++ );
                Name=etName.getText().toString();
                Phone=etPhone.getText().toString();
                Room=etRoom.getText().toString();
                Amount=etAmount.getText().toString();
                dbReference.child("Name").setValue(Name);
                dbReference.child("Phone").setValue(Phone);
                dbReference.child("Room No").setValue(Room);
                dbReference.child("Rent Amount").setValue(Amount);
                Toast.makeText(getBaseContext() , "Tenant Added", Toast.LENGTH_SHORT).show();
                btnAdd.setVisibility(View.VISIBLE);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etName.setText("");
                etPhone.setText("");
                etAmount.setText("");
                etRoom.setText("");
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
        btnAdd=findViewById(R.id.btnAddMore);
        btnSave=findViewById(R.id.btSave);
        btnNext=findViewById(R.id.btnnext);
    }
}
