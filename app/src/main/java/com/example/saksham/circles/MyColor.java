package com.example.saksham.circles;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Saksham on 5/11/2015.
 */
public class MyColor {
    private static ArrayList hexCodes = new ArrayList<String>();

    public static void addColors() {
        hexCodes.add("#05D9E4");
        hexCodes.add("#ccff00");
        hexCodes.add("#512DA8");
        hexCodes.add("#009688");
        hexCodes.add("#FFC107");
        hexCodes.add("#FF5722");
        hexCodes.add("#D32F2F");
        hexCodes.add("#1A237E");
        hexCodes.add("#76FF03");
        hexCodes.add("#29B6F6");
        hexCodes.add("#5E35B1");
        hexCodes.add("#b71c1c");
        hexCodes.add("#4CAF50");
        hexCodes.add("#eeff41");
        hexCodes.add("#4a148c");
        hexCodes.add("#0d47a1");
        hexCodes.add("#1b5e20");
        hexCodes.add("#e65100");
        hexCodes.add("#d50000");
        hexCodes.add("#e91e63");
        hexCodes.add("#2196f3");
        hexCodes.add("#00bcd4");
        hexCodes.add("#004d40");
    }
    public static String getCode() {
        Random r = new Random();
        return (String) hexCodes.get((int) (Math.random()*hexCodes.size()));
//        return (String) hexCodes.get(r.nextInt(hexCodes.size()));
    }
}

