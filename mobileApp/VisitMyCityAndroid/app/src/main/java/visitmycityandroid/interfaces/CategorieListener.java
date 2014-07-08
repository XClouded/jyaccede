package visitmycityandroid.interfaces;

import java.util.ArrayList;

import visitmycityandroid.model.CategoryModel;

public interface CategorieListener {
    public abstract void OnCompleted(final ArrayList<CategoryModel> categories);
}
