package visitmycityandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import visitmycityandroid.app.R;
import visitmycityandroid.configuration.Variables;
import visitmycityandroid.service.JaccedeService;

public class MapsActivity extends VisitMyCityActivity {

    private List<LatLng> mdestination = new ArrayList<LatLng>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        String latitude = intent.getStringExtra("latitude");
        String longitude = intent.getStringExtra("longitude");
        String currentLatitude = intent.getStringExtra("currentLatitude");
        String currentLongitude = intent.getStringExtra("currentLongitude");
        String fromActivity = intent.getStringExtra("fromActivity");

        if(fromActivity != null && fromActivity.equals(Variables.ActivityCloser)){
            JaccedeService js = new JaccedeService();
            js.execute();
        }
        else if(fromActivity != null && fromActivity.equals(Variables.ActivitySearch)){
            LatLng ll = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 15));
            map.addMarker(initMarker(ll, address));

            mdestination.add(ll);
        }

        LatLng currentll = new LatLng(Double.parseDouble(currentLatitude), Double.parseDouble(currentLongitude));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentll, 15));
        map.addMarker(initMarker(currentll, getString(R.string.here)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.maps, menu);
        return true;
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
}
