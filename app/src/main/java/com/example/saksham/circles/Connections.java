package com.example.saksham.circles;

/**
 * Created by Saksham on 6/5/2015.
 */



        import android.bluetooth.BluetoothAdapter;

public class Connections {

    private static boolean state = false;

    public static boolean blueTooth() {

        BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
        if (!bluetooth.isEnabled()) {
            System.out.println("Bluetooth is Disable...");
            state = true;
        } else if (bluetooth.isEnabled()) {
            String address = bluetooth.getAddress();
            String name = bluetooth.getName();
            System.out.println(name + " : " + address);
            state = false;
        }
        return state;
    }
}
