package majorproject.amity.smarttourist.models;

/**
 * Created by vijim_000 on 5/10/2016.
 */
public class Currency {

    public boolean success;
    public String terms;
    public String privacy;
    public double timestamp;
    public String source;
    public Quote quotes;


    public static class Quote {
        public float USDUSD;
        public float USDAED;
        public float USDAUD;
        public float USDINR;
        public float USDSAR;
        public float USDPHP;
        public float USDPKR;
        public float USDEUR;
        public float USDOMR;
        public float USDSGD;
        /*
        */
    }
}
