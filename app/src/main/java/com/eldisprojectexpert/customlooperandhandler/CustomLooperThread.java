package com.eldisprojectexpert.customlooperandhandler;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class CustomLooperThread extends Thread {
    private static final String TAG = CustomLooperThread.class.getSimpleName();

    Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    public void run() {
        Looper.prepare();

        //instance needs to be done after the Looper.prepare
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) { //Message API is used to send any arbitrary data to a Handler
                super.handleMessage(msg);
                Log.i(TAG, "handleMessage: at Thread id : " + Thread.currentThread().getId() + "and Thread Name is " + Thread.currentThread().getName());
                Log.i(TAG, "handleMessage:  Count : "  + msg.obj); //msg.obj will be initialized by thread that send Message to this Handler.
            }
        };

        Looper.loop();
    }
}
