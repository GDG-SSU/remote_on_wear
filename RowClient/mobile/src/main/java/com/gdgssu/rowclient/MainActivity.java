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

import com.gdgssu.rowclient.model.AllPersonItems;
import com.gdgssu.rowclient.model.Person;
import com.gdgssu.rowclient.model.Program;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private AlarmManager mAlarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button shotnoti = (Button) findViewById(R.id.main_shotnoti);

        mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        String json = JsonFromFile.readJsonFromAssets("data.json", getBaseContext());
        Gson gson = new Gson();
        AllPersonItems data = gson.fromJson(json, AllPersonItems.class);

        ArrayList<Person> person = data.persons;
        ArrayList<Program> programArrayList = new ArrayList<>();
        for(int i=0; i<person.size(); i++){
            ArrayList<Program> programs = person.get(i).person_sche;
            for(int j=0; j<programs.size(); j++){
                Program program = programs.get(j);
                programArrayList.add(program);
            }
        }
        Collections.sort(programArrayList, new Comparator<Program>() {
            @Override
            public int compare(Program lhs, Program rhs) {
                if(lhs.date.compareTo(rhs.date) < 0)
                    return -1;
                return 1;
            }
        });

        for(int i=0; i<programArrayList.size(); i++){
            Log.i("gsg",programArrayList.get(i).date);
        }

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
