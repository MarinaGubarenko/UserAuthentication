package com.example.marina.userauthentication.SendToEmail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.marina.userauthentication.DataModel.DataModel;
import com.example.marina.userauthentication.R;
import com.example.marina.userauthentication.StartActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class SendToEmailActivity extends AppCompatActivity implements SendToEmailView {


    @BindView(R.id.name)
    EditText nameText;
    String contact, text, name;
    SendToEmailPresenter sendToEmailPresenter;
    @BindView(R.id.buttonExit)
    Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_data_to_email);
        sendToEmailPresenter = new SendToEmailPresenterImpl(this);
        ButterKnife.bind(this);
    }


    @Override
    public String getWayType() {
        return text;
    }

    @Override
    public String getName() {
        return name;
    }

    @OnCheckedChanged({R.id.radio1, R.id.radio2, R.id.radio3})
    void onCheck(CompoundButton button, boolean checked) {
        if (checked) {
            switch (button.getId()) {
                case R.id.radio1:
                    text = "Указательным или средним пальцем";
                    break;
                case R.id.radio2:
                    text = "Большим пальцем";
                    break;
                case R.id.radio3:
                    text = "Двумя пальцами";
                    break;

            }
        }

    }

    @OnClick(R.id.buttonSend)
    void sendToMail() {
        name = nameText.getText().toString();
        sendToEmail(text, name);
        exitButton.setEnabled(true);

    }

    @OnClick(R.id.buttonExit)
    void exit() {
        Intent intent = new Intent(this, StartActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra("finish", true);

        startActivity(intent);
    }

    public void sendToEmail(String types, String name) {
        String contact = "marinasolyonaya2809.com@gmail.com";
        String subject = name;
        String message = "Способ ввода: " + types + "\nPress \n";
        for (int i = 0; i < sendToEmailPresenter.getTimePress().size(); i++) {
            if (i%10 ==0){
                message += "\nPress \n";
            }
            message += sendToEmailPresenter.getTimePress().get(i).toString() + "\t";
        }
        message += "\ntimeTouch\n";
        for (int i = 0; i < sendToEmailPresenter.getTimeTouch().size(); i++) {
            if (i%10 ==0){
                message += "\ntimeTouch\n";
            }
            message += sendToEmailPresenter.getTimeTouch().get(i).toString() + "\t";
        }

        Intent emailIntent = new Intent(Intent.ACTION_SEND); // отправляем письмо
        emailIntent.setType("plain/text"); // устанавливаем тип содержимого
        // Кому
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{contact});
        // Зачем
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        // О чём
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            //startActivity(emailIntent);
        } else {
            Toast.makeText(this, "Error.", Toast.LENGTH_SHORT).show();
        }


    }
}
