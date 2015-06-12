package com.example.saksham.circles;

/**
 * Created by Saksham on 6/6/2015.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WirelessClient extends Activity{

    TextView textResponse;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;

    EditText welcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client);

        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        buttonConnect = (Button) findViewById(R.id.connect);
        buttonClear = (Button) findViewById(R.id.clear);
        textResponse = (TextView) findViewById(R.id.response);

        welcomeMsg = (EditText)findViewById(R.id.welcomemsg);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);

        buttonClear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                textResponse.setText("");
            }
        });
    }

    OnClickListener buttonConnectOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {

            String tMsg = welcomeMsg.getText().toString();
            if(tMsg.equals("")){
                tMsg = null;
                Toast.makeText(WirelessClient.this, "No Welcome Msg sent", Toast.LENGTH_SHORT).show();
            }
            String tAd = editTextAddress.getText().toString();
            if(tAd.equals("")){
                tAd = null;
                Toast.makeText(WirelessClient.this, "No Address Input found", Toast.LENGTH_SHORT).show();
                return;
            }
            String tPt = editTextAddress.getText().toString();
            if(tPt.equals("")){
                tPt = null;
                Toast.makeText(WirelessClient.this, "No Port Input found", Toast.LENGTH_SHORT).show();
                return;
            }

            MyClientTask myClientTask = new MyClientTask(editTextAddress
                    .getText().toString(), Integer.parseInt(editTextPort
                    .getText().toString()));
            myClientTask.execute();
        }
    };

    public class MyClientTask extends AsyncTask<Void, Void, Void> implements Serializable{

        String dstAddress;
        int dstPort;
        String response = "";

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



                response = dataInputStream.readUTF();

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
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
                Intent i = new Intent(WirelessClient.this,ClientSender.class);
                i.putExtra("dstPort", 8080);
                i.putExtra("dstAddress", dstAddress);
                startActivity(i);
                return null;
            }
            //return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textResponse.setText(response);
            super.onPostExecute(result);
        }

    }

}