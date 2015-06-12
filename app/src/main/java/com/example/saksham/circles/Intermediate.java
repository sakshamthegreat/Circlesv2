package com.example.saksham.circles;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Saksham on 6/2/2015.
 */
public class Intermediate extends Activity{
    TextView t;
    int game;
    public void onCreate(Bundle savedInstanceState) {
        MyColor.addColors();
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.intermediate_fullscreen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        t = (TextView) findViewById(R.id.textView2);
        Button orderButton = (Button)findViewById(R.id.button4);

        orderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intermediate.this, FullscreenActivity.class);
                startActivity(intent);
            }

        });
        Button orderButton2 = (Button)findViewById(R.id.button3);

        orderButton2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (game == 1) {
                    Intent intent = new Intent(Intermediate.this, EasyGame.class);
                    startActivity(intent);
                    return;
                } else if (game == 2){
                    Intent intent = new Intent(Intermediate.this, Game.class);
                    startActivity(intent);
                    return;
                }else if (game == 2) {
                    Intent intent = new Intent(Intermediate.this, HardGame.class);
                    startActivity(intent);
                    return;
                }
            }
        });
    }
    public void onStart(){
        super.onStart();
        Intent i = getIntent();
        int score = i.getIntExtra("score", 5);
        t.setText(String.valueOf(score));
        game = getIntent().getIntExtra("game", 0);
    }
}
