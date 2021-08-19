

package com.example.world_hello;

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
    private NLServiceReceiver nlservicereciver;
    @Override
    public void onCreate() {
        super.onCreate();
        nlservicereciver = new NLServiceReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.world_hello.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
        registerReceiver(nlservicereciver,filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nlservicereciver);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String pack = sbn.getPackageName();
        String ticker = (String) sbn.getNotification().tickerText;
        Bundle extras = sbn.getNotification().extras;
        long time = sbn.getPostTime();
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("d-MMM-YYYY HH:mm:s");
        final DatabaseHelper helper = new DatabaseHelper(this);

        String title = "";
        String text = "";




        if (extras.containsKey("android.title")) {
            title = extras.getString("android.title");
        }

        if (extras.containsKey("android.text")) {
            if (extras.getCharSequence("android.text") != null) {
                text = extras.getCharSequence("android.text").toString();
            }
        }
        helper.insert(title, text);
        Toast.makeText(getApplicationContext(), "Inserted", Toast.LENGTH_SHORT);


    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Intent i = new  Intent("com.example.world_hello.NOTIFICATION_LISTENER_EXAMPLE");
        i.putExtra("removed",sbn.getPackageName());
        sendBroadcast(i);
    }

    void writeFile(String s) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(FILENAME, MODE_APPEND)));
            bw.write(s);
            bw.write("\n");
            bw.close();
            Log.d("Ыщ", "Notification info saved");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class NLServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra("command").equals("clearall")){
                NLService.this.cancelAllNotifications();
            }
            else if(intent.getStringExtra("command").equals("list")){
                Intent i1 = new  Intent("com.example.world_hello.NOTIFICATION_LISTENER_EXAMPLE");
                sendBroadcast(i1);
                int i=1;
                for (StatusBarNotification sbn : NLService.this.getActiveNotifications()) {
                    Intent i2 = new  Intent("com.example.world_hello.NOTIFICATION_LISTENER_EXAMPLE");
                    i2.putExtra("notification_event",i +" " + sbn.getNotification().tickerText + "n");
                    sendBroadcast(i2);
                    i++;
                }
                Intent i3 = new  Intent("com.example.world_hello.NOTIFICATION_LISTENER_EXAMPLE");
                sendBroadcast(i3);

            }

        }
    }

}