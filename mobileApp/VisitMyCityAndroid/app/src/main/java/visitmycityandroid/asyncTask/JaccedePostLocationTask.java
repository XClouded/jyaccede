package visitmycityandroid.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.HashMap;

import visitmycityandroid.app.R;
import visitmycityandroid.configuration.Variables;
import visitmycityandroid.model.LocationModel;
import visitmycityandroid.tools.JsonTools;

public class JaccedePostLocationTask extends AsyncTask<LocationModel, Integer, Void> {

    private Context mContext;

    private String mMessage;

    public JaccedePostLocationTask(Context context){
        mContext = context;
    }

    @Override
    protected Void doInBackground(LocationModel... locations) {
        LocationModel m = locations[0];
        if(m == null){
            mMessage = String.valueOf(R.string.error);
            return null;
        }

        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Variables.postLocationUrl);

            HashMap<String, String> params = new HashMap<String, String>();
            params.put("editor_uid", "J'y accède");
            params.put("name", m.getName());
            params.put("category_id", Integer.toString(m.getIdCategorie()));
            params.put("description", m.getRemark());
            params.put("longitude", Double.toString(m.getLongitude()));
            params.put("latitude", Double.toString(m.getLatitude()));
            params.put("city_name", m.getAddress());
            params.put("accessibility", Integer.toString(m.getLvlAccess()));

            String json = JsonTools.getJsonObjectFromMap(params).toString();
            StringEntity  jsonEntity = new StringEntity(json);
            httpPost.setEntity(jsonEntity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse response = client.execute(httpPost);
            mMessage = String.valueOf(R.string.locationPost);
        }
        catch (ProtocolException e) {
            mMessage = String.valueOf(R.string.error);
        }
        catch (IOException e) {
            mMessage = String.valueOf(R.string.error);
        }
        catch(JSONException e){
            mMessage = String.valueOf(R.string.error);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(mContext, mMessage, Toast.LENGTH_SHORT).show();
    }
}
