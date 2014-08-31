package com.jyaccede.activity;

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.jyaccede.configuration.Variables;
import com.jyaccede.googleMaps.MultipleLineAdapter;
import com.jyaccede.interfaces.JaccedeTaskListener;
import com.jyaccede.model.PlaceModel;
import com.jyaccede.tools.GoogleMapsDirection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jyaccede.app.R;
import com.jyaccede.asyncTask.GoogleGetDirectionTask;
import com.jyaccede.asyncTask.JaccedeGetPlaceTask;
import com.jyaccede.interfaces.GoogleDirectionListener;

public class MapsActivity extends JyaccedeActivity implements JaccedeTaskListener, GoogleDirectionListener, GoogleMap.OnMarkerClickListener {

    private List<PlaceModel> mDestination = new ArrayList<PlaceModel>();

    private LatLng mCurrentLL;

    private LatLng mToGo;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

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
                    Toast.makeText(getApplicationContext(), getString(R.string.noLocationError), Toast.LENGTH_LONG);
                    return true;
                }
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                String destinationString = null;
                try {
                    List<Address> addresses = geocoder.getFromLocation(mToGo.latitude, mToGo.longitude, 1);
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
    private MarkerOptions initMarker(LatLng ll, String title, String description, int idCategorie){
        MarkerOptions mo = new MarkerOptions();
        StringBuilder content = new StringBuilder().append(title);
        content.append("\n").append(description);

        mo.title(content.toString());
        mo.position(ll);

        if(idCategorie != 0){
            String imgName = "c" + idCategorie;
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
