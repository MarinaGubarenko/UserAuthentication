package com.example.marina.userauthentication.DataModel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.marina.userauthentication.R;
import com.example.marina.userauthentication.StartActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PassesActivity extends AppCompatActivity{

    private int numberOfPasses;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        numberOfPasses = intent.getIntExtra("passes",1);
        setContentView(R.layout.activity_passed);
        numberOfPasses++;
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonReplay)
    void StartActivity(){
        Intent intent = new Intent(PassesActivity.this, EnterActivity.class);
        intent.putExtra("passes", numberOfPasses);
        startActivity(intent);
    }
}
