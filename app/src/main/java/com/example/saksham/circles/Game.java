package com.example.saksham.circles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Saksham on 5/10/2015.
 */
public class Game extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private long lastUpdate;

    ShapeDrawable mDrawable = new ShapeDrawable(new OvalShape());
    public static int x;
    public static int y;

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onCreate(Bundle savedInstanceState) {
        MyColor.addColors();
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        setContentView(new MyView(this));
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void onRestart() {
        super.onRestart();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        setContentView(new MyView(this));
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void onStart() {
        super.onStart();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        setContentView(new MyView(this));
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            x -= (int) event.values[0];
            y += (int) event.values[1];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor x, int accuracy) {
    }


    class MyView extends View {
        //public    Paint c;
        public Paint p;

        private int RADIUS = 46;
        private MyCircle mc = new MyCircle(getWidth() / 2, getHeight() / 2, 20, 0, 0);
        private int centerX;
        private int centerY;
        private int speedX = 5;
        private int speedY = 4;
        Circle cir;
        Circle cir2;
        ArrayList<Circle> listOfCircles = new ArrayList<Circle>();
        private int count = 1;

        public MyView(Context context) {
            super(context);
            p = new Paint();
            p.setColor(Color.GREEN);

        }


        private Circle setCircles() {
            Circle c;
            Random r = new Random();
            int x = r.nextInt(4) + 1;
            if (x == 1) {
                c = new Circle(10, new Random().nextInt(getHeight()),
                        mc.getRadius() - 10 + new Random().nextInt(20), new Random().nextInt(5) + 1, new Random().nextInt(5) + 1);
            } else if (x == 2) {
                c = new Circle(this.getHeight() - 10, new Random().nextInt(getHeight()),
                        mc.getRadius() - 10 + new Random().nextInt(20), new Random().nextInt(5) + 1, new Random().nextInt(5) + 1);

            } else if (x == 3) {
                c = new Circle(new Random().nextInt(getWidth()), 10,
                        mc.getRadius() - 10 + new Random().nextInt(20), new Random().nextInt(5) + 1, new Random().nextInt(5) + 1);

            } else {
                c = new Circle(new Random().nextInt(getWidth()), this.getHeight() - 10,
                        mc.getRadius() - 10 + new Random().nextInt(20), new Random().nextInt(5) + 1, new Random().nextInt(5) + 1);
            }
            return c;
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldW, int oldH) {
            centerX = w / 2;
            centerY = h / 2;
        }

        protected void onDraw(Canvas c) {
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            c.drawRect(0, 0, this.getWidth(), this.getHeight(), p);
            super.onDraw(c);
            if (count == 1) {
                for (int i = 0; i < 15; i++) {
                    listOfCircles.add(setCircles());
                }
                count++;
            }
            for (Circle cir : listOfCircles) {
                cir.draw(c, this);
            }
            int ex;
            int yi;
            if (x < 0) {
                ex = 0;
            } else if (x * 2 > this.getWidth()) {
                ex = this.getWidth() - mc.getRadius() -1;
            } else {
                ex = x * 2;
            }
            if (y < 0) {
                yi = 0;
            } else if (y * 2 > this.getHeight()) {
                yi = this.getHeight() - mc.getRadius() -1;
            } else {
                yi = y * 2;
            }

            p.setColor(mc.code);
            c.drawCircle(ex, yi, mc.getRadius(), p);
            //mDrawable.setBounds(ex, yi, ex + mc.getRadius(), yi + mc.getRadius());
            mc.setPosX(ex);
            mc.setPosY(yi);
            mDrawable.draw(c);

            super.onDraw(c);
            setCircles();
            for (Circle cir : listOfCircles) {
                cir.draw(c, this);
            }

            for (int i = 0; i < listOfCircles.size(); i++) {

                if (!listOfCircles.get(i).isDestroyed() && listOfCircles.get(i).hasCollision(mc)) {
                    mc.testIncreaseSize(listOfCircles.get(i));
                    Log.i("", String.valueOf(mc.getRadius()));
                }
                if (mc.isDestroyed()) {
                    Intent intent = new Intent(Game.this, com.example.saksham.circles.Intermediate.class);
                    intent.putExtra("score", mc.getScore());
                    startActivity(intent);
                    return;
                }
            }

            ArrayList<Circle> addThese = new ArrayList<Circle>();
            for (Circle temp : listOfCircles) {
                if (temp.isDestroyed()) {
                    temp = setCircles();
                    addThese.add(temp);
                }
                if (temp.getRadius() > 150 && !temp.isDestroyed()) {
//                    zoomOut();
                    addThese.add(temp);
                }
            }
            for (int i = this.listOfCircles.size() - 1; i >= 0; i--) {
                if (listOfCircles.get(i).isDestroyed()) {
                    listOfCircles.remove(i);
                }
            }
            listOfCircles.addAll(addThese);
                        if(mc.isDestroyed()){
//                Intent intent = new Intent(Game.this, FullscreenActivity.class);
//                startActivity(intent);
//
            }
            this.postInvalidateDelayed(10);
        }

        private void zoomOut() {
            for (Circle c : listOfCircles) {
                c.incrementSize(-3);
            }
            this.postInvalidateDelayed(1);
        }

    }
}



