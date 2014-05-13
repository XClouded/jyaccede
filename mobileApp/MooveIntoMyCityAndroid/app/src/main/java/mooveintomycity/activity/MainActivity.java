package mooveintomycity.activity;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.apache.http.client.methods.HttpRequestBase;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Locale;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class MainActivity extends Activity {
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String s) {}

        @Override
        public void onProviderDisabled(String s) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Location init and settings
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        String provider = lm.getBestProvider(criteria, true);
        Location l = lm.getLastKnownLocation(provider);
        updateLocation(l);
        lm.requestLocationUpdates(provider, 2000, 100, mLocationListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    /** Update GUI with a location
     *
     * @param l the location to display.
     */
    private void updateLocation(Location l){
        String locationDisplay = getResources().getString(R.string.unknownLocationLabel);

        if(l != null) {
            double latitude = l.getLatitude();
            double longitude = l.getLongitude();
            Geocoder gc = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = gc.getFromLocation(latitude, longitude, 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    locationDisplay = address.getLocality();
                }
            }
            catch (IOException e) {
                Log.v("MainActivity", e.getMessage());
            }
        }

        TextView positionLabel = (TextView)findViewById(R.id.positionLabel);
        positionLabel.setText(locationDisplay);

        TextView latitudeLabel = (TextView)findViewById(R.id.positionLabel);
        latitudeLabel.setText(locationDisplay);

        TextView longitudeLabel = (TextView)findViewById(R.id.positionLabel);
        longitudeLabel .setText(locationDisplay);
    }

    private static final String ACCESS_KEY_ID = "test-jispapi-access-key-id";
    private static final String SECRET_ACCESS_KEY = "test-jispapi-secret-access-key";
    private static final String HEADER_NAME_TIMESTAMP = "x-jispapi-timestamp";
    private static final String HEADER_NAME_AUTH = "Authorization";
    private static final String HEADER_PART_JISPAPI = "JISPAPI";

    private static final String computeStringToSign(HttpRequestBase request, long now) {

        URI uri = request.getURI();
        String method = request.getMethod().toUpperCase(java.util.Locale.getDefault());
        String path = uri.getPath();

        StringBuilder buff = new StringBuilder();
        buff.append(method);
        buff.append("\n");
        buff.append("x-jispapi-timestamp:").append(now);
        buff.append("\n");
        buff.append(path);

        return buff.toString();
    }

    public static final void setAuthHeaders(HttpRequestBase request) {

        try {

            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_ACCESS_KEY.getBytes("UTF-8"), mac.getAlgorithm());
            mac.init(keySpec);

            long now = System.currentTimeMillis();
            byte[] signed = mac.doFinal(computeStringToSign(request, now).getBytes());

            String signature = Base64.encodeToString(HexUtils.encodeHexString(signed).getBytes("UTF-8"), Base64.NO_WRAP);

            StringBuilder buff = new StringBuilder(HEADER_PART_JISPAPI);
            buff.append(" ").append(ACCESS_KEY_ID).append(":").append(signature);
            String auth = buff.toString();

            request.setHeader(HEADER_NAME_TIMESTAMP, String.valueOf(now));
            request.setHeader(HEADER_NAME_AUTH, auth);

        } catch (Exception e) {
            Log.e(TAG, "computeAuthHeader(error computing auth header: " + e.getMessage() + ")");
        }
    }
}
