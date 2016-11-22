package majorproject.amity.smarttourist.models;

/**
 * Created by vijim_000 on 6/8/2016.
 */
public class Elements {

    public DataAbout distance;
    public DataAbout duration;
    public String status;

    public static class DataAbout {
        public String text;
        public float value;
    }
}
