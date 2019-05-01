package com.example.androidnotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

    // 1 - Notification Channel
    // 2 - Notification Channel
    // 3 - Notification Manager

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "Android Notification";
    private static final String CHANNEL_NAME = "Shakeel Haider";
    private static final String CHANNEL_DESC = "Shakkel Haider send notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mClick = findViewById(R.id.notification);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }

        mClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                displayNotification();
            }
        });
    }

    private void displayNotification()
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("ohh, it works !!")
                .setContentText("My first Notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationMGR =  NotificationManagerCompat.from(this);
        mNotificationMGR.notify(1, mBuilder.build());


    }


}
