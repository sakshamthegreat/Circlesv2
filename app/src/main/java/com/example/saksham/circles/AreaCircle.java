package com.example.saksham.circles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Saksham on 5/31/2015.
 */
public class AreaCircle extends Circle implements Serializable{
    int score;
    int area = radius*radius;
    public AreaCircle(int posx, int posy, int r, int dx, int dy) {
        super(posx, posy, r, dx, dy);
        setColor();
        score =0;
    }

    @Override
    public void draw (Canvas c, View v){
        int w = v.getWidth();
        int h = v.getHeight();
        xpos += speedX;
        ypos+= speedY;
        int rightLimit = w - radius;
        int bottomLimit = h - radius;

        if (xpos >= rightLimit) {
            xpos = rightLimit;
            //speedX *= -1;
            this.destroy();
        }
        if (xpos <= radius) {
            xpos = radius;
            //speedX *= -1;
            this.destroy();
        }
        if (ypos >= bottomLimit) {
            ypos = bottomLimit;
            speedY *= -1;
        }
        if (ypos <= radius) {
            ypos = radius;
            speedY *= -1;
            this.destroy();
        }
        Paint p = new Paint();
        p.setColor(code);
        c.drawCircle(xpos, ypos, radius, p);
    }
    public void setPosX(int i){
        xpos=i;
    }
    public void setPosY(int i){ypos=i;}
    public void setColor(){
        code = Color.WHITE;
    }
    @Override
    public void testIncreaseSize(Circle c){
        if(this.getRadius()>=c.getRadius()){
            this.incrementSize(c.getRadius()*c.getRadius());
            c.destroy();
            this.incrementScore();
        }else {
            c.incrementSize(c.getRadius() * c.getRadius());
            this.destroy();
        }
    }

    private void incrementScore() {
        score++;
    }

    @Override
    public void incrementSize(int a){

        area += a;
        radius= (int) Math.sqrt(area);
    }
    public byte[] serialize() throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(this);
        Log.d("TAG", "Ball gets serialized()");
        return b.toByteArray();
    }

    public int getScore() {
        return score;
    }
    public void setRadius(int i){
        radius+=i;
    }
}