package com.example.mertdesktop.schoolproject;

import android.app.*;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;


public class NotificationEmptyClass extends Service {
    private String myString = "<Empty>";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    public void readSettings() {
        try {
            FileInputStream fileInputStream = openFileInput("Setting.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            int i = 0;
            while ((lines = bufferedReader.readLine()) != null) {
                VariableClass.settingArray[i] = lines;
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        readSettings();
        if(VariableClass.settingArray[2].equals("checkBox is not checked")) {
            Intent intentb = new Intent();
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentb, 0);
            Notification notification = new Notification.Builder(this)
                    .setTicker(null)
                    .setContentTitle("Your LCD Message")
                    .setContentText(myString)
                    .setSmallIcon(R.drawable.event_availablexh)
                    .setContentIntent(pendingIntent)
                    .build();

            notification.flags = android.app.Notification.FLAG_AUTO_CANCEL;
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(0, notification);
            stopSelf();
        /*

        Gönderme baglantı işlemi


         */
        }
        return START_STICKY;
    }
}

