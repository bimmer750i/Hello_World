

package com.example.world_hello;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NLService extends NotificationListenerService {
    final String FILENAME = "file";
    private String TAG = this.getClass().getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.world_hello.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {

        long time = sbn.getPostTime();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("d-MMM-YYYY HH:mm:s");
        final DatabaseHelper helper = new DatabaseHelper(this);
        String date_string = format.format(date);

        String title = sbn.getNotification().extras.getString("android.title");
        String text = sbn.getNotification().extras.getString("android.text");


        Intent intent = new Intent("posted");
        intent.putExtra("Time", date_string);
        intent.putExtra("Title", title);
        intent.putExtra("Text", text);
        sendBroadcast(intent);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Intent i = new  Intent("com.example.world_hello.NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("removed",sbn.getPackageName());
        sendBroadcast(i);
    }





}