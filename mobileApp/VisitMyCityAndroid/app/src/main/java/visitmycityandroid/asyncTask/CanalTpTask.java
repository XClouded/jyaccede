package visitmycityandroid.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import visitmycityandroid.interfaces.CanalTpTaskListener;
import visitmycityandroid.model.LineModel;
import visitmycityandroid.model.LocationModel;
import visitmycityandroid.tools.JsonTools;

public class CanalTpTask extends AsyncTask<Void, Void, Void> {

    private String mUrl;

    private CanalTpTaskListener mListener;

    private ArrayList<LineModel> mLines = new ArrayList<LineModel>();

    /** Construct
     *
     * @param url
     * @param listener
     */
    public CanalTpTask(String url,CanalTpTaskListener listener){
        mUrl = url;
        mListener = listener;
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
                    mLines.add(new LineModel(row.getString("name")));
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
        mListener.OnCompleted(mLines);
    }
}
