package com.example.saksham.circles;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Saksham on 5/13/2015.
 */
public class Circle {
    protected int radius;
    protected int code;
    protected int xpos;
    protected int ypos;
    protected int speedX = 5;
    protected int speedY = 4;
    protected boolean destroyed;
    public Circle(int posx, int posy, int r, int dx, int dy){
        xpos = posx;
        ypos = posy;
        radius= r;
        speedX= dx;
        speedY = dy;
        code = Color.parseColor(MyColor.getCode());
      }
    public int getX(){
        return xpos;
    }
    public int getY(){
        return ypos;
    }
    public void draw (Canvas c, View v){
        int w = v.getWidth();
        int h = v.getHeight();
        xpos += speedX;
        ypos+= speedY;
        int rightLimit = w - radius;
        int bottomLimit = h - radius;

        if (xpos >= rightLimit) {
            xpos = rightLimit;
            speedX *= -1;
        }
        if (xpos <= radius) {
            xpos = radius;
            speedX *= -1;
        }
        if (ypos >= bottomLimit) {
            ypos = bottomLimit;
            speedY *= -1;
        }
        if (ypos <= radius) {
            ypos = radius;
            speedY *= -1;
        }
        Paint p = new Paint();
        p.setColor(code);
        c.drawCircle(xpos, ypos, radius, p);
    }
    public boolean hasCollision(Circle circle){
        double xDiff = this.getX() - circle.getX();
        double yDiff = this.getY() - circle.getY();

        double distance = Math.sqrt((Math.pow(xDiff, 2) + Math.pow(yDiff, 2)));

        return distance < (radius + circle.getRadius());
    }
    public int getRadius(){
        return radius;
    }
    public void setColor(){
        code = Color.parseColor(MyColor.getCode());
    }
    public void incrementSize(int size){
        radius += size;
    }
    public void testIncreaseSize(Circle c){
        if(this.getRadius()>=c.getRadius()){
            this.incrementSize((int)(c.getRadius()/(4)));
            c.destroy();
        }else {
            c.incrementSize((int)(c.getRadius()/(4)));
            this.destroy();
        }
    }
    public void destroy(){
        if (destroyed==false)
            destroyed=true;
    }
    public boolean isDestroyed(){
        return destroyed;
    }
    @Override
    public String toString(){
    return "center" + xpos + "," + ypos + "radius " + radius;
    }

}


