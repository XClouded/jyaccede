package lelexxx.com.jyaccede.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import lelexxx.com.jyaccede.interfaces.JaccedeTaskListener;
import lelexxx.com.jyaccede.model.PlaceModel;
import lelexxx.com.jyaccede.tools.JsonTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import lelexxx.com.jyaccede.model.CategoryModel;

public class JaccedeGetPlaceTask extends AsyncTask<String, Void, Void> {

    private JaccedeTaskListener mListener;

    private ArrayList<PlaceModel> mLocations = new ArrayList<>();

    private String mCurrentUrl;

    private boolean mUpDisabled = true;

    private CategoryModel mCategorieFilter = null;

    private static final String TAG = "JaccedeGetPlaceTask";

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
        try {
            String result = JsonTools.getStringFromJson(mCurrentUrl + URLEncoder.encode(where[0], "utf-8"));
            JSONObject jsonObj = new JSONObject(result);
            JSONArray items = jsonObj.getJSONArray("results");
            for (int i = 0; i < items.length(); i++) {
                JSONObject row = items.getJSONObject(i);
                JSONObject categorie = row.getJSONObject("category");
                int idCategorie = categorie.getInt("id");

                if(mCategorieFilter == null || mUpDisabled && idCategorie == 34) {
                    JSONObject address = row.getJSONObject("address");
                    String latitudesStr = address.getString("latitude");
                    double latitude = 0,longitude = 0;
                    if(latitudesStr != "null")
                        latitude = Double.valueOf(latitudesStr);
                    String longitudeStr = address.getString("longitude");
                    if(longitudeStr != "null")
                        longitude = Double.valueOf(longitudeStr);

                    mLocations.add(new PlaceModel(row.getString("name"), row.getString("full_address"), latitude,
                            longitude, row.getString("description"), idCategorie, row.getInt("accessibility_rating")));

                }
            }
        }
        catch (UnsupportedEncodingException e) {
        }
        catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mListener.OnCompleted(mLocations);
    }
}
