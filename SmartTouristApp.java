package majorproject.amity.smarttourist;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by vijim_000 on 5/20/2016.
 */
public class SmartTouristApp extends Application {

    private static SmartTouristApp instance;

    public SmartTouristApp() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
