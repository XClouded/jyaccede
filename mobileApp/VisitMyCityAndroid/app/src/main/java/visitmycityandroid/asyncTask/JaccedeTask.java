package visitmycityandroid.asyncTask;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import visitmycityandroid.interfaces.JaccedeTaskListener;
import visitmycityandroid.model.CategorieModel;
import visitmycityandroid.model.LocationModel;
import visitmycityandroid.tools.JsonTools;

public class JaccedeTask extends AsyncTask<String, Void, Void> {

    private JaccedeTaskListener mListener;

    private ArrayList<LocationModel> mLocations = new ArrayList<LocationModel>();

    private String mCurrentUrl;

    private boolean mUpDisabled = true;

    private CategorieModel mCategorieFilter = null;

    /** Construct
     *
     * @param jtl
     */
    public JaccedeTask(JaccedeTaskListener jtl, String url, boolean upDisabled){
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
                        mLocations.add(new LocationModel(row.getString("name"), row.getString("address"), row.getDouble("latitude"),
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
