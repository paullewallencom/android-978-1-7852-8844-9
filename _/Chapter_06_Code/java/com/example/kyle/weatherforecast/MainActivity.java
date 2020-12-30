package com.example.kyle.weatherforecast;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {
    private static final String DEBUG_TAG = "tag";
    //
    private static int notificationId;
    private ViewPager viewPager;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.pager);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance();
        String weekDay;
        SimpleDateFormat dayFormat;

        dayFormat = new SimpleDateFormat("cccc", Locale.getDefault());
        // dayFormat = new SimpleDateFormat("c LLL d", Locale.getDefault());
        // dayFormat = (SimpleDateFormat) new SimpleDateFormat().getDateInstance();
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText("Today").setTabListener(this));
        for (int i = 1; i < 5; i++) {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            weekDay = dayFormat.format(calendar.getTime());
            actionBar.addTab(actionBar.newTab().setText(weekDay).setTabListener(this));
        }

        if (notificationId == 0) {
            postAlert(0);
        }

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void postAlert(int i) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Weather Alert!")
                .setContentText(WeatherData.outlookArray[i])
                .setSmallIcon(R.drawable.small_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), WeatherData.symbolArray[i]))
                .setAutoCancel(true)
                .setTicker("Wrap up warm!")
                // Heads-up and lock screen notifications
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(new long[]{100, 100, 100, 200, 200})
                .setVibrate(new long[]{0})
                .setCategory(Notification.CATEGORY_ALARM);

        NotificationCompat.BigPictureStyle bigStyle = new NotificationCompat.BigPictureStyle();
        bigStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.snow_scene));
        builder.setStyle(bigStyle);

        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class).addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, builder.build());

        notificationId++;
        Log.d(DEBUG_TAG, "ID: " + notificationId);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }
}