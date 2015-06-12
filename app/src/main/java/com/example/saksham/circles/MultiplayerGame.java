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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Saksham on 6/8/2015.
 */
public class MultiplayerGame extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private long lastUpdate;
    private Thread socketServerThread;
    Button but;

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
        //setContentView(R.layout.multiplayer_game);
        MyColor.addColors();
        super.onCreate(savedInstanceState);

        init();



    }

    private void init() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        setContentView(new MyView());
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        socketServerThread = new Thread(new ServerSender("server").getThread());
        socketServerThread.start();
    }

    public void onRestart() {
        super.onRestart();
        init();
    }

    public void onStart() {
        super.onStart();
        init();
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
        private AreaCircle mc = new AreaCircle(getWidth() / 2, getHeight() / 2, 20, 0, 0);
        private int centerX;
        private int centerY;
        private int speedX = 5;
        private int speedY = 4;
        Circle cir;
        Circle cir2;
        ArrayList<Dot> listOfDots = new ArrayList<Dot>();
        ArrayList<AreaCircle> bullets = new ArrayList<AreaCircle>();
        private int count = 1;

        public MyView() {
            super(MultiplayerGame.this);
            p = new Paint();
            p.setColor(Color.GREEN);
            OnTouchListener OTL= new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    switch (MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_DOWN: {
                            bullets.add(new AreaCircle(mc.getX(), mc.getY(), mc.getRadius() / 3, x, 10 + y));
                            mc.setRadius(-(mc.getRadius() / 3));
                            Log.i("work", "I did it");
                        }

                        case MotionEvent.ACTION_MOVE: {
                            bullets.add(new AreaCircle(mc.getX(), mc.getY(), mc.getRadius() / 3, x, 10 + y));
                            mc.setRadius(-(mc.getRadius() / 3));
                            Log.i("work", "I did it");
                        }

                        case MotionEvent.ACTION_UP: {
                            bullets.add(new AreaCircle(mc.getX(), mc.getY(), mc.getRadius() / 3, x, 10 + y));
                            mc.setRadius(-(mc.getRadius() / 3));
                            Log.i("work", "I did it");
                        }

                        case MotionEvent.ACTION_CANCEL: {
                            bullets.add(new AreaCircle(mc.getX(), mc.getY(), mc.getRadius() / 3, x, 10 + y));
                            mc.setRadius(-(mc.getRadius() / 3));
                            Log.i("work", "I did it");
                        }

                        case MotionEvent.ACTION_POINTER_UP: {
                            bullets.add(new AreaCircle(mc.getX(), mc.getY(), mc.getRadius() / 3, x, 10 + y));
                            mc.setRadius(-(mc.getRadius() / 3));
                            Log.i("work", "I did it");
                        }
                    }

                    return true;
                }
            };
        }


        private Dot setCircles() {
            Dot d;
            Random r = new Random();
            return new Dot(r.nextInt(this.getWidth()), r.nextInt(this.getHeight()));
        }



        private void makeToast() {
            Toast.makeText(MultiplayerGame.this, "help me", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldW, int oldH) {
            centerX = w / 2;
            centerY = h / 2;
        }

        protected void onDraw(Canvas c) {
            //new ServerSender("").getThread().run();
            super.onDraw(c);
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            c.drawRect(0, 0, this.getWidth(), this.getHeight(), p);
            if (count == 1) {
//                but.setOnClickListener(new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//
//                });
                for (int i = 0; i < 30; i++) {
                    listOfDots.add(setCircles());

                }
                count++;
            }
            for (Dot d : listOfDots) {
                d.draw(c, this);
            }
            int ex;
            int yi;
            if (x < 0) {
                ex = 0;
            } else if (x * 3 > this.getWidth()) {
                ex = this.getWidth() - mc.getRadius() - 1;
            } else {
                ex = x * 3;
            }
            if (y < 0) {
                yi = 0;
            } else if (y * 3 > this.getHeight()) {
                yi = this.getHeight() - mc.getRadius() - 1;
            } else {
                yi = y * 3;
            }

            p.setColor(mc.code);
            c.drawCircle(ex, yi, mc.getRadius(), p);
            //mDrawable.setBounds(ex, yi, ex + mc.getRadius(), yi + mc.getRadius());
            mc.setPosX(ex);
            mc.setPosY(yi);
            mDrawable.draw(c);

            super.onDraw(c);
            setCircles();
            for (Circle cir : listOfDots) {
                cir.draw(c, this);
            }
            for(AreaCircle ac :bullets){
                ac.draw(c, this);
                if(ac.isDestroyed()){
                    Intent i = new Intent(MultiplayerGame.this,ServerSender.class);
                    try {
                        i.putExtra("bullet", ac.serialize());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    startActivity(i);
                }
            }
            for (int i = 0; i < listOfDots.size(); i++) {

                if (!listOfDots.get(i).isDestroyed() && listOfDots.get(i).hasCollision(mc)) {
                    mc.testIncreaseSize(listOfDots.get(i));
                    Log.i("", String.valueOf(mc.getRadius()));
                }
                if (mc.isDestroyed()) {
                    Intent intent = new Intent(MultiplayerGame.this, com.example.saksham.circles.Intermediate.class);
                    intent.putExtra("score", mc.getScore());
                    startActivity(intent);
                    return;
                }
            }

            ArrayList<Dot> addThese = new ArrayList<Dot>();
            for (Dot temp : listOfDots) {
                if (temp.isDestroyed()) {
                    temp = setCircles();
                    addThese.add(temp);
                }
            }
            for (int i = this.listOfDots.size() - 1; i >= 0; i--) {
                if (listOfDots.get(i).isDestroyed()) {
                    listOfDots.remove(i);
                }
            }
            listOfDots.addAll(addThese);
            postInvalidate();

        }


    }
}
