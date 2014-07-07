package visitmycityandroid.interfaces;

import com.google.android.gms.maps.model.PolylineOptions;

public interface GoogleDirectionListener {
    public void OnCompleted(final PolylineOptions directions);
}
