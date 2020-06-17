package com.eldisprojectexpert.customlooperandhandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    boolean isStopLoop;
    private static final String TAG = MainActivity.class.getSimpleName();
    Button buttonStartThread, buttonStopThread;
    TextView textViewNumber;
    int count = 0;

    Handler handler;
    CustomLooperThread customLooperThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStartThread = findViewById(R.id.button_start_thread);
        buttonStopThread = findViewById(R.id.button_stop_thread);
        textViewNumber = findViewById(R.id.textview_number);
        buttonStartThread.setOnClickListener(this);
        buttonStopThread.setOnClickListener(this);

        Log.i(TAG, "onCreate: at Thread id : " + Thread.currentThread().getId());

        customLooperThread = new CustomLooperThread();
        customLooperThread.start();

        //initialize Handler with reference to Looper
//        handler = new Handler(Looper.getMainLooper()); //should have reference to message queue of the UI Thread.
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start_thread:
                isStopLoop = true;
//                executeOnCustomLooper();
                executeOnCustomLooperWithCustomHandler();
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_stop_thread:
                isStopLoop = false;
                break;
            default:
                break;
        }
    }

    private void executeOnCustomLooper(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isStopLoop){
                    try {
                        Log.i(TAG, "Thread that sends the message at Thread id : " + Thread.currentThread().getId());
                        Thread.sleep(1000);
                        count++;
                        Message message = new Message();
                        message.obj = "" + count;
                        customLooperThread.handler.sendMessage(message); //send message to handler that had been initialized in our own CustomLooper class
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void executeOnCustomLooperWithCustomHandler(){ //this method sends message to message queue UI Thread through custom handler
        customLooperThread.handler.post(new Runnable() {
            @Override
            public void run() {
                while (isStopLoop){
                    try{
                        Thread.sleep(1000);
                        count++;

                        Log.i(TAG, "Thread id of Runnable posted : " + Thread.currentThread().getId());

                        //this method makes sure that we communicate from non-UI thread to a UI thread
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i(TAG, "Thread id of runOnUiThread : " + Thread.currentThread().getId());
                                textViewNumber.setText("" + count);
                            }
                        });
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}