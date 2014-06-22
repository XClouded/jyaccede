package visitmycityandroid.interfaces;

import java.util.ArrayList;

import visitmycityandroid.model.LineModel;

public interface CanalTpTaskListener {
    public abstract void OnCompleted(final ArrayList<LineModel> lines);
}