package visitmycityandroid.asyncTask;

import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;

import visitmycityandroid.interfaces.GoogleDirectionListener;
import visitmycityandroid.tools.GoogleMapsDirection;

public class GoogleDirectionTask extends AsyncTask<Void, Void, Void> {

    private final GoogleDirectionListener mListener;

    private final LatLng mStart;

    private final LatLng mFinish;

    private final String mWay;

    private PolylineOptions mDirections = new PolylineOptions().width(3);

    public GoogleDirectionTask(LatLng start, LatLng finish, String way, GoogleDirectionListener listener){
        this.mStart = start;
        this.mFinish = finish;
        mWay = way;
        mListener = listener;

        if(mWay.equals(GoogleMapsDirection.MODE_DRIVING)){
            mDirections.color(Color.RED);
        }
        else{
            mDirections.color(Color.BLUE);
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        GoogleMapsDirection direction = new GoogleMapsDirection();
        Document doc = direction.getDocument(mStart, mFinish, mWay);

        ArrayList<LatLng> directionPoint = direction.getDirection(doc);
        for (int i = 0; i < directionPoint.size(); i++) {
            mDirections.add(directionPoint.get(i));
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mListener.OnCompleted(mDirections);
    }
}
