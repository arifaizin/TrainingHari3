package com.imastudio.traininghari3;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

public class StreamingService extends Service {

    private MediaPlayer player = new MediaPlayer();
    private BroadcastReceiver receiver;
    private IntentFilter filter;

    private static final String TAG = "StreamingService";

    @Override
    public void onCreate() {
        //terima broadcast
        filter = new IntentFilter();
        filter.addAction("stop");
        filter.addAction("start");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals("stop")){
                    if (player != null){
                        pauseMedia();
                    }
                } else if (action.equals("start")) {
                    playMedia();
                }
            }
        };
        registerReceiver(receiver, filter);

        //mediaplayer
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                pauseMedia();
            }
        });

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals("play")) {
            player.reset();
            if (!player.isPlaying()) {
                try {
                    player.setDataSource("http://103.16.198.36:9160/stream");
                    player.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e){
                    e.printStackTrace();
                }
                Log.d(TAG, "onStartCommand: "+ "service start");
                showNotif();
            }
        } else {
            pauseMedia();
            Log.d(TAG, "onStartCommand: "+ "service stop");

        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void showNotif() {
        Intent stopIntent = new Intent(this, StreamingReceiver.class);
        stopIntent.setAction("exit");
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(this, 12345, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent pauseIntent = new Intent(this, StreamingReceiver.class);
        pauseIntent.setAction("stop");
        PendingIntent pausePendingIntent = PendingIntent.getBroadcast(this, 12345, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent playIntent = new Intent(this, StreamingReceiver.class);
        playIntent.setAction("start");
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(this, 12345, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_media_play)
                .setOngoing(true)
                .setContentTitle("radio streaming")
                .setContentText("By Arif Faizin")
                .addAction(android.R.drawable.ic_media_play, "PLAY", playPendingIntent)
                .addAction(android.R.drawable.ic_media_pause, "PAUSE", pausePendingIntent)
                .addAction(android.R.drawable.ic_menu_close_clear_cancel, "EXIT", stopPendingIntent)
        ;
        startForeground(115, builder.build());
    }

    private void pauseMedia() {
        if (player.isPlaying()){
            player.pause();
        }
    }

    private void playMedia() {
        if (!player.isPlaying()){
            player.start();
        }
    }

    public StreamingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player.isPlaying()){
            player.stop();
        }
        player.release();

        unregisterReceiver(receiver);

        removeNotif();
    }

    private void removeNotif() {
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1);
    }

}
