package com.example.saksham.circles;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.hardware.Camera;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Saksham on 6/11/2015.
 */
public class ServerSender extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ServerSender(String name) {
        super(name);
        new SocketServerThread();
    }
    protected SocketServerThread getThread(){
        return new SocketServerThread();
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        outbullet = intent.getByteArrayExtra("bullet");
    }
        AreaCircle bulletIn;
        AreaCircle bulletOut;
        byte[]inbullet;
        byte[] outbullet;
        EditText textTitle;

        TextView info, infoip, msg;
        String message = "";
        ServerSocket serverSocket;

        CheckBox optWelcome;



        public class SocketServerThread extends Thread {

            static final int SocketServerPORT = 8080;
            int count = 0;
            public SocketServerThread(){

            }
            @Override
            public void run() {
                Socket socket = null;
                DataInputStream dataInputStream = null;
                DataOutputStream dataOutputStream = null;

                try {
                    serverSocket = new ServerSocket(SocketServerPORT);
                    while (true) {
                        socket = serverSocket.accept();
                        dataInputStream = new DataInputStream(socket.getInputStream());
                        dataOutputStream = new DataOutputStream(socket.getOutputStream());


                        //Check available() before readUTF(),
                        //to prevent program blocked if dataInputStream is empty
                        if(dataInputStream.available()>0){
                           inbullet = new byte[dataInputStream.readInt()];
                            dataInputStream.readFully(inbullet, 0, inbullet.length);
                        }

                         ByteArrayInputStream in = new ByteArrayInputStream(inbullet);
                        ObjectInputStream is = new ObjectInputStream(in);
                        bulletIn= (AreaCircle)is.readObject();
                        Intent i = new Intent(ServerSender.this,MultiplayerGame.class);
                        i.putExtra("bullet", inbullet);
                        if(optWelcome.isChecked()){

                            String msgReply = count + ": " + textTitle.getText().toString();
                            dataOutputStream.writeUTF(msgReply);
                        }

                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    final String errMsg = e.toString();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    if (dataInputStream != null) {
                        try {
                            dataInputStream.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    if (dataOutputStream != null) {
                        try {
                            dataOutputStream.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

        public String getIpAddress() {
            String ip = "";
            try {
                Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                        .getNetworkInterfaces();
                while (enumNetworkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = enumNetworkInterfaces
                            .nextElement();
                    Enumeration<InetAddress> enumInetAddress = networkInterface
                            .getInetAddresses();
                    while (enumInetAddress.hasMoreElements()) {
                        InetAddress inetAddress = enumInetAddress.nextElement();

                        if (inetAddress.isSiteLocalAddress()) {
                            ip += "SiteLocalAddress: "
                                    + inetAddress.getHostAddress() + "\n";
                        }

                    }

                }

            } catch (SocketException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                ip += "Something Wrong! " + e.toString() + "\n";
            }

            return ip;
        }
    }

