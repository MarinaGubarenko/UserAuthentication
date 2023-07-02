package com.example.marina.userauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.marina.userauthentication.DataModel.EnterActivity;
import com.example.marina.userauthentication.SendToEmail.SendToEmailPresenterImpl;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        ButterKnife.bind(this);
        if (getIntent().getBooleanExtra("finish", false)) finish();

    }

    @OnClick(R.id.buttonStart)
    void StartActivity(){
        Intent intent = new Intent(StartActivity.this, EnterActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonTest)
    void StartActivityTest(){
        Intent intent = new Intent(StartActivity.this, AuthenticationActivity.class);
        startActivity(intent);
    }

}
