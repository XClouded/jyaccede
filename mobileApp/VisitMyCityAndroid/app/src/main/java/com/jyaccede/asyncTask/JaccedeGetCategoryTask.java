package com.jyaccede.asyncTask;

import android.os.AsyncTask;

import com.jyaccede.interfaces.CategorieListener;
import com.jyaccede.tools.JsonTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.jyaccede.model.CategoryModel;

public class JaccedeGetCategoryTask extends AsyncTask<Void, Void, Void> {

    private CategorieListener mListener;

    private ArrayList<CategoryModel> mCategories = new ArrayList<CategoryModel>();

    private String mCurrentUrl;

    /** Construct
     *
     * @param ctl
     */
    public JaccedeGetCategoryTask(CategorieListener ctl, String url){
        mListener = ctl;
        mCurrentUrl = url;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String result = JsonTools.getStringFromJson(mCurrentUrl);

        try {
            JSONObject jsonObj = new JSONObject(result);
            JSONObject results = jsonObj.getJSONObject("results");
            JSONArray items = results.getJSONArray("results");
            for (int i = 0; i < items.length(); i++) {
                JSONObject row = items.getJSONObject(i);
                try {
                    mCategories.add(new CategoryModel(row.getString("id"), row.getString("name")));
                }
                catch (Exception e) {
                }
            }
        }
        catch (JSONException e) {
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mCategories.add(new CategoryModel("0", "Tous"));
        mListener.OnCompleted(mCategories);
    }
}
