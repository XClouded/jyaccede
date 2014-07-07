package visitmycityandroid.googleMaps;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import visitmycityandroid.app.R;

public class MultipleLineAdapter implements GoogleMap.InfoWindowAdapter {

    private final View markerView;

    public MultipleLineAdapter(Activity caller) {
        markerView = caller.getLayoutInflater().inflate(R.layout.marker_location, null);
    }

    public View getInfoWindow(Marker marker) {
        render(marker);
        return markerView;
    }

    public View getInfoContents(Marker marker) {
        String title = marker.getTitle().split("\n")[0];
        String remark = marker.getTitle().split("\n")[1];
        String access = marker.getTitle().split("\n")[2];

        TextView titleTextView = (TextView) markerView.findViewById(R.id.titleTextView);
        TextView remarkTextView = (TextView) markerView.findViewById(R.id.remarkTextView);
        TextView accessTextView = (TextView) markerView.findViewById(R.id.accessTextView);

        titleTextView.setText(title);
        remarkTextView.setText(remark);
        accessTextView.setText("Niveau d'accès " + access + "/10");

        return markerView;
    }

    private void render(Marker marker) {
        TextView titleTextView = (TextView) markerView.findViewById(R.id.titleTextView);
        TextView remarkTextView = (TextView) markerView.findViewById(R.id.remarkTextView);

        try {
            String title = marker.getTitle().split("\n")[0];
            String remark = marker.getTitle().split("\n")[1];

            titleTextView.setText(title);
            remarkTextView.setText(remark);
        }
        catch (Exception e){
            titleTextView.setText(marker.getTitle());
        }
    }
}