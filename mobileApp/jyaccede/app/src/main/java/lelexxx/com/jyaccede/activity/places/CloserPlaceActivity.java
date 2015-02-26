package lelexxx.com.jyaccede.activity.places;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import lelexxx.com.jyaccede.R;
import lelexxx.com.jyaccede.activity.JyaccedeActivity;
import lelexxx.com.jyaccede.activity.MapsActivity;
import lelexxx.com.jyaccede.asyncTask.JaccedeGetCategoryTask;
import lelexxx.com.jyaccede.configuration.Variables;
import lelexxx.com.jyaccede.database.DataAccessLayer;
import lelexxx.com.jyaccede.interfaces.CategorieListener;
import lelexxx.com.jyaccede.model.CategoryModel;

public class CloserPlaceActivity extends JyaccedeActivity implements View.OnClickListener {

    private double mLatitude;
    private double mLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closer_location);

        DataAccessLayer dal = getDal();
        List<CategoryModel> categories = dal.selectAllData(CategoryModel.class);

        //Spinner init and settings
        Spinner spinner = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        for(CategoryModel categorie : categories){
            adapter.add(categorie.getName());
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //PlaceModel init and settings
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

        //event handler
        View sendMessageButton = findViewById(R.id.closerButton);
        sendMessageButton.setOnClickListener(this);
        View clear_street = findViewById(R.id.clear_street);
        clear_street.setOnClickListener(this);
        View clear_city = findViewById(R.id.clear_city);
        clear_city.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        EditText inputStreet = (EditText) findViewById(R.id.inputStreet);
        EditText inputCity = (EditText) findViewById(R.id.inputCity);

        switch (v.getId()){
            case R.id.clear_street:
                inputStreet.setText("");
                inputStreet.requestFocus();
                break;
            case R.id.clear_city:
                inputCity.setText("");
                inputCity.requestFocus();
                break;
            case R.id.closerButton:
                updateLatLng();

                Intent intentMaps = new Intent(this, MapsActivity.class);
                intentMaps.putExtra("address", inputStreet.getText().toString());
                intentMaps.putExtra("location", inputCity.getText().toString());
                intentMaps.putExtra("latitude", String.valueOf(mLatitude));//inputLatitude.getText().toString());
                intentMaps.putExtra("longitude", String.valueOf(mLongitude));//inputLongitude.getText().toString());
                intentMaps.putExtra("fromActivity", Variables.ActivityCloser);
                startActivity(intentMaps);
                break;
        }
    }

    //region PRIVATE METHODS

    /** Update GUI with a location
     *
     * @param l the location to display.
     */
    private void updateLocation(Location l){
        String addressDisplay = getResources().getString(R.string.unknownLocationError);
        String locationDisplay = getResources().getString(R.string.unknownLocationError);

        if(l != null) {
            mLatitude = l.getLatitude();
            mLongitude = l.getLongitude();
            Geocoder gc = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = gc.getFromLocation(mLatitude, mLongitude, 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    locationDisplay = address.getLocality() + " " + address.getPostalCode();
                    addressDisplay = address.getAddressLine(0);
                }
            }
            catch (IOException e) {
                Toast.makeText(this, locationDisplay, Toast.LENGTH_LONG).show();
            }
        }

        TextView addressLabel = (TextView)findViewById(R.id.inputStreet);
        addressLabel.setText(addressDisplay);

        TextView positionLabel = (TextView)findViewById(R.id.inputCity);
        positionLabel.setText(locationDisplay);
    }

    /**
     * Update latLng according to the address entered by the user
     */
    private void updateLatLng(){
        EditText inputStreet = (EditText) findViewById(R.id.inputStreet);
        String street = inputStreet.getText().toString();

        EditText inputCity = (EditText) findViewById(R.id.inputCity);
        String city = inputCity.getText().toString();

        String address = street + " " + city;
        LatLng latLng = getLatLngFromAddress(address);

        mLatitude = latLng.latitude;
        mLongitude = latLng.longitude;
    }

    /** Retrieve Latitude and longitude from string adress.
     *
     * @param strAddress String address
     *
     * @return LatLng object
     */
    private LatLng getLatLngFromAddress(String strAddress) {
        try {
            Geocoder coder = new Geocoder(this);
            List<Address> address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);

            return new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (IOException e) {
            return null;
        }
    }

    //endregion
}
