package majorproject.amity.smarttourist.models;

import java.util.List;

/**
 * Created by vijim_000 on 5/14/2016.
 */
public class Steps {

    public Distance distance;
    public Distance duration;
    public LocationDet end_location;
    public String html_instructions;
    public PolyLine polyline;
    public LocationDet start_location;
    public List<Steps> steps;
    public String travel_mode;

    public static class LocationDet {
        public float lat;
        public float lng;
    }
    public static class Distance {
        public String text;
        public double value;
    }

    public static class DurationDesc {
        public String text;
        public double value;
    }

    public static class PolyLine {
        public String points;
    }

}
