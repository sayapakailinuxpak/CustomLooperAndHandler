package com.eldisprojectexpert.customlooperandhandler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;


public class CustomHandlerThread extends HandlerThread {

    private static final String TAG = "CustomHandlerThread";
    Handler handler;

    public CustomHandlerThread(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.i(TAG, "handleMessage: Thread id when message is posted : " + Thread.currentThread().getId() + ", Count = " + msg.obj);
            }
        };
    }
}
