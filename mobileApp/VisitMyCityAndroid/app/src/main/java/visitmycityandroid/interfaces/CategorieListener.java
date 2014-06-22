package visitmycityandroid.interfaces;

import java.util.ArrayList;

import visitmycityandroid.model.CategorieModel;

public interface CategorieListener {
    public abstract void OnCompleted(final ArrayList<CategorieModel> categories);
}
