package visitmycityandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import visitmycityandroid.app.R;
import visitmycityandroid.asyncTask.CanalTpTask;
import visitmycityandroid.configuration.Variables;

public class UpdateLineActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_line);

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

            if(l != null){
                Geocoder gc = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> addresses = gc.getFromLocation(l.getLatitude(), l.getLongitude(), 1);
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);

                        String url = Variables.SeachNearbyPlaces.replace("{0}", address.getLocality());
                        url = url.replace("{1}", l.getLatitude()+"");
                        url = url.replace("{2}", l.getLongitude()+"");
                        CanalTpTask ct = new CanalTpTask(url);
                        ct.execute();
                    }
                }
                catch (IOException e) {
                    Log.v("MainActivity-UpdateLocation", e.getMessage());
                }
            }
        }
        catch (Exception e){
            Log.v("MainActivity-GetLocation", e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.update_line, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
