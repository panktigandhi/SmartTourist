package majorproject.amity.smarttourist.models;

import java.util.List;

/**
 * Created by vijim_000 on 5/20/2016.
 */
public class HotelInfo {
    public String HotelID;
    public String Name;
    public LocationOfHotel Location;
    public String Description;
    public String DetailsUrl;
    public String StarRating;
    public String ThumbnailUrl;
    public String GuestRating;
    public String GuestReviewCount;
    public AmenityList AmenityList;

    public static class LocationOfHotel {
        public String StreetAddress;
        public String City;
        public String Province;
        public String Country;
        public LocationInLatLon GeoLocation;

        public static class LocationInLatLon{
            public String Latitude;
            public String Longitude;
        }
    }

    public static class AmenityList {
        public List<String> Amenity;
        //public String Amenity;
    }

    /*


     */
}
