package com.gdgssu.rowclient;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AlarmManager mAlarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button shotnoti = (Button) findViewById(R.id.main_shotnoti);

        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        shotnoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo : 예시 노티피케이션을 전송하는 부분 구현

                Notification.Builder builder = new Notification.Builder(getBaseContext());
                builder.setContentTitle("Scheduled Notification");
                builder.setContentText("Content");
                builder.setSmallIcon(R.mipmap.ic_launcher);


                Intent notificationIntent = new Intent(getBaseContext(), IUAlarmReceiver.class);
                notificationIntent.putExtra(IUAlarmReceiver.NOTIFICATION_ID, 1);
                notificationIntent.putExtra(IUAlarmReceiver.NOTIFICATION, builder.build());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                Calendar calendar = Calendar.getInstance();
                calendar.set(2015, Calendar.OCTOBER, 31, 4, 15);
                calendar.set(Calendar.AM_PM, Calendar.PM);
                long futureInMillis = calendar.getTimeInMillis();
                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
            }
        });
    }
}
