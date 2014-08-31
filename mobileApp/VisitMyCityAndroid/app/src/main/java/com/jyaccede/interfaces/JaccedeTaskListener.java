package com.jyaccede.interfaces;

import java.util.ArrayList;

import com.jyaccede.model.PlaceModel;

public interface JaccedeTaskListener {
    public abstract void OnCompleted(final ArrayList<PlaceModel> locations);
}