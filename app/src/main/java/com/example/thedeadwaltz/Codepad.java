/*
* This part can make you like a pro, yet stupid... delete it if you want ha ha
*
* */

package com.example.thedeadwaltz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class Codepad extends AppCompatActivity {
    //MAC Address of Bluetooth Module
    private final String DEVICE_ADDRESS = "8C:AA:B5:93:2F:CA";
    //serial special UUID between the phone and bluetooth, no need to change
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    private BluetoothDevice device;
    private BluetoothSocket socket;

    private OrientationEventListener mLandOrientationListener;
    private OutputStream outputStream;

    private String code = "5010";
    private String myCode = "";

    Button n0, n1, n2, n3, n4, n5, n6, n7, n8, n9, back_btn, commitLaunch_btn;
    TextView showText;

    String command; //string variable that will store value to be transmitted to the bluetooth module

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codepad);

        device = MainActivity.device;
        socket = MainActivity.socket;
        outputStream = MainActivity.outputStream;

        //set to full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mLandOrientationListener = new OrientationEventListener(this) {

            @Override
            public void onOrientationChanged(int orientation) {
                if (orientation > 230 && orientation <= 310) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }else if(orientation > 50 && orientation <= 130){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                }
                //System.out.println("orientation = " + orientation);
            }
        };

        mLandOrientationListener.enable();
        //declaration of button variables
        n0 = (Button) findViewById(R.id.n0);
        n1 = (Button) findViewById(R.id.n1);
        n2 = (Button) findViewById(R.id.n2);
        n3 = (Button) findViewById(R.id.n3);
        n4 = (Button) findViewById(R.id.n4);
        n5 = (Button) findViewById(R.id.n5);
        n6 = (Button) findViewById(R.id.n6);
        n7 = (Button) findViewById(R.id.n7);
        n8 = (Button) findViewById(R.id.n8);
        n9 = (Button) findViewById(R.id.n9);
        back_btn = (Button) findViewById(R.id.back_btn);
        commitLaunch_btn = (Button) findViewById(R.id.commitLaunch_btn);
        showText = (TextView) findViewById(R.id.showText);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("your code has been deleted");
                Intent intent = new Intent();
                intent.setClass(Codepad.this,Control.class);
                startActivity(intent);
            }
        });

        n0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCode += "0";
                showText.setText(myCode);
            }
        });

        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCode += "1";
                showText.setText(myCode);

            }
        });

        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCode += "2";
                showText.setText(myCode);
            }
        });

        n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCode += "3";
                showText.setText(myCode);
            }
        });

        n4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCode += "4";
                showText.setText(myCode);
            }
        });

        n5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCode += "5";
                showText.setText(myCode);
            }
        });

        n6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCode += "6";
                showText.setText(myCode);
            }
        });

        n7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCode += "7";
                showText.setText(myCode);
            }
        });

        n8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCode += "8";
                showText.setText(myCode);

            }
        });

        n9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCode += "9";
                showText.setText(myCode);
            }
        });

        commitLaunch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                command = "LCHz";

                try
                {
                    outputStream.write(command.getBytes());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                if(myCode.equals(code)){
                    showToast("qualified");
                    Intent intent = new Intent();
                    intent.setClass(Codepad.this,Adjust.class);
                    startActivity(intent);
                }else{
                    showToast("check your code!");
                    Intent intent = new Intent();
                    intent.setClass(Codepad.this,Control.class);
                    startActivity(intent);
                }



            }
        });
    }



    @Override
    protected void onStart()
    {
        super.onStart();
    }

    public void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

}
