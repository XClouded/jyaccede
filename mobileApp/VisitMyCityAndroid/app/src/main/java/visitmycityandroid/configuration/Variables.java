package visitmycityandroid.configuration;

public interface Variables {

    /** API URL **/
    public static final String PostLocationUrl = "http://www.visitmycity.com/messages";
    public static final String SearchLocationUrl = "http://dev.jaccede.com/api/v2/places/search/?where=";
    public static final String SearchCategorieUrl = "http://dev.jaccede.com/api/v2/places/categories/";
    //public static final String SeachNearbyPlaces = "http://api.navitia.io/v1/coverage/{0}/coords/{1};{2}/places_nearby";
    public static final String SeachNearbyPlaces = "http://api.navitia.io/v1/coverage/sf/coords/-122.402770;37.794682/places_nearby";

    /** ACTIVITY NAME **/
    public static final String ActivitySearch = "ActivitySearch";
    public static final String ActivityCloser = "ActivityCloser";

    /** JSON Node names **/
    public static final String TAG_CONTACTS = "contacts";
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_ADDRESS = "address";
    public static final String TAG_GENDER = "gender";
    public static final String TAG_PHONE = "phone";
    public static final String TAG_PHONE_MOBILE = "mobile";
    public static final String TAG_PHONE_HOME = "home";
    public static final String TAG_PHONE_OFFICE = "office";
}
