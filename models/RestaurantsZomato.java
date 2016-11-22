package majorproject.amity.smarttourist.models;

import java.util.List;

/**
 * Created by vijim_000 on 5/6/2016.
 */
public class RestaurantsZomato {

    public LocationDetails location;
    public Popularity popularity;
    public String link;
    public NearbyRestaurants nearby_restaurants;

    public static class LocationDetails {
        public String entity_type;
        public int entity_id;
        public String title;
        public String latitude;
        public String longitude;
        public int city_id;
        public String city_name;
        public int country_id;
        public String country_name;
    }

    public static class Popularity {
        public String popularity;
        public String nightlife_index;
        public List<String> nearby_res;
        public List<String> top_cuisines;
        public String popularity_res;
        public String nightlife_res;
        public String subzone;
        public int subzone_id;
        public String city;
    }

    public static class NearbyRestaurants {

        public RestaurantDetails restaurant;
    }

    /*
    {
  "nearby_restaurants": {
    "1": {
      "restaurant": {

      }
    },
    "2": {
      "restaurant": {
      }
    },
    "3": {
      "restaurant": {

      }
    },
    "4": {
      "restaurant": {

      }
    },
    "5": {
      "restaurant": {

      }
    },
    "6": {
      "restaurant": {

      }
    },
    "7": {

      }
    },
    "8": {
      "restaurant": {

      }
    },
    "9": {
      "restaurant": {

      }
    }
  }
}
     */
}
