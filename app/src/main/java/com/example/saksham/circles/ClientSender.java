package com.example.saksham.circles;

import android.content.Intent;
import android.os.AsyncTask;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Saksham on 6/12/2015.
 */
public class ClientSender {
    private byte[] inbullet;
    ServerSocket serverSocket;
    public class MyClientTask extends AsyncTask<Void, Void, Void> implements Serializable {

        String dstAddress;
        int dstPort;
        Byte[] response;
        private AreaCircle bulletIn;

        MyClientTask(String addr, int port) {
            dstAddress = addr;
            dstPort = port;

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Socket socket = null;
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;

            try {
                socket = new Socket(dstAddress, dstPort);
                dataOutputStream = new DataOutputStream(
                        socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());

                if(dataInputStream.available()>0){
                    inbullet = new byte[dataInputStream.readInt()];
                    dataInputStream.readFully(inbullet, 0, inbullet.length);
                }

                //response = dataInputStream.read();

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //response = "IOException: " + e.toString();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
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

                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
            return null;
        }
        //@Override
        public void run() {
            Socket socket = null;
            DataInputStream dataInputStream = null;
            DataOutputStream dataOutputStream = null;

            try {
                serverSocket = new ServerSocket(8080);
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
//                    Intent i = new Intent(ClientSender.this, MultiplayerGame.class);
//                    i.putExtra("bullet", inbullet);


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

}

