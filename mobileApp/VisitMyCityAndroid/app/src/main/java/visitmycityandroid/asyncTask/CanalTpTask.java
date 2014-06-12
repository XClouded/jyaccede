package visitmycityandroid.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import visitmycityandroid.model.LineModel;
import visitmycityandroid.model.LocationModel;
import visitmycityandroid.tools.JsonTools;

public class CanalTpTask extends AsyncTask<Void, Void, Void> {

    private String mUrl;

    /** Construct
     *
     * @param url
     */
    public CanalTpTask(String url){
        mUrl = url;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String result = JsonTools.getStringFromJson(mUrl);

        try {
            JSONObject jsonObj = new JSONObject(result);
            JSONArray items = jsonObj.getJSONArray("places_nearby");
            for (int i = 0; i < items.length(); i++) {
                JSONObject row = items.getJSONObject(i);
                try {
                    LineModel line = new LineModel(row.getString("name"));
                }
                catch (Exception e) {
                }
            }
        }
        catch (JSONException e) {
        }

        return null;
    }
}
