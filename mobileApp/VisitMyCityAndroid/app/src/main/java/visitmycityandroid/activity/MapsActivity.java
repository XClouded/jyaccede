package visitmycityandroid.activity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import visitmycityandroid.app.R;

public class MapsActivity extends VisitMyCityActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        String latitude = intent.getStringExtra("latitude");
        String longitude = intent.getStringExtra("longitude");

        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        if(address != null){

        }
        else if(latitude != null && longitude != null){

        }

        //Init marker and add it to map
        MarkerOptions mo = new MarkerOptions();
        mo.position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)));
        mo.title(address);
        map.addMarker(mo);
    }
}
