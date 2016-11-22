package majorproject.amity.smarttourist.models;

import java.util.List;

/**
 * Created by vijim_000 on 5/6/2016.
 */
public class RestaurantDetails {
    //https://developers.zomato.com/api/v2.1/restaurant?res_id=208335
    public Rest R;
    public String apikey;
    public String id;
    public String name;
    public String url;
    public LocationDetails location;
    public String cuisines;
    public String average_cost_for_two;
    public String price_range;
    public String currency;
    public List<String> offers;
    public String thumb;
    public UserRating user_rating;
    public String photos_url;
    public String menu_url;
    public String featured_image;
    public String has_online_delivery;
    public String is_delivering_now;
    public String deeplink;

    /*
        public static final Parcelable.Creator<VehicleStatus> CREATOR = new Creator<VehicleStatus>() {
        public VehicleStatus createFromParcel(Parcel source) {
            VehicleStatus mVehicleStatus = new VehicleStatus();

    public double Latitude;
    public String LocDateTime;
    public String LocationEn;
    public double Longitude;
    public boolean MovingWithoutDriver;
    public int OS;
    public double Odometer;
    public double Speed;
    public String VehicleId;
    public String VehicleModal;
    public String VehicleType;
    public int VehicleUno;
    mVehicleStatus.DriverFullName = source.readString();
    mVehicleStatus.DriverMobileNo = source.readString();
    mVehicleStatus.DynamicStatus = source.readInt();
    mVehicleStatus.HarshAcceleration = source.readInt();
    mVehicleStatus.HarshBreak = source.readInt();

    if (source.readInt() == 1)
    mVehicleStatus.Ideling = true;
    else
    mVehicleStatus.Ideling = false;

    if (source.readInt() == 1)
    mVehicleStatus.Ignition = true;
    else
    mVehicleStatus.Ignition = false;

    mVehicleStatus.LastStatusDuration = source.readString();
    mVehicleStatus.Latitude = source.readDouble();
    mVehicleStatus.LocDateTime = source.readString();
    mVehicleStatus.LocationEn = source.readString();
    mVehicleStatus.Longitude = source.readDouble();

    if (source.readInt() == 1)
    mVehicleStatus.MovingWithoutDriver = true;
    else
    mVehicleStatus.MovingWithoutDriver = false;
    mVehicleStatus.OS = source.readInt();
    mVehicleStatus.Odometer = source.readDouble();
    mVehicleStatus.Speed = source.readDouble();
    mVehicleStatus.VehicleId = source.readString();
    mVehicleStatus.VehicleModal = source.readString();
    mVehicleStatus.VehicleType = source.readString();
    mVehicleStatus.VehicleUno = source.readInt();
    return mVehicleStatus;
}

    public VehicleStatus[] newArray(int size) {

        return new VehicleStatus[size];
    }

};
     */

    public RestaurantDetails(List<String> offers, Rest r, String apikey, String id, String name, String url, LocationDetails location, String cuisines, String average_cost_for_two, String price_range, String currency, String thumb, UserRating user_rating, String photos_url, String menu_url, String featured_image, String has_online_delivery, String is_delivering_now, String deeplink, String events_url, String all_reviews_count, String photo_count, String phone_numbers) {
        this.offers = offers;
        R = r;
        this.apikey = apikey;
        this.id = id;
        this.name = name;
        this.url = url;
        this.location = location;
        this.cuisines = cuisines;
        this.average_cost_for_two = average_cost_for_two;
        this.price_range = price_range;
        this.currency = currency;
        this.thumb = thumb;
        this.user_rating = user_rating;
        this.photos_url = photos_url;
        this.menu_url = menu_url;
        this.featured_image = featured_image;
        this.has_online_delivery = has_online_delivery;
        this.is_delivering_now = is_delivering_now;
        this.deeplink = deeplink;
        this.events_url = events_url;
        this.all_reviews_count = all_reviews_count;
        this.photo_count = photo_count;
        this.phone_numbers = phone_numbers;
    }

    public String events_url;
    public String all_reviews_count;
    public String photo_count;
    public String phone_numbers;

    public LocationDetails getLocation(){
        return location;
    }

    public String getName(){
        return name;
    }

    public String getCuisine(){
        return cuisines;
    }

    public UserRating getZomatoRating(){
        return user_rating;
    }


    public static class LocationDetails {
        public String address;
        public String locality;
        public String city;
        public String latitude;
        public String longitude;
        public String zipcode;
        public String country_id;

        public String getAddress() {
            return address;
        }
    }

   public static class UserRating {
        public String aggregate_rating;
        public String rating_text;
        public String rating_color;
        public String votes;
    }

    public static class Rest {
        public float res_id;
    }
}

