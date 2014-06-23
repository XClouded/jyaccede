package visitmycityandroid.asyncTask;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import visitmycityandroid.interfaces.CategorieListener;
import visitmycityandroid.interfaces.JaccedeTaskListener;
import visitmycityandroid.model.CategorieModel;
import visitmycityandroid.model.LocationModel;
import visitmycityandroid.tools.JsonTools;

public class JaccedeCategorieTask extends AsyncTask<Void, Void, Void> {

    private CategorieListener mListener;

    private ArrayList<CategorieModel> mCategories = new ArrayList<CategorieModel>();

    private String mCurrentUrl;

    /** Construct
     *
     * @param ctl
     */
    public JaccedeCategorieTask(CategorieListener ctl, String url){
        mListener = ctl;
        mCurrentUrl = url;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String result = JsonTools.getStringFromJson(mCurrentUrl);

        try {
            JSONObject jsonObj = new JSONObject(result);
            JSONObject results = jsonObj.getJSONObject("results");
            JSONArray items = results.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject row = items.getJSONObject(i);
                try {
                    mCategories.add(new CategorieModel(row.getString("id"), row.getString("name")));
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
        mListener.OnCompleted(mCategories);
    }
}
