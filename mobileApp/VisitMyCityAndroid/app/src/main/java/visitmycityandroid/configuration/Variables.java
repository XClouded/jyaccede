package visitmycityandroid.configuration;

public interface Variables {

    /** API URL **/
    public static final String SearchLocationUrl = "http://dev.jaccede.com/api/v2/places/search/?where=";
    public static final String SearchCategorieUrl = "http://dev.jaccede.com/api/v2/places/categories/";
    public static final String postLocationUrl = "http://dev.jaccede.com/api/v2/places/create/";

    /** ACTIVITY NAME **/
    public static final String ActivitySearch = "ActivitySearch";
    public static final String ActivityCloser = "ActivityCloser";

    /** JSON Node names **/
    public static final String TAG_CATEGORY = "category";
    public static final String TAG_NAME = "name";
    public static final String TAG_ADDRESS = "address";
    public static final String TAG_LATITUDE = "latitude";
    public static final String TAG_LONGITUDE = "longitude";
    public static final String TAG_DESCRIPTION = "description";
}
