package majorproject.amity.smarttourist.models;

import java.util.List;

/**
 * Created by vijim_000 on 6/8/2016.
 */
public class Distances {
    public List<String> destination_addresses;
    public List<String> origin_addresses;
    public List<Rows> rows;
    public String status;

    public static class Rows {
        public List<Elements> elements;
    }
}
