package com.jyaccede.asyncTask;

import android.os.AsyncTask;

import com.jyaccede.interfaces.JaccedeTaskListener;
import com.jyaccede.model.PlaceModel;
import com.jyaccede.tools.JsonTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import com.jyaccede.model.CategoryModel;

public class JaccedeGetPlaceTask extends AsyncTask<String, Void, Void> {

    private JaccedeTaskListener mListener;

    private ArrayList<PlaceModel> mLocations = new ArrayList<PlaceModel>();

    private String mCurrentUrl;

    private boolean mUpDisabled = true;

    private CategoryModel mCategorieFilter = null;

    /** Construct
     *
     * @param jtl
     */
    public JaccedeGetPlaceTask(JaccedeTaskListener jtl, String url, boolean upDisabled){
        mListener = jtl;
        mCurrentUrl = url;
        mUpDisabled = upDisabled;
    }

    @Override
    protected Void doInBackground(String... where) {
        String result = JsonTools.getStringFromJson(mCurrentUrl + where[0]);

        try {
            JSONObject jsonObj = new JSONObject(result);
            JSONObject results = jsonObj.getJSONObject("results");
            JSONArray items = results.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject row = items.getJSONObject(i);
                try {
                    JSONObject categorie = row.getJSONObject("category");
                    int idCategorie = categorie.getInt("id");

                    if(mCategorieFilter == null || mUpDisabled && idCategorie == 34) {
                        mLocations.add(new PlaceModel(row.getString("name"), row.getString("address"), row.getDouble("latitude"),
                                row.getDouble("longitude"), row.getString("description"), idCategorie, row.getInt("accessibility_points")));
                    }
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
        mListener.OnCompleted(mLocations);
    }
}
