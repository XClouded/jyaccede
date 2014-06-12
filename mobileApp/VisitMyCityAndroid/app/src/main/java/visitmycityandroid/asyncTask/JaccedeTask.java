package visitmycityandroid.asyncTask;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import visitmycityandroid.configuration.Variables;
import visitmycityandroid.interfaces.JaccedeTaskListener;
import visitmycityandroid.model.LocationModel;
import visitmycityandroid.tools.JsonTools;

public class JaccedeTask extends AsyncTask<String, Void, Void> {

    private JaccedeTaskListener mListener;

    private ArrayList<LocationModel> mLocations = new ArrayList<LocationModel>();

    /** Construct
     *
     * @param jtl
     */
    public JaccedeTask(JaccedeTaskListener jtl){
        mListener = jtl;
    }

    @Override
    protected Void doInBackground(String... where) {
        String result = JsonTools.getStringFromJson(Variables.SearchLocationUrl + where[0]);

        try {
            JSONObject jsonObj = new JSONObject(result);
            JSONObject results = jsonObj.getJSONObject("results");
            JSONArray items = results.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject row = items.getJSONObject(i);
                try {
                    JSONObject categorie = row.getJSONObject("category");
                    int idCategorie = categorie.getInt("id");
                    mLocations.add(new LocationModel(row.getString("name"), row.getString("address"), row.getDouble("latitude"),
                                                     row.getDouble("longitude"), row.getString("description"), idCategorie));
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
