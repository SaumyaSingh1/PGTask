package com.saumya.pgtask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.saumya.pgtask.Manager.SignUpActivity;
import com.saumya.pgtask.Tenant.TenantAuthActivity;

public class MainActivity extends AppCompatActivity {

    TextView Tenant , Manager ;
    EditText Email, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Tenant=findViewById(R.id.tvTenant);
        Manager=findViewById(R.id.tvManager);
        Email=findViewById(R.id.editmail);
        Password=findViewById(R.id.editpwd);
        Manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SignUpActivity.class));
            }
        });
        Tenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), TenantAuthActivity.class));
            }
        });



    }
}
