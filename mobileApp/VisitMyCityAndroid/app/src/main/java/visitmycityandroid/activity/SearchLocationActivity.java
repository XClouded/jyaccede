package visitmycityandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import visitmycityandroid.app.R;

public class SearchLocationActivity extends Activity {
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
        setContentView(R.layout.activity_search_location);

        //Location init and settings
        try {
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
        catch (Exception e){
            Log.v("MainActivity-GetLocation", e.getMessage());
        }

        //Spinner init and settings
        Spinner spinner = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_location, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_addLocation :
                Intent intentAdd = new Intent(this, AddLocationActivity.class);
                startActivity(intentAdd);
            case R.id.action_searchLocation :
                Intent intentSearch = new Intent(this, SearchLocationActivity.class);
                startActivity(intentSearch);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    /** Update GUI with a location
     *
     * @param l the location to display.
     */
    private void updateLocation(Location l){
        String locationDisplay = getResources().getString(R.string.unknownLocationLabel);
        double latitude = 0;
        double longitude = 0;

        if(l != null) {
            latitude = l.getLatitude();
            longitude = l.getLongitude();
            Geocoder gc = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = gc.getFromLocation(latitude, longitude, 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    locationDisplay = address.getLocality();
                }
            }
            catch (IOException e) {
                Log.v("MainActivity-UpdateLocation", e.getMessage());
            }
        }

        TextView positionLabel = (TextView)findViewById(R.id.positionLabel);
        positionLabel.setText(locationDisplay);

        TextView latitudeLabel = (TextView)findViewById(R.id.latitudeLabel);
        latitudeLabel.setText(String.valueOf(latitude));

        TextView longitudeLabel = (TextView)findViewById(R.id.longitudeLabel);
        longitudeLabel.setText(String.valueOf(longitude));
    }
}
