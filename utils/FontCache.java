package majorproject.amity.smarttourist.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import java.util.Hashtable;


public class FontCache {
    private static final Hashtable<String, Typeface> CACHE = new Hashtable<String, Typeface>();

    public static Typeface get(AssetManager manager, String name) {
        synchronized (CACHE) {

            if (!CACHE.containsKey(name)) {
                Typeface t = Typeface.createFromAsset(manager, name);
                CACHE.put(name, t);
            }
            return CACHE.get(name);
        }
    }
}
