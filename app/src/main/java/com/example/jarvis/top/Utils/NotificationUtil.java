/*
* Created by Matheus Rocigno
* */

package com.example.jarvis.top.Utils;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;


public class NotificationUtil {

    public static void simpleNotificaion(Context context, String title, String content, int smallIcon, int ID_Notification){
        createNotificationChannel(context, "-1000","Simple notifications", "That is a channel to showing simple notifications", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "-1000")
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(3)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content));
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(ID_Notification, mBuilder.build());
    }

    public static void showNotification(Context context, String title, String content, String group, String channel, Intent resultIntent, int ID_Notification, int priority, int smallIcon){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channel)
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(priority) //Maxima
                .setAutoCancel(true)
                .setGroup(group)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content))
                .setVibrate(new long[] {1000, 1000});

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        //stackBuilder.addParentStack(Main.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(ID_Notification, mBuilder.build());

        //Summary function works only version equal or greater than API 24 (Nougat)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationUtil.summaryNotification(context, group, channel, smallIcon);
        }
    }

    /**
     * To group the notifications
     */
    private static void summaryNotification(Context contexto, String grupo, String canal, int smallIcon){
        NotificationCompat.Builder summaryNotification = new NotificationCompat.Builder(contexto, canal)
                .setAutoCancel(true)
                .setSmallIcon(smallIcon)
                .setGroup(grupo)
                .setGroupSummary(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(contexto);
        notificationManager.notify(-1, summaryNotification.build());
    }

    /**
     * Only for Oreo version
     * With group declaration
     */
    public static void createNotificationChannel(Context context, String channel_id, String channel_name, String channel_description, int importance, String group_id, String group_name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channel_id, channel_name, importance);
            channel.setDescription(channel_description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
            notificationManager.createNotificationChannelGroup(new NotificationChannelGroup(group_id, group_name));
        }
    }

    /**
     * Only for Oreo version
     * Without group declaration
     */
    public static void createNotificationChannel(Context context, String channel_id, String channel_name, String channel_description, int importance) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channel_id, channel_name, importance);
            channel.setDescription(channel_description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }

}
