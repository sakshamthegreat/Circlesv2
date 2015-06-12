package com.example.saksham.circles;

/**
 * Created by Saksham on 6/6/2015.
 */


        import java.io.DataInputStream;
        import java.io.DataOutputStream;
        import java.io.IOException;
        import java.net.InetAddress;
        import java.net.NetworkInterface;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.net.SocketException;
        import java.util.Enumeration;

        import android.content.Intent;
        import android.content.pm.ActivityInfo;
        import android.hardware.Sensor;
        import android.hardware.SensorManager;
        import android.os.Bundle;
        import android.app.Activity;
        import android.view.WindowManager;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.TextView;

public class WirelessServer extends Activity {

    EditText textTitle;

    TextView info, infoip, msg;
    String message = "";
    ServerSocket serverSocket;
    Boolean connected = false;
    CheckBox optWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        info = (TextView) findViewById(R.id.info);
        infoip = (TextView) findViewById(R.id.infoip);
        msg = (TextView) findViewById(R.id.msg);

        optWelcome = (CheckBox)findViewById(R.id.optwelcome);

        infoip.setText(getIpAddress());

        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();

        textTitle = (EditText)findViewById(R.id.title);
    }

    public class SocketServerThread extends Thread {

        static final int SocketServerPORT = 8080;
        int count = 0;
        public SocketServerThread(){
            
        }
        @Override
        public void run() {
            if(connected==true){

            }
            Socket socket = null;
            DataInputStream dataInputStream = null;
            DataOutputStream dataOutputStream = null;

            try {
                serverSocket = new ServerSocket(SocketServerPORT);
                WirelessServer.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        info.setText("I'm waiting here: "
                                + serverSocket.getLocalPort());
                    }
                });

                while (true) {
                    socket = serverSocket.accept();
                    dataInputStream = new DataInputStream(
                            socket.getInputStream());
                    dataOutputStream = new DataOutputStream(
                            socket.getOutputStream());

                    String messageFromClient = "";

                    //Check available() before readUTF(),
                    //to prevent program blocked if dataInputStream is empty
                    if(dataInputStream.available()>0){
                        messageFromClient = dataInputStream.readUTF();
                    }

                    count++;
                    message += "#" + count + " from " + socket.getInetAddress()
                            + ":" + socket.getPort() + "\n"
                            + "Msg from client: " + messageFromClient + "\n";

                    WirelessServer.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            msg.setText(message);
                        }
                    });

                    if(optWelcome.isChecked()){

                        String msgReply = count + ": " + textTitle.getText().toString();
                        dataOutputStream.writeUTF(msgReply);

                    }
                    Intent i = new Intent(WirelessServer.this,MultiplayerGame.class);
                    startActivity(i);
                    return;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                final String errMsg = e.toString();
                WirelessServer.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        msg.setText(errMsg);
                    }
                });

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

    public static String getIpAddress() {
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