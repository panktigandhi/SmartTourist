package majorproject.amity.smarttourist.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.List;

import majorproject.amity.smarttourist.models.Events;
import majorproject.amity.smarttourist.models.HotelInfo;
import majorproject.amity.smarttourist.models.Poi;
import majorproject.amity.smarttourist.models.RestaurantDetails;


/**
 * Created by vijim_000 on 5/21/2016.
 */
public class TheSmartTourist {

    public static boolean isIsLoggedIn = false;
    public static List<HotelInfo> globalHotelInfo;
    public static List<RestaurantDetails> globalRestInfo;
    public static List<Poi> globalPOI;
    public static List<Events> globalEvents;
    public static List<Poi> favorites;
//    public static List<Itinerary> globalItinerary;
    public static boolean isLoggedIn = false;
    public static String fbProfileName;
    public static int no_of_places_visited = 0;

    public static double UserLat, UserLon;
    public static boolean AppBarOffset;
    private static android.location.Location currentLocation;

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static double getUserLat() {
        return UserLat;
    }

    public static void setUserLat(double l) {
        TheSmartTourist.UserLat = l;
    }

    public static double getUserLon() {
        return UserLon;
    }

    public static void setUserLon(double l) {
        TheSmartTourist.UserLon = l;
    }

    public static android.location.Location getCurrentLocation() {
        return TheSmartTourist.currentLocation;
    }

    public static void setCurrentLocation(android.location.Location currentLocation) {
        TheSmartTourist.currentLocation = currentLocation;
    }

    public static enum Types{
        ONE(1, "Restuarants"),
        TWO(2, "Hotels"),
        THREE(3, "PlacesOfInterest"),
        FOUR(4, "Explore"),
        FIVE(5, "Events"),
        SIX(6,"Currency"),
        SEVEN(7,"Itinerary"),
        EIGHT(8,"Map"),
        NINE(9,"AroundMe"),
        TEN(10,"Settings")
        ;

        private int code;
        private String name;

        private Types(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public static enum Status{
        ONE(1, "Normal"),
        TWO(2, "AroundMe"),
        THREE(3, "ItineraryMap"),
        FOUR(4, "SingleMap"),
        FIVE(5, "DirectionsMap")
        ;

        private int code;
        private String name;

        private Status(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }


}
