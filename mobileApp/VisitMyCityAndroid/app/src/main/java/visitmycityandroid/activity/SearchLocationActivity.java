package visitmycityandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import visitmycityandroid.app.R;
import visitmycityandroid.configuration.Variables;

public class SearchLocationActivity extends VisitMyCityActivity implements View.OnClickListener {

    private double mLatitude;
    private double mLongitude;

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

        //LocationModel init and settings
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

        //event handler
        Button sendMessageButton = (Button)findViewById(R.id.searchButton);
        sendMessageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText inputAddress = (EditText) findViewById(R.id.inputAddress);
        EditText inputLatitude = (EditText) findViewById(R.id.inputLatitude);
        EditText inputLongitude = (EditText) findViewById(R.id.inputLongitude);

        Intent intentMaps = new Intent(this, MapsActivity.class);
        intentMaps.putExtra("address", inputAddress.getText().toString());
        intentMaps.putExtra("latitude", inputLatitude.getText().toString());
        intentMaps.putExtra("longitude", inputLongitude.getText().toString());
        intentMaps.putExtra("currentLatitude", String.valueOf(mLatitude));
        intentMaps.putExtra("currentLongitude", String.valueOf(mLongitude));
        intentMaps.putExtra("fromActivity", Variables.ActivitySearch);
        startActivity(intentMaps);

        inputAddress.setText("");
        inputLatitude.setText("");
        inputLongitude.setText("");
    }

    /** Update GUI with a location
     *
     * @param l the location to display.
     */
    private void updateLocation(Location l){
        if(l != null) {
            mLatitude = l.getLatitude();
            mLongitude = l.getLongitude();
        }
    }
}
