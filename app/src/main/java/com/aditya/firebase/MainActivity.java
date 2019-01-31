package com.aditya.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView noticeCard,circularCard,galleryCard,aboutusCard,contactusCard,logoutCard;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        //defining cards
        noticeCard = (CardView) findViewById(R.id.noticecardId);
        circularCard = (CardView) findViewById(R.id.circularcardId);
        galleryCard = (CardView) findViewById(R.id.gallerycardId);
        aboutusCard = (CardView) findViewById(R.id.aboutuscardId);
        contactusCard = (CardView) findViewById(R.id.contactuscardId);
        logoutCard = (CardView) findViewById(R.id.logoutcardId);
        //add onclick listener to the cards
        noticeCard.setOnClickListener(this);
        circularCard.setOnClickListener(this);
        galleryCard.setOnClickListener(this);
        aboutusCard.setOnClickListener(this);
        contactusCard.setOnClickListener(this);
        logoutCard.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.noticecardId : i = new Intent(this,notice.class); startActivity(i); break;
            case R.id.circularcardId : i = new Intent(this,circular.class); startActivity(i); break;
            case R.id.gallerycardId : i =new Intent(this,gallery.class); startActivity(i); break;
            case R.id.aboutuscardId : i =new Intent(this,aboutus.class); startActivity(i); break;
            case R.id.contactuscardId : i =new Intent(this,contactus.class); startActivity(i);break;
            case R.id.logoutcardId :
                logout();
            default:break;
        }
    }

    private void logout() {

        mAuth.signOut();
        sendToLogin();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){

            sendToLogin();
        }
    }

    private void sendToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginPageActivity.class);
        startActivity(intent);
        finish();
    }
}
