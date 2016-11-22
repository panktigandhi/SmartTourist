package majorproject.amity.smarttourist.models;

import java.util.List;

/**
 * Created by vijim_000 on 5/11/2016.
 */
public class MapDirections {
    public List<GeocodedWaypoints> geocoded_waypoints;
    public List<Routes> routes;
    public String status;

    public static class GeocodedWaypoints {
        public String geocoder_status;
        public String place_id;
        public List<String> types;
    }

    public static class Routes {
        public Bounds bounds;
        public String copyrights;
        public List<Legs> legs;
        public PolyLine overview_polyline;
        public String summary;
        public List<String> warnings;
        public List<String> waypoint_order;
    }

    public static class Bounds {
        public LocationDet northeast;
        public LocationDet southwest;
    }

    public static class Legs {
        public TimeDesc arrival_time;
        public TimeDesc departure_time;
        public Distance distance;
        public Distance duration;
        public String end_address;
        public LocationDet end_location;
        public String start_address;
        public LocationDet start_location;
        public List<Steps> steps;
        public List<String> traffic_speed_entry;
        public List<String> via_waypoint;
    }


    public static class TimeDesc {
        public String text;
        public String time_zone;
        public double value;
    }
    public static class LocationDet {
        public float lat;
        public float lng;
    }
    public static class Distance {
        public String text;
        public double value;
    }

    public static class PolyLine {
        public String points;
    }
}
