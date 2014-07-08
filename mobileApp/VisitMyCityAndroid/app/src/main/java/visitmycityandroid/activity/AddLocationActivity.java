package visitmycityandroid.activity;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import visitmycityandroid.app.R;
import visitmycityandroid.asyncTask.JaccedeGetCategoryTask;
import visitmycityandroid.asyncTask.JaccedePostLocationTask;
import visitmycityandroid.configuration.Variables;
import visitmycityandroid.interfaces.CategorieListener;
import visitmycityandroid.model.CategoryModel;
import visitmycityandroid.model.LocationModel;

public class AddLocationActivity extends JyaccedeActivity implements CategorieListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

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
        }
        catch (Exception e){
        }

        //event handler
        Button sendMessageButton = (Button)findViewById(R.id.addLocationButton);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText inputName = (EditText) findViewById(R.id.inputAddress);
                EditText inputLatitude = (EditText) findViewById(R.id.inputLatitude);
                EditText inputLongitude = (EditText) findViewById(R.id.inputLongitude);
                EditText inputRemark = (EditText) findViewById(R.id.inputRemarque);
                LocationModel l = new LocationModel();
                l.setName(inputName.getText().toString());
                l.setLatitude(Double.parseDouble(inputLatitude.getText().toString()));
                l.setLongitude(Double.parseDouble(inputLongitude.getText().toString()));
                l.setRemark(inputRemark.getText().toString());

                JaccedePostLocationTask plt = new JaccedePostLocationTask(getApplicationContext());
                plt.execute(l);

                inputName.setText("");
                inputLatitude.setText("");
                inputLongitude.setText("");
                inputRemark.setText("");

                Toast.makeText(getApplicationContext(), getText(R.string.locationPost), Toast.LENGTH_LONG).show();
            }
        });

        JaccedeGetCategoryTask jc = new JaccedeGetCategoryTask(this, Variables.SearchCategorieUrl);
        jc.execute();
    }

    /** Update GUI with a location
     *
     * @param l the location to display.
     */
    private void updateLocation(Location l){
        String addressDisplay = getResources().getString(R.string.unknownLocationError);
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
                    addressDisplay = address.getAddressLine(0) +  " " + address.getLocality() + " " + address.getPostalCode();
                }
            }
            catch (IOException e) {
                Log.v("MainActivity-UpdateLocation", e.getMessage());
            }
        }

        TextView addressLabel = (TextView)findViewById(R.id.inputAddress);
        addressLabel.setText(addressDisplay);

        TextView latitudeLabel = (TextView)findViewById(R.id.inputLatitude);
        latitudeLabel.setText(String.valueOf(latitude));

        TextView longitudeLabel = (TextView)findViewById(R.id.inputLongitude);
        longitudeLabel.setText(String.valueOf(longitude));
    }

    @Override
    public void OnCompleted(ArrayList<CategoryModel> categories) {
        //Spinner init and settings
        Spinner spinner = (Spinner) findViewById(R.id.categorySpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        for(CategoryModel categorie : categories){
            adapter.add(categorie.getName());
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
