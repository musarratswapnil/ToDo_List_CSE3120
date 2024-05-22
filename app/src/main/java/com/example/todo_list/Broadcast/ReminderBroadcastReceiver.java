package com.example.todo_list.Broadcast;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.SmsManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.todo_list.R;

public class ReminderBroadcastReceiver extends BroadcastReceiver {
    private int generateNotificationId() {
        // Generate a unique notification ID using current timestamp
        return (int) System.currentTimeMillis();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the task details from the intent
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        boolean isSMS = intent.getBooleanExtra("isSMS", false);
        boolean isCall = intent.getBooleanExtra("isCall", false);
        int notificationId = generateNotificationId();
        if (isSMS) {
            // Handle SMS sending
            String phoneNumber = intent.getStringExtra("phoneNumber");
            String message = intent.getStringExtra("message");
            sendSMS(phoneNumber, message);
        } else if (isCall) {
            String phoneNumber = intent.getStringExtra("phoneNumber");
            handleNotification2(context, intent,notificationId);

            initiateCall(context, phoneNumber);
        } else {
            handleNotification(context, intent, notificationId);
            playAlarmSound(context);
        }

    }

    private void handleNotification(Context context, Intent intent,int notificationId) {
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        // Notification channel creation for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    context.getString(R.string.default_notification_channel_id),
                    context.getString(R.string.default_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Create and show notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.task_time)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        try {
            notificationManager.notify(notificationId, builder.build());
        } catch (SecurityException e) {
            // Handle the SecurityException
            e.printStackTrace();
        }    }

    private void handleNotification2(Context context, Intent originalIntent,int notificationId) {
        String title = originalIntent.getStringExtra("title");
        String content = originalIntent.getStringExtra("content");
        String phoneNumber = originalIntent.getStringExtra("phoneNumber");

        // Intent for opening the dialer
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse("tel:" + phoneNumber));
        dialIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                context, notificationId, dialIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Notification channel creation for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    context.getString(R.string.default_notification_channel_id),
                    "Call Notifications",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        // Create and show notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.task_time)
                .setContentTitle("Call " + phoneNumber)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setFullScreenIntent(notifyPendingIntent, true) // This is what you need for the notification to be shown as a heads-up notification
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        try {
            notificationManager.notify( notificationId, builder.build());
        } catch (SecurityException e) {
            // Handle the SecurityException
            e.printStackTrace();
        }
    }

    private void playAlarmSound(Context context) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmSound == null) {
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmSound);
        if (ringtone != null) {
            ringtone.play();
        }
    }
    private void sendSMS(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }
    private void initiateCall(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Log.e("ReminderBroadcastReceiver", "No activity to handle dial intent");
        }
    }
}