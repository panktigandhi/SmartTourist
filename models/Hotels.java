package majorproject.amity.smarttourist.models;

import java.util.List;

/**
 * Created by PanktiGandhi on 4/18/16.
 */
public class Hotels {
    public String MatchingHotelCount;
    public String HotelCount;
    public HotelInfoList HotelInfoList;

    public static class HotelInfoList {
        public List<HotelInfo> HotelInfo;
    }
}


