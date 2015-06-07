package com.example.saksham.circles;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Saksham on 5/21/2015.
 */
public class MyCircle extends Circle{
    int score;

    public MyCircle(int posx, int posy, int r, int dx, int dy) {
        super(posx, posy, r, dx, dy);
        setColor();
        score = 1;
    }
    @Override
    public void draw (Canvas c, View v){
        int w = v.getWidth();
        int h = v.getHeight();
        xpos += speedX;
        ypos+= speedY;
        int rightLimit = w - 2*radius;
        int bottomLimit = h - 2*radius;

        if (xpos >= rightLimit) {
            xpos = rightLimit;
            speedX *= -1;
        }
        if (xpos <= 0) {
            xpos = 0;
            speedX *= -1;
        }
        if (ypos >= bottomLimit) {
            ypos = bottomLimit;
            speedY *= -1;
        }
        if (ypos <= 0) {
            ypos = 0;
            speedY *= -1;
        }
        Paint p = new Paint();
        p.setColor(code);
        c.drawCircle(xpos, ypos, radius, p);
    }
    @Override
    public void testIncreaseSize(Circle c){
        if(this.getRadius()>=c.getRadius()){
            this.incrementSize((c.getRadius()/(4 +score)));
            score ++;
            c.destroy();
        }else {
            c.incrementSize((this.getRadius()/(4+score)));
            c.score ++;
            this.destroy();
        }
    }
    public void setPosX(int i){
        xpos=i;
    }
    public void setPosY(int i){ypos=i;}
    public void setColor(){
        code = Color.WHITE;
    }
    public int getScore(){
        return score;
    }
    public void incrementScore(){
        score++;
    }
}
