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

/**
 * Created by Saksham on 6/2/2015.
 */
public class Intermediate extends Activity{
    public void onCreate(Bundle savedInstanceState) {
        MyColor.addColors();
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.intermediate_fullscreen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
                Intent intent = new Intent(Intermediate.this, Game.class);
                startActivity(intent);
            }

        });
    }
}
