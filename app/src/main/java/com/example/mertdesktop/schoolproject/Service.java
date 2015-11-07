package com.example.mertdesktop.schoolproject;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;


public class Service extends android.app.Service {
    private int serviceCopyId;
    PendingIntent pendingIntent;
    public final IBinder iBinder = new LocalBinder();
   public class LocalBinder extends Binder{
       Service getService(){
           return Service.this;
       }
   }
    final class TheThread implements Runnable{
        int serviceId;
        TheThread(int serviceId){
            this.serviceId = serviceId;
        }

        @Override
        public void run() {
        /*




        Bağlantı gönderme işlemi




         */
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.serviceCopyId = startId;
        return START_STICKY;
    }

    public void sendOnTime() {
        Notification notification = new Notification.Builder(this)
                .setTicker("TickerTitle")
                .setContentTitle("Title")
                .setContentText("ContentText")
                .setSmallIcon(R.drawable.assigment_m)
                .setContentIntent(pendingIntent)
                .build();

        notification.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0,notification);
        }
    public void thread (){
        Thread thread = new Thread(new TheThread(serviceCopyId));
        thread.start();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
}
