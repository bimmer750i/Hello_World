package com.example.world_hello

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification


class NLService : NotificationListenerService() {

    private var connected = false;

    override fun onListenerConnected() {
        connected = true;
        super.onListenerConnected()
    }

    override fun onListenerDisconnected() {
        connected = false;
        super.onListenerDisconnected()
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val extras = sbn.notification.extras;
        val title = extras.getCharSequence(Notification.EXTRA_TITLE);
        val text = extras.getCharSequence(Notification.EXTRA_TEXT);
        cancelNotification(sbn.key);
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
    }





}