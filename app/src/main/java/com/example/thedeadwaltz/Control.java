/*
* The move control system is special. User can control left and right side movement independently.
* That's comfortable and satisfying ... at least for controlling tanks. They are slow and they just
* have two motors, each on each side.
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class Control extends AppCompatActivity {
    //MAC Address of Bluetooth Module
    private final String DEVICE_ADDRESS = "8C:AA:B5:93:2F:CA";
    //serial special UUID between the phone and bluetooth, no need to change
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;

    private OrientationEventListener mLandOrientationListener;

    Button mLeftForward_btn, mLeftBack_btn, mRightForward_btn, mRightBack_btn, back_btn, launch_btn, settings_btn;

    String command; //string variable that will store value to be transmitted to the bluetooth module

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        //In this activity, I just take the pairing data out from MainActivity, no need to connect again,
        //in other words, if you lose connection, you have to go back to connect
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
        mLeftForward_btn = (Button) findViewById(R.id.leftForward_btn);
        mLeftBack_btn = (Button) findViewById(R.id.leftBack_btn);
        mRightForward_btn = (Button) findViewById(R.id.rightForward_btn);
        mRightBack_btn = (Button) findViewById(R.id.rightBack_btn);
        back_btn = (Button) findViewById(R.id.back_btn);
        launch_btn = (Button) findViewById(R.id.launch_btn);
        settings_btn = (Button) findViewById(R.id.settings_btn);



        //OnTouchListener code for the forward button (button long press)
        mLeftForward_btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    //z here is for the arduino program identify and cut the word, see also in my
                    //arduino project
                    command = "LFz";

                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    command = "LFSz";

                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                }

                return false;
            }

        });


        mLeftBack_btn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    command = "LBz";

                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    command = "LBSz";

                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {

                    }

                }
                return false;
            }
        });


        mRightForward_btn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    command = "RFz";

                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    command = "RFSz";

                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {

                    }

                }
                return false;
            }
        });


        mRightBack_btn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    command = "RBz";

                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    command = "RBSz";

                    try
                    {
                        outputStream.write(command.getBytes());
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }

                }
                return false;
            }
        });




        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(Control.this, MainActivity.class);
                startActivity(intent);

            }
        });



        launch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Control.this, Codepad.class);
                startActivity(intent);
            }
        });

        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                command = "STSz";

                try
                {
                    outputStream.write(command.getBytes());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                intent.setClass(Control.this, Settings.class);
                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        command = "CTLz";

        try {
            outputStream.write(command.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    public void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

}
