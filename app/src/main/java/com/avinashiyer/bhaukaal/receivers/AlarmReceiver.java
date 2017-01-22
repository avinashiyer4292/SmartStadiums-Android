package com.avinashiyer.bhaukaal.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.avinashiyer.bhaukaal.R;
import com.avinashiyer.bhaukaal.activities.NotificationViewActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by avinashiyer on 1/22/17.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //create notification here
        //Toast.makeText(context,"Hi",Toast.LENGTH_SHORT).show();
        Notification(context);
    }
    public void Notification(Context context) {
        // Set Notification Title
        String strtitle = "New notification";
        // Set Notification Text
        String strtext = "Get 50% off on HotDogs";

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(context, NotificationViewActivity.class);
        // Send data to NotificationView Class
        intent.putExtra("title", strtitle);
        intent.putExtra("text", strtext);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        //Create Notification using NotificationCompat.Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                // Set Icon
                .setSmallIcon(R.drawable.ic_launcher1)
                // Set Ticker Message
                .setTicker("notification ticker!")
                // Set Title
                .setContentTitle("content title")
                // Set Text
                .setContentText("content text")
                // Add an Action Button below Notification
                .addAction(R.drawable.ic_launcher1, "Action Button", pIntent)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Dismiss Notification
                .setAutoCancel(true);

        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(0, builder.build());

    }

}
