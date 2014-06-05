package visitmycityandroid.interfaces;

import java.util.ArrayList;

import visitmycityandroid.model.LocationModel;

public interface JaccedeTaskListener {
    public abstract void OnCompleted(final ArrayList<LocationModel> locations);
}