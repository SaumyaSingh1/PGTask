package com.saumya.pgtask.Tenant;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.saumya.pgtask.Manager.TenantDataModel;
import com.saumya.pgtask.R;

import java.util.concurrent.TimeUnit;

public class TenantAuthActivity extends AppCompatActivity {

    private EditText etTenantPhone , etTenantCode;

    private TenantDataModel tenantDataModel;
    private FirebaseAuth mAuth;
    private boolean mVerificationInProgress=false;
    private String mVerificationId;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference ,databaseReference1 , databaseReference2 ;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private Button btnSignUp,btnSignIn, btnProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_auth);
        findAllViews();
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        firebaseUser=mAuth.getCurrentUser();

        databaseReference=firebaseDatabase.getReference();
        databaseReference1=firebaseDatabase.getReference("PG"+"/Uc73EXIXqZQjqmAZ5trKcLNRaRL2" + "/NotOnBoardedTenants");
        databaseReference2 =firebaseDatabase.getReference("PG"+"/Uc73EXIXqZQjqmAZ5trKcLNRaRL2").child("OnBoardedTenants").child(firebaseUser.getUid());

        mCallBacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                /*This callback will be called if phone number is instantly verified without receiving or sending any OTP*/
                Log.d("TenantAuth","Credentials"+ phoneAuthCredential);
                mVerificationInProgress=false;
                signInWithPhoneCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
            /*This callback is made if an invalid request for verification is made*/
                /*For example if phone number is invalid*/
                mVerificationInProgress=false ;
                if (e instanceof FirebaseAuthInvalidCredentialsException){
                    etTenantPhone.setError("Invalid Phone Number");
                    Toast.makeText(getBaseContext(),"Invalid Phone Number",Toast.LENGTH_SHORT).show();
                }else if (e instanceof FirebaseTooManyRequestsException){
                    Toast.makeText(getBaseContext(),"Too many Request made",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                Log.d("Code:", "VerifyCode:"+ s);
                mResendToken=forceResendingToken;
                mVerificationId=s;
            }
        };

        final String phone = etTenantPhone.getText().toString();


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if (!validateNumber()){
                return;
            }
            VerifyWithDataBase();

            }
        });



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String code=etTenantCode.getText().toString();
            if (TextUtils.isEmpty(code)){
                etTenantCode.setError("Cannot be empty");
                return;
            }
            verifyPhoneWithCode(mVerificationId ,code);
                Toast.makeText(getBaseContext() , "Verified and logged in Successfully", Toast.LENGTH_SHORT).show();

            }
        });

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getBaseContext() , "Logged In", Toast.LENGTH_SHORT).show();

                databaseReference.setValue(tenantDataModel) ;

                databaseReference.child("Tenants").child(firebaseUser.getUid()).child("Details").child(firebaseUser.getPhoneNumber()).setValue( tenantDataModel);
                startActivity(new Intent(getApplicationContext() , TenantProfileEdit.class ));

            }
        });

    }
    private void VerifyWithDataBase(){

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(etTenantPhone.getText().toString())){

                    tenantDataModel = dataSnapshot.getValue(TenantDataModel.class);

                    VerifyNumber(etTenantPhone.getText().toString());

                    Toast.makeText(getBaseContext() ,  "We are verifying ", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", dataSnapshot.toString());

                }else{
                    Toast.makeText(getBaseContext() ,  "You are not registered ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private  void verifyPhoneWithCode(String verificationId, String code){
        PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(verificationId,code);
signInWithPhoneCredential(phoneAuthCredential);
    }
    private void  VerifyNumber(String number){

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + number, 60, TimeUnit.SECONDS, this, mCallBacks);
        mVerificationInProgress=true;
    }

    private void signInWithPhoneCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d("TenantAuth:","SignIn Success");
                            FirebaseUser user= task.getResult().getUser();
                            String mobile=user.getPhoneNumber().toString();
                        }else{
                            /*SignIn Failed*/
                            if (task.getException() instanceof  FirebaseAuthInvalidCredentialsException){
                                etTenantPhone.setError("Invalid Phone");
                            }

                        }
                    }
                });
    }
    private boolean validateNumber(){
        String phone=etTenantPhone.getText().toString();
        if (TextUtils.isEmpty(phone)){
            etTenantPhone.setError("Invalid Phone Number.");
            return false ;
        }
        return true;
    }
    private void findAllViews(){
        etTenantPhone=findViewById(R.id.etTenantPhone);
        btnSignUp=findViewById(R.id.btnSignUpTenant);
        btnSignIn=findViewById(R.id.btnSignInTenant);
        btnProceed=findViewById(R.id.btnTenProceed);
        etTenantCode=findViewById(R.id.etTenantCode);
    }
}
