package com.example.marina.userauthentication.DataModel;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class DataModelImpl extends AppCompatActivity implements DataModel {
    final String LOG_TAG = "myLogs";
    final String DIR_SD = "MyData";
    final String FILENAME_SD = "DataTouch.txt";
    private static volatile DataModelImpl instance;

    private ArrayList<Integer> timePress = new ArrayList<>();
    private ArrayList<Integer> timeTouch = new ArrayList<>();



    public void writeToFile(ArrayList timePress, ArrayList timeTouch) {


        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        try {
            // получаем путь к SD
            File sdPath = Environment.getExternalStorageDirectory();

            // формируем объект File, который содержит путь к файлу
            File sdFile = new File(sdPath, FILENAME_SD);

            // открываем поток для записи
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));


            bw.write("\ntimePress\n");

            for (int i = 0; i < timePress.size(); i++) {
                bw.write(timePress.get(i).toString() + " ");
            }
            bw.write("\ntimeTouch\n");
            for (int i = 0; i < timeTouch.size(); i++) {
                bw.write(timeTouch.get(i).toString() + " ");
            }
            bw.write("\n");

            bw.close();
            Log.d(LOG_TAG, "Записано");
        } catch (IOException e) {
            Log.i("TAG", e.getMessage());
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public void sendToData(ArrayList timePress, ArrayList timeTouch) {
        timePress = new ArrayList<Integer>();
        timeTouch = new ArrayList<Integer>();
        this.timePress = timePress;
        this.timeTouch = timeTouch;
    }

    private DataModelImpl() {

    }

    public static DataModelImpl getInstance() {
        if (instance == null) {
            synchronized (DataModelImpl.class) {
                if (instance == null) {
                    instance = new DataModelImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void saveData() {
        //super.onPause();

    }

   /* @Override
    protected void onResume() {
        super.onResume();


        if (mSettings.contains(APP_PREFERENCES_PRESS)) {
            timePress = new ArrayList<Integer>();
            timeTouch = new ArrayList<Integer>();
            int i = 0;
            String press = "", touch = "";
            press = mSettings.getString(APP_PREFERENCES_PRESS, "");
            touch = mSettings.getString(APP_PREFERENCES_TOUCH, "");
            if (press != "" && touch != "") {
                while (i != press.length()) {
                    if (press.charAt(i) != ' ') {
                        timeTouch.add((int) touch.charAt(i));
                        timePress.add((int) press.charAt(i));
                    }
                }
            }
        }
    }*/

    @Override
    public ArrayList<Integer> getTimePress() {
        return timePress;
    }

    @Override
    public ArrayList<Integer> getTimeTouch() {
        return timeTouch;
    }

    @Override
    public int[] getEtalonePress(){
        int[] result = new int[10];
        if (timeTouch.size()!=0) {
            for (int i = 0; i < 10; i++) {
                result[i] = timePress.get(i) + timePress.get(i + 10) + timePress.get(i + 20) +
                        timePress.get(i + 30) + timePress.get(i + 40);
                result[i]/=5.0;

            }
        }
        return  result;
    }
    @Override
    public int[] getEtaloneTouch(){
        int[] result = new int[10];
        if (timeTouch.size()!=0) {
            for (int i = 0; i < 10; i++) {
                result[i] = timeTouch.get(i) + timeTouch.get(i + 10) + timeTouch.get(i + 20) +
                        timeTouch.get(i + 30) + timeTouch.get(i + 40);
                result[i]/=5.0;
            }
        }
        return  result;
    }


    /*public void sendToEmail(String types, String name) {
        String contact = "marina.solyonaya@yandex.ru";
        String subject = name;
        String message = types + "\nPress \t";
        for (int i = 0; i < timePress.size(); i++) {
            message += timePress.get(i).toString() + " ";
        }
        message += "\ntimeTouch\n";
        for (int i = 0; i < timeTouch.size(); i++) {
            message += timeTouch.get(i).toString() + " ";
        }

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO); // отправляем письмо
        emailIntent.setType("plain/text"); // устанавливаем тип содержимого
        // Кому
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{contact});
        // Зачем
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        // О чём
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            DataModelImpl.this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            //startActivity(emailIntent);
        } else {
            Toast.makeText(this, "Error.", Toast.LENGTH_SHORT).show();
        }
    }
    */


}
