package lelexxx.com.jyaccede.interfaces;

import java.util.ArrayList;

import lelexxx.com.jyaccede.model.PlaceModel;

public interface JaccedeTaskListener {
    public abstract void OnCompleted(final ArrayList<PlaceModel> locations);
}