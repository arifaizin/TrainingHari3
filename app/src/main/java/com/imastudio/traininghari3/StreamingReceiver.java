package com.imastudio.traininghari3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StreamingReceiver extends BroadcastReceiver {
    private static final String TAG = "StreamingReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
       String aksi = intent.getAction();
        Log.d(TAG, "onReceive: "+ aksi);
       if (aksi.equals("start")){
           context.sendBroadcast(new Intent("start"));
       } else if (aksi.equals("stop")){
           context.sendBroadcast(new Intent("stop"));
       } else if (aksi.equals("exit")){
           Intent service = new Intent(context, StreamingService.class);
           context.stopService(service);
        }
    }
}
