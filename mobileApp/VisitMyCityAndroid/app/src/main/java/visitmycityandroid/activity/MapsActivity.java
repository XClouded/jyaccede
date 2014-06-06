package visitmycityandroid.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import visitmycityandroid.app.R;
import visitmycityandroid.configuration.Variables;
import visitmycityandroid.asyncTask.JaccedeTask;
import visitmycityandroid.interfaces.JaccedeTaskListener;
import visitmycityandroid.model.LocationModel;

public class MapsActivity extends VisitMyCityActivity implements JaccedeTaskListener {

    private List<LocationModel> mDestination = new ArrayList<LocationModel>();

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        String location = intent.getStringExtra("location");
        String latitude = intent.getStringExtra("latitude");
        String longitude = intent.getStringExtra("longitude");
        String currentLatitude = intent.getStringExtra("currentLatitude");
        String currentLongitude = intent.getStringExtra("currentLongitude");
        String fromActivity = intent.getStringExtra("fromActivity");

        if(fromActivity != null && fromActivity.equals(Variables.ActivityCloser)){
            JaccedeTask js = new JaccedeTask(this);
            js.execute(location.split(" ")[0]);
        }
        else if(fromActivity != null && fromActivity.equals(Variables.ActivitySearch)){
            LatLng ll = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 15));
            mMap.addMarker(initMarker(ll, address));

            mDestination.add(new LocationModel("", address, Double.parseDouble(longitude), Double.parseDouble(latitude), "", 0));
        }

        LatLng currentll = new LatLng(Double.parseDouble(currentLatitude), Double.parseDouble(currentLongitude));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentll, 15));
        mMap.addMarker(initMarker(currentll, getString(R.string.here)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_go :
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                String destinationString = null;
                try {
                    List<Address> addresses = geocoder.getFromLocation(mDestination.get(0).latitude, mDestination.get(0).longitude, 1);
                    destinationString = addresses.get(0).getAddressLine(0);
                }
                catch (IOException e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.noCityError), Toast.LENGTH_LONG);
                }

                String uri = String.format("google.navigation:q=%s", destinationString);
                Intent intentGo = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intentGo);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** Init a MarkerOptions
     *
     * @param ll
     * @param title
     * @return
     */
    private MarkerOptions initMarker(LatLng ll, String title){
        MarkerOptions mo = new MarkerOptions();
        mo.title(title);
        mo.position(ll);

        return mo;
    }

    @Override
    public void OnCompleted(final ArrayList<LocationModel> locations){
        for(int i=0; i<locations.size(); i++){
            LocationModel l = locations.get(i);
            LatLng ll = new LatLng(l.getLatitude(), l.getLongitude());
            mMap.addMarker(initMarker(ll, l.getName()));
            mDestination.add(l);
        }
    }
}
