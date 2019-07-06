package com.saumya.pgtask.Manager;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saumya.pgtask.R;

import java.util.ArrayList;

public class TenantListActivity extends AppCompatActivity {
    ListView listView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    TextView textView;

    ArrayList<TenantDataModel> tenantDataModelArrayList;
    TenantAdapter tenantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_list);


        textView=findViewById(R.id.textView2);

        listView=findViewById(R.id.lv);

        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        tenantDataModelArrayList = new ArrayList<>();

        databaseReference=firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/NotOnBoardedTenants");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            tenantDataModelArrayList.clear();

            for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                /*will loop through each every phone number*/
                TenantDataModel tenantDataModel = snapshot.getValue(TenantDataModel.class);
                tenantDataModelArrayList.add(tenantDataModel);
            }
                tenantAdapter = new TenantAdapter(getBaseContext() , R.layout.activity_tenant_adapter , tenantDataModelArrayList);
                listView.setAdapter(tenantAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e("TAG", databaseError.getMessage());
            }
        });
    }

}
