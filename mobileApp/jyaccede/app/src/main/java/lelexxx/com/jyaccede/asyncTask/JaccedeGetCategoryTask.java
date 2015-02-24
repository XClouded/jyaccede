package lelexxx.com.jyaccede.asyncTask;

import android.os.AsyncTask;

import lelexxx.com.jyaccede.interfaces.CategorieListener;
import lelexxx.com.jyaccede.tools.JsonTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lelexxx.com.jyaccede.model.CategoryModel;

public class JaccedeGetCategoryTask extends AsyncTask<Void, Void, Void> {

    private CategorieListener mListener;

    private ArrayList<CategoryModel> mCategories = new ArrayList<>();

    private String mCurrentUrl;

    /** Construct
     *
     * @param ctl calback object
     * @param url URL to call to get categories json
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
                mCategories.add(new CategoryModel(row.getString("id"), row.getString("name")));
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
