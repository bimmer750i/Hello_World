package com.example.world_hello;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class MainActivity extends Activity implements View.OnClickListener {

    private TextView txtView;
    private NotificationReceiver nReceiver;


    private static int NOTIFY_ID = 0;
    private static final String CHANNEL_ID = "CHANNEL_ID";

    SQLiteDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("posted");
        registerReceiver(nReceiver,filter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nReceiver);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnCreateNotify){
            Notify();
        }
    }

    class NotificationReceiver extends BroadcastReceiver{

        final DatabaseHelper helper = new DatabaseHelper(getApplicationContext());

        @Override
        public void onReceive(Context context, Intent intent) {
            String date = intent.getStringExtra("Time");
            String title = intent.getStringExtra("Title");
            String text = intent.getStringExtra("Text");
            Toast.makeText(getApplicationContext(),date + " " + title, Toast.LENGTH_SHORT).show();
            helper.insert(date, title, text);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void Notify() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground )
                .setContentTitle("WORLD_HELLO")
                .setContentText("NOTIFICATION GENERATOR DEMO")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFY_ID, builder.build());
        notificationManager.cancel(NOTIFY_ID);
        NOTIFY_ID++;
    }



}


