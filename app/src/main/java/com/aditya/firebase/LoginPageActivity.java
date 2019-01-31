package com.aditya.firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPageActivity extends AppCompatActivity {

    private EditText mEmailFeild;
    private EditText mPasswordFeild;

    private Button mLoginBtn;
    private Button mloginRegBtn;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mEmailFeild = (EditText) findViewById(R.id.regemailFeild);
        mPasswordFeild = (EditText) findViewById(R.id.regPasswordFeild);

        mLoginBtn = (Button) findViewById(R.id.btn_Login);
        mloginRegBtn = (Button) findViewById(R.id.btn_Register);

        mloginRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntend = new Intent(LoginPageActivity.this,RegisterActivity.class);
                startActivity(regIntend);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(LoginPageActivity.this, MainActivity.class));
                }
            }
        };

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startSignIn();

            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn(){
        String email = mEmailFeild.getText().toString();
        String password = mPasswordFeild.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            Toast.makeText(LoginPageActivity.this, "Fields Are Empty.", Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {

                        Toast.makeText(LoginPageActivity.this, "Sign In Problem.", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

}
