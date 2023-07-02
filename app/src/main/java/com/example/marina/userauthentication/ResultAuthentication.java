package com.example.marina.userauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marina.userauthentication.DataModel.EnterActivity;

import butterknife.BindArray;
import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultAuthentication extends AppCompatActivity {

    String name = "";
    TextView text;
    @BindView(R.id.imageViewNo)
    ImageView no;
    @BindView(R.id.imageViewYes)
    ImageView yes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        text = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        if (name != ""){
            text.setText(name);
            yes.setVisibility(View.VISIBLE);
            no.setVisibility(View.INVISIBLE);
        }else{
            no.setVisibility(View.VISIBLE);
            yes.setVisibility(View.INVISIBLE);
        }

    }

    @OnClick(R.id.buttonBack)
    void StartActivity(){
        Intent intent = new Intent(ResultAuthentication.this, StartActivity.class);
        startActivity(intent);
    }

}
