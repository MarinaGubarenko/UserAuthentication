package com.example.marina.userauthentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import com.example.marina.userauthentication.DataModel.DataModel;
import com.example.marina.userauthentication.DataModel.DataModelImpl;
import com.example.marina.userauthentication.DataModel.EnterActivity;
import com.example.marina.userauthentication.DataModel.PassesActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class AuthenticationActivity extends AppCompatActivity {

    String[] name = {"Марина Губаренко", "Данил Стульбо", "Губаренко Евгений"};
    int[][] etalonTouch = {
            {683, 454, 544, 428, 459, 523, 558, 544, 466, 406},
            {1102, 834, 683, 675, 703, 655, 546, 993, 630, 611},
            {640, 451, 437, 763, 427, 504, 491, 459, 434, 440}
    };
    int[][] etalonPress = {
            {69, 71, 93, 77, 67, 65, 69, 73, 71, 59},
            {128, 111, 121, 135, 101, 140, 112, 116, 157, 107},
            {73, 78, 94, 121, 114, 83, 90, 109, 93, 97}
    };
    private int[] timePress;
    private int[] timeTouch;


    float touchX, touchY, cx, cy;
    float radius = 60;
    Random rdm = new Random();
    Display display;
    Point size;
    int height, width;
    int start, stop, startPress, stopPress;
    Calendar c;
    int numberOfPasses = 1;
    int passes = 5;
    int n = 10;
    int offset = 60;
    float[][] coordinate = new float[2][11];
    int[] resultTestPress = new int[n], resultTestTouch = new int[n];
    int index = 0;

    public static final String APP_PREFERENCES = "mysettings";
    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES_PRESS = "data_press";
    public static final String APP_PREFERENCES_TOUCH = "data_touch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GraphicsView myView = new GraphicsView(this);
        setContentView(myView);
      //  mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y - 200;
        DataModel dataModel = DataModelImpl.getInstance();
        timePress = dataModel.getEtalonePress();
        timeTouch = dataModel.getEtaloneTouch();
      /*  if (mSettings.contains(APP_PREFERENCES_PRESS)) {
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
        }*/
    }

    private void initialCoordinate() {
        coordinate[0][0] = radius + offset;
        coordinate[1][0] = radius + offset;
        coordinate[0][1] = width / 2.0f;
        coordinate[1][1] = height / 2.0f;
        coordinate[0][2] = width - radius - offset;
        coordinate[1][2] = height - radius - offset;
        coordinate[0][3] = width - radius - offset;
        coordinate[1][3] = height / 2.0f;
        coordinate[0][4] = width - radius - offset;
        coordinate[1][4] = radius + offset;
        coordinate[0][5] = radius + offset;
        coordinate[1][5] = height - radius - offset;
        coordinate[0][6] = width / 2.0f;
        coordinate[1][6] = height - radius - offset;
        coordinate[0][7] = width / 2.0f;
        coordinate[1][7] = radius + offset;
        coordinate[0][8] = radius + offset;
        coordinate[1][8] = height / 2.0f;
        coordinate[0][9] = width / 2.0f;
        coordinate[1][9] = height / 2.0f;
    }

    public int getTimePress() {
        return stopPress - startPress;
    }

    public int getTimeTouch() {
        return stop - start;

    }

    public class GraphicsView extends View {
        public GraphicsView(Context context) {
            super(context);
        }

        int i = 0;

        @Override
        protected void onDraw(Canvas canvas) {
            initialCoordinate();
            Paint mPaint = new Paint();
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(Color.WHITE);
            canvas.drawPaint(mPaint);
            if (i < n) {
                cx = coordinate[0][i];
                cy = coordinate[1][i];
                mPaint.setColor(Color.rgb(0, 0, 255));
                canvas.drawCircle(cx, cy, radius, mPaint);
                c = Calendar.getInstance();
                start = c.get(Calendar.MILLISECOND) + c.get(Calendar.SECOND) * 1000 + c.get(Calendar.MINUTE) * 60000 + c.get(Calendar.HOUR) * 3600000;

            }
            if (i>=n){
                DTW dtw = new DTW();
                boolean flag = false;
                double tempResultDTWPress=0, tempResultDTWTouch=0;
                String resultName = "";
                double resultDTWPress = 0, resultDTWTouch = 0;
                if (timePress.length !=0){

                    resultDTWPress = dtw.calculateDTW(timePress, resultTestPress) / 100.0;
                    resultDTWTouch = dtw.calculateDTW(timeTouch, resultTestTouch) / 1000.0;
                    if (resultDTWPress <= 1 && resultDTWTouch <= 1.5) {
                        flag = true;
                        resultName="пользователь телефона";
                    }
                }
                else {
                    for (int j = 0; j < etalonPress.length; j++) {
                        try {
                            resultDTWPress = dtw.calculateDTW(etalonPress[j], resultTestPress) / 100.0;
                            resultDTWTouch = dtw.calculateDTW(etalonTouch[j], resultTestTouch) / 1000.0;
                        } catch (Exception ex) {

                        }
                        if (resultDTWPress <= 1 && resultDTWTouch <= 1.5) {
                            if (!flag) {
                                tempResultDTWPress = resultDTWPress;
                                tempResultDTWTouch = resultDTWTouch;
                                resultName = name[j];
                                flag = true;
                            } else {
                                if (tempResultDTWPress > resultDTWPress && tempResultDTWTouch > resultDTWTouch) {
                                    resultName = name[j];
                                    tempResultDTWPress = resultDTWPress;
                                    tempResultDTWTouch = resultDTWTouch;
                                }
                            }
                        }

                    }
                }

               if (flag) {
                   Intent intent = new Intent(AuthenticationActivity.this, ResultAuthentication.class);
                   intent.putExtra("Name", resultName);
                   startActivity(intent);
                   /* mPaint.setColor(Color.BLACK);
                    canvas.drawText(resultName,100,100,mPaint);
                } else {
                   canvas.drawText("Ты кто такой? Давай досвиданья",50,100,mPaint);
                }*/
               }
               else {
                   Intent intent = new Intent(AuthenticationActivity.this, ResultAuthentication.class);
                   intent.putExtra("Name", "");
                   startActivity(intent);
               }

            }
        }


        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN ) {
                touchX = event.getX();
                touchY = event.getY();
                if (Math.sqrt(Math.pow(touchX - cx, 2.0) + Math.pow(touchY - cy, 2.0)) <= radius) {
                    c = Calendar.getInstance();
                    stop = c.get(Calendar.MILLISECOND) + c.get(Calendar.SECOND) * 1000 + c.get(Calendar.MINUTE) * 60000 + c.get(Calendar.HOUR) * 3600000;
                    startPress = stop;
                    resultTestTouch[index] = stop - start;

                }
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (Math.sqrt(Math.pow(touchX - cx, 2.0) + Math.pow(touchY - cy, 2.0)) <= radius ) {
                    c = Calendar.getInstance();
                    stopPress = c.get(Calendar.MILLISECOND) + c.get(Calendar.SECOND) * 1000 + c.get(Calendar.MINUTE) * 60000 + c.get(Calendar.HOUR) * 3600000;
                    i++;
                    resultTestPress[index] = stopPress - startPress;
                    index++;
                    invalidate();
                }
            }
            return true;
        }

    }
}
