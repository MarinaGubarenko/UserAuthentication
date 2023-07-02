package com.example.marina.userauthentication.DataModel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import com.example.marina.userauthentication.R;
import com.example.marina.userauthentication.SendToEmail.SendToEmailActivity;
import com.example.marina.userauthentication.StartActivity;

import java.util.Calendar;
import java.util.Random;

import butterknife.ButterKnife;

public class EnterActivity extends AppCompatActivity implements DataView {
    private DataPresenter dataPresenter;
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

    public static final String APP_PREFERENCES = "mysettings";
    private SharedPreferences mSettings;
    public static final String APP_PREFERENCES_PRESS = "data_press";
    public static final String APP_PREFERENCES_TOUCH = "data_touch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        numberOfPasses = intent.getIntExtra("passes", 1);
        GraphicsView myView = new GraphicsView(this);
        setContentView(myView);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        dataPresenter = new DataPresenterImpl(this);
        width = size.x;
        height = size.y - 200;

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

    @Override
    public int getTimePress() {
        return stopPress - startPress;
    }

    @Override
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
            // стиль Заливка
            mPaint.setStyle(Paint.Style.FILL);
            // закрашиваем холст белым цветом
            mPaint.setColor(Color.WHITE);
            canvas.drawPaint(mPaint);
            mPaint.setAntiAlias(true);
            if (numberOfPasses <= passes) {
                if (i < n) {
                    cx = coordinate[0][i];
                    cy = coordinate[1][i];
                    mPaint.setColor(Color.GREEN);
                    canvas.drawCircle(cx, cy, radius, mPaint);
                    c = Calendar.getInstance();
                    start = c.get(Calendar.MILLISECOND) + c.get(Calendar.SECOND) * 1000 + c.get(Calendar.MINUTE) * 60000 + c.get(Calendar.HOUR) * 3600000;

                } else {
                    Intent intent = new Intent(EnterActivity.this, PassesActivity.class);
                    intent.putExtra("passes", numberOfPasses);
                    startActivity(intent);
                }
            } else {

             /*   String press = "", touch = "";
                for (int i = 0; i < tempPress.length; i++) {
                    press += tempPress[i] + " ";
                    touch += tempTouch[i] + " ";
                }
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_PRESS, press);
                editor.putString(APP_PREFERENCES_TOUCH, touch);
                editor.apply();*/

                Intent intent = new Intent(EnterActivity.this, StartActivity.class);
                startActivity(intent);
            }

        }

        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN && i <= n) {
                touchX = event.getX();
                touchY = event.getY();
                if (Math.sqrt(Math.pow(touchX - cx, 2.0) + Math.pow(touchY - cy, 2.0)) <= radius) {
                    c = Calendar.getInstance();
                    stop = c.get(Calendar.MILLISECOND) + c.get(Calendar.SECOND) * 1000 + c.get(Calendar.MINUTE) * 60000 + c.get(Calendar.HOUR) * 3600000;
                    startPress = stop;
                    dataPresenter.addTimeTouch();

                }
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (Math.sqrt(Math.pow(touchX - cx, 2.0) + Math.pow(touchY - cy, 2.0)) <= radius && i <= n) {
                    c = Calendar.getInstance();
                    stopPress = c.get(Calendar.MILLISECOND) + c.get(Calendar.SECOND) * 1000 + c.get(Calendar.MINUTE) * 60000 + c.get(Calendar.HOUR) * 3600000;
                    dataPresenter.addTimePress();
                    i++;
                    invalidate();
                }
            }
            return true;
        }

    }
}
