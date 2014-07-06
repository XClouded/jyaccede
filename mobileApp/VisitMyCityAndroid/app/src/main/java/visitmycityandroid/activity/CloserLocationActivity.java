package visitmycityandroid.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import visitmycityandroid.app.R;
import visitmycityandroid.asyncTask.JaccedeCategorieTask;
import visitmycityandroid.configuration.Variables;
import visitmycityandroid.interfaces.CategorieListener;
import visitmycityandroid.model.CategorieModel;

public class CloserLocationActivity extends VisitMyCityActivity  implements View.OnClickListener, CategorieListener {
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
        setContentView(R.layout.activity_closer_location);

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
        }

        JaccedeCategorieTask jc = new JaccedeCategorieTask(this, Variables.SearchLocationUrl);
        jc.execute();

        //event handler
        Button sendMessageButton = (Button)findViewById(R.id.closerButton);
        sendMessageButton.setOnClickListener(this);
    }

    /** Update GUI with a location
     *
     * @param l the location to display.
     */
    private void updateLocation(Location l){
        String addressDisplay = getResources().getString(R.string.unknownLocationError);
        String locationDisplay = getResources().getString(R.string.unknownLocationError);
        double latitude = 0;
        double longitude = 0;

        if(l != null) {
            mLatitude = l.getLatitude();
            mLongitude = l.getLongitude();
            latitude = l.getLatitude();
            longitude = l.getLongitude();
            Geocoder gc = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = gc.getFromLocation(latitude, longitude, 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    locationDisplay = address.getLocality() + " " + address.getPostalCode();
                    addressDisplay = address.getAddressLine(0);
                }
            }
            catch (IOException e) {
            }
        }

        TextView addressLabel = (TextView)findViewById(R.id.addressLabel);
        addressLabel.setText(addressDisplay);

        TextView positionLabel = (TextView)findViewById(R.id.positionLabel);
        positionLabel.setText(locationDisplay);

        TextView latitudeLabel = (TextView)findViewById(R.id.latitudeLabel);
        latitudeLabel.setText(String.valueOf(latitude));

        TextView longitudeLabel = (TextView)findViewById(R.id.longitudeLabel);
        longitudeLabel.setText(String.valueOf(longitude));
    }

    @Override
    public void onClick(View v) {
        EditText inputAddress = (EditText) findViewById(R.id.addressLabel);
        EditText inputLocation = (EditText) findViewById(R.id.positionLabel);
        EditText inputLatitude = (EditText) findViewById(R.id.latitudeLabel);
        EditText inputLongitude = (EditText) findViewById(R.id.longitudeLabel);
        CheckBox checkUpDisabled = (CheckBox) findViewById(R.id.upDisabled);

        Intent intentMaps = new Intent(this, MapsActivity.class);
        intentMaps.putExtra("address", inputAddress.getText().toString());
        intentMaps.putExtra("location", inputLocation.getText().toString());
        intentMaps.putExtra("latitude", inputLatitude.getText().toString());
        intentMaps.putExtra("longitude", inputLongitude.getText().toString());
        intentMaps.putExtra("currentLatitude", String.valueOf(mLatitude));
        intentMaps.putExtra("currentLongitude", String.valueOf(mLongitude));
        intentMaps.putExtra("fromActivity", Variables.ActivityCloser);
        intentMaps.putExtra("upDisabled", checkUpDisabled.isChecked());
        startActivity(intentMaps);
    }

    @Override
    public void OnCompleted(ArrayList<CategorieModel> categories) {
        //Spinner init and settings
        Spinner spinner = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        for(CategorieModel categorie : categories){
            adapter.add(categorie.getName());
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
