package lelexxx.com.jyaccede.activity;

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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import lelexxx.com.jyaccede.asyncTask.GoogleGetDirectionTask;
import lelexxx.com.jyaccede.asyncTask.JaccedeGetPlaceTask;
import lelexxx.com.jyaccede.configuration.Variables;
import lelexxx.com.jyaccede.googleMaps.MultipleLineAdapter;
import lelexxx.com.jyaccede.interfaces.GoogleDirectionListener;
import lelexxx.com.jyaccede.interfaces.JaccedeTaskListener;
import lelexxx.com.jyaccede.model.PlaceModel;
import lelexxx.com.jyaccede.tools.GoogleMapsDirection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import lelexxx.com.jyaccede.R;

public class MapsActivity extends JyaccedeActivity implements JaccedeTaskListener, GoogleDirectionListener, GoogleMap.OnMarkerClickListener {

    private List<PlaceModel> mDestination = new ArrayList<>();

    private LatLng mCurrentLL;

    private LatLng mToGo;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        Intent intent = getIntent();
        String location = intent.getStringExtra("location");
        String currentLatitude = intent.getStringExtra("currentLatitude");
        String currentLongitude = intent.getStringExtra("currentLongitude");
        String fromActivity = intent.getStringExtra("fromActivity");

        if(fromActivity != null && fromActivity.equals(Variables.ActivityCloser)){
            JaccedeGetPlaceTask js = new JaccedeGetPlaceTask(this, Variables.SearchLocationUrl, true);
            js.execute(location.split(" ")[0]);
            mMap.setInfoWindowAdapter(new MultipleLineAdapter(this));
        }

        mCurrentLL = new LatLng(Double.parseDouble(currentLatitude), Double.parseDouble(currentLongitude));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLL, 15));
        mMap.addMarker(initMarker(mCurrentLL, getString(R.string.here), "", 0));

        mMap.setOnMarkerClickListener(this);
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
                if(mToGo == null){
                    Toast.makeText(getApplicationContext(), getString(R.string.noLocationError), Toast.LENGTH_LONG).show();
                    return true;
                }

                try {
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    List<Address> addresses = geocoder.getFromLocation(mToGo.latitude, mToGo.longitude, 1);
                    String destinationString = addresses.get(0).getAddressLine(0);

                    String uri = String.format("google.navigation:q=%s", destinationString);
                    Intent intentGo = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intentGo);
                }
                catch (IOException e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.noCityError), Toast.LENGTH_LONG).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** Init a MarkerOptions
     *
     * @param ll Latitude and longitude
     * @param title
     * @param description
     * @param idCategory
     * @return
     */
    private MarkerOptions initMarker(LatLng ll, String title, String description, int idCategory){
        MarkerOptions mo = new MarkerOptions();
        StringBuilder content = new StringBuilder().append(title);
        if(!description.isEmpty()) {
            content.append("\n").append(description);
        }

        mo.title(content.toString());
        mo.position(ll);

        if(idCategory != 0){
            String imgName = "c" + idCategory;
            mo.icon(BitmapDescriptorFactory.fromResource(getResources().getIdentifier(imgName, "drawable", getPackageName())));
        }

        return mo;
    }

    @Override
    public void OnCompleted(final ArrayList<PlaceModel> locations){
        for(int i=0; i<locations.size(); i++){
            PlaceModel l = locations.get(i);
            LatLng ll = new LatLng(l.getLatitude(), l.getLongitude());
            mMap.addMarker(initMarker(ll, l.getName(), l.getRemark(), l.idCategorie));
            mDestination.add(l);
        }
    }

    @Override
    public void OnCompleted(final PolylineOptions directions){
        mMap.addPolyline(directions);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        mToGo = marker.getPosition();

        GoogleGetDirectionTask gdtd = new GoogleGetDirectionTask(mCurrentLL, mToGo, GoogleMapsDirection.MODE_DRIVING, this);
        gdtd.execute();

        GoogleGetDirectionTask gdtw = new GoogleGetDirectionTask(mCurrentLL, mToGo, GoogleMapsDirection.MODE_WALKING, this);
        gdtw.execute();

        return false;
    }
}
