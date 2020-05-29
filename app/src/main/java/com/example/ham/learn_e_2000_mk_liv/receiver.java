package com.example.ham.learn_e_2000_mk_liv;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

/**
 * Created by H.A.M on 12/5/2017.
 */

public class receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, main.class), 0);
        NotificationCompat.Builder build = new NotificationCompat.Builder(context);
        build.setSmallIcon(R.drawable.ic_notifaction_icon);
        build.setContentTitle("كلمة اليوم");
        build.setContentText("هيا تعلم كلمة جديدة");
        build.setContentIntent(pendingIntent);
        build.setDefaults(NotificationCompat.DEFAULT_SOUND);
        build.setAutoCancel(true);
        NotificationManager N = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        N.cancel(1);
        N.notify(1, build.build());
    }
}


