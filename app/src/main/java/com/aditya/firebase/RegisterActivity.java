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
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText reg_email_feild;
    private EditText reg_password_feild;
    private EditText reg_confirm_password_feild;

    private Button reg_btn;
    private Button reg_login_btn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        reg_email_feild = (EditText) findViewById(R.id.regemailFeild);
        reg_password_feild = (EditText) findViewById(R.id.regPasswordFeild);
        reg_confirm_password_feild = (EditText) findViewById(R.id.regPasswordconfirmFeild);
        reg_btn = (Button) findViewById(R.id.btn_Reg);
        reg_login_btn = (Button) findViewById(R.id.reg_Login_btn);

        reg_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = reg_email_feild.getText().toString();
                String pass = reg_password_feild.getText().toString();
                String Confirm_pass = reg_confirm_password_feild.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(Confirm_pass)){

                    if(pass.equals(Confirm_pass)){
                        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent setupIntend = new Intent(RegisterActivity.this,setupActivity.class);
                                    startActivity(setupIntend);
                                    finish();
                                } else{
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this, "Error : "+errorMessage,Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Password dosent't match !!Please check again.",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent mainIntend = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(mainIntend);
        finish();
    }
}
