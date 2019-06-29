package com.saumya.pgtask.Manager;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.saumya.pgtask.R;

public class SignUpActivity extends AppCompatActivity {

    /*This is for Manager*/
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private String UserId;
    private Button SignIn, SignUp , Proceed;
    private EditText Email, Password;
    private static final String TAG = "EmailPasswordSignUp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth=FirebaseAuth.getInstance();
        SignUp=findViewById(R.id.btnSignUp);
        SignIn=findViewById(R.id.btnSignIn);
        Proceed=findViewById(R.id.btnProceed);
        Email=findViewById(R.id.editmail);
        Password=findViewById(R.id.editpwd);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSignUpAccount(Email.getText().toString(), Password.getText().toString() );

            }
        });
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(Email.getText().toString(),Password.getText().toString());

            }
        });
        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext() , ManagerData.class));
            }
        });
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

    }

    private void signIn(String email, String password){
        Log.d(TAG , "signin"+ email);
        if (!validateForm()){
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {            if (task.isSuccessful()){
                        /*successfully signedIn*/
                        Log.d(TAG , "signIn :successfully");
                        FirebaseUser user=mAuth.getCurrentUser();
                        updateUI(user);
                    }else {
                        /*sign in fails*/
                        Log.d(TAG , "signIn: Failed");
                        Toast.makeText(getBaseContext(), "SignIn Failed", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }

                    }
                });
        /*SignIn method ends here*/
    }
    private void createSignUpAccount(String email, String password){
        Log.d(TAG, "createAccount:"+ email);
        if (!validateForm()){
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //SignIn Success
                    Log.d(TAG, "userCreated:successfully");
                    FirebaseUser user=mAuth.getCurrentUser();
                    updateUI(user);
                }else{
                    /*sign in fails*/
                    Log.d(TAG, "userCreated:failed"+ task.getException());
                   Toast.makeText(getBaseContext(), "Authentication Failed:"+task.getException(), Toast.LENGTH_SHORT).show();
                   updateUI(null);

                }
            }
        }) ;
        /*End CreateAccount method*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser=mAuth.getCurrentUser();
        updateUI(firebaseUser);
    }

    private boolean validateForm(){
        boolean valid=true;

        String email=Email.getText().toString();
        if (TextUtils.isEmpty(email)){
            Email.setError("Required Field:");
            valid=false;
        }else {
            Email.setError(null);
        }
        String password=Password.getText().toString();
        if (TextUtils.isEmpty(password)){
            Password.setError("Required Field:");
            valid=false;
        }else {
            Password.setError(null);
        }
        return valid;
    }

    private void updateUI(FirebaseUser firebaseUser) {
        if (firebaseUser !=null){
            /*when the user is logged in*/
            SignIn.setVisibility(View.VISIBLE);
            SignUp.setVisibility(View.GONE);
        }else{
            SignUp.setVisibility(View.VISIBLE);
            SignIn.setVisibility(View.GONE);
            Password.setVisibility(View.VISIBLE);
            Email.setVisibility(View.VISIBLE);
        }
    }
}
