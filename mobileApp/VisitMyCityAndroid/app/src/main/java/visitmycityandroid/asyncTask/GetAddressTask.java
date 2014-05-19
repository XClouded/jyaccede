package visitmycityandroid.asyncTask;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GetAddressTask extends AsyncTask<String, Void, Address> {

    private Context mContext;

    public GetAddressTask(Context context) {
        super();
        mContext = context;
    }

    @Override
    protected Address doInBackground(String... params) {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        String locName = params[0];
        List<Address> addresses = null;
        Address address = null;

        try {
            addresses = geocoder.getFromLocationName(locName, 1);
        } catch (IOException e1) {
            Log.e("GetAddressTask", "IO Exception in getFromLocation()");
            return null;
        } catch (IllegalArgumentException e2) {
            Log.e("GetAddressTask", "Illegal arguments passed to address service");
            return null;
        }

        if(addresses != null && addresses.size() > 0){
            address = addresses.get(0);
        }

        return address;
    }
}
