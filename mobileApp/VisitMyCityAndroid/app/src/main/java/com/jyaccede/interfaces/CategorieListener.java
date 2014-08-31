package com.jyaccede.interfaces;

import java.util.ArrayList;

import com.jyaccede.model.CategoryModel;

public interface CategorieListener {
    public abstract void OnCompleted(final ArrayList<CategoryModel> categories);
}
