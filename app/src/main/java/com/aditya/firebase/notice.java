package com.aditya.firebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class notice extends AppCompatActivity {

    private EditText notice_subject;
    private EditText notice_Description;
    private Button send_notice_btn;
    private ProgressBar progressbar_Notice;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private String current_user_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        current_user_Id = firebaseAuth.getCurrentUser().getUid();

        notice_subject = findViewById(R.id.subject_editText);
        notice_Description = findViewById(R.id.notice_content_editText);
        send_notice_btn = findViewById(R.id.notice_submit_btn);
        progressbar_Notice = findViewById(R.id.progressbar_Notice);


        send_notice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = notice_subject.getText().toString();
                String description = notice_Description.getText().toString();

                if(TextUtils.isEmpty(subject) || TextUtils.isEmpty(description)) {
                    progressbar_Notice.setVisibility(View.VISIBLE);

                    Toast.makeText(notice.this, "Fields Are Empty.", Toast.LENGTH_LONG).show();
                }else {

                    final Map<String, Object> noticeMap =new HashMap<>();
                    noticeMap.put("Notice Subject", subject);
                    noticeMap.put("Notice Description", description);
                    noticeMap.put("User_id", current_user_Id);


                    firebaseFirestore.collection("Quick Notice").add(noticeMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(notice.this,"Notice added successfully", Toast.LENGTH_LONG).show();

                            }else {

                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(notice.this, "Error : "+errorMessage,Toast.LENGTH_LONG).show();
                            }
                            progressbar_Notice.setVisibility(View.INVISIBLE);
                        }
                    });
                }
                progressbar_Notice.setVisibility(View.INVISIBLE);


            }
        });
    }
}
