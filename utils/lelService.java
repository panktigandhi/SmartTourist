package majorproject.amity.smarttourist.utils;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import majorproject.amity.smarttourist.R;


/**
 * Created by vijim_000 on 5/3/2016.
 */
public class lelService extends IntentService {
    // Must create a default constructor
    final static String ACTION = "NotifyServiceAction";
    final static String STOP_SERVICE = "";
    final static int RQS_STOP_SERVICE = 1;

    //NotifyServiceReceiver notifyServiceReceiver;

    private static final int MY_NOTIFICATION_ID=1;
    private NotificationManager notificationManager;
    private Notification myNotification;
    private final String myBlog = "http://android-er.blogspot.com/";


    public lelService() {
        // Used to name the worker thread, important only for debugging.
        super("test-service");
    }

    @Override
    public void onCreate() {
        super.onCreate(); // if you override onCreate(), make sure to call super().
        // If a Context object is needed, call getApplicationContext() here.
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION);
        //registerReceiver(notifyServiceReceiver, intentFilter);

// Send Notification
        notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        myNotification = new Notification(R.drawable.logo_big, "Notification!", System.currentTimeMillis());
        Context context = getApplicationContext();
        String notificationTitle = "Exercise of Notification!";
        String notificationText = "http://android-er.blogspot.com/";
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myBlog));
        PendingIntent pendingIntent
                = PendingIntent.getActivity(getBaseContext(),
                0, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        myNotification.defaults |= Notification.DEFAULT_SOUND;
        myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        myNotification.setLatestEventInfo(context,
                notificationTitle,
                notificationText,
                pendingIntent);
        notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
    }
}
