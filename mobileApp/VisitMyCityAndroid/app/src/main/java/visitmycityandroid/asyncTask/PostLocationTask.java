package visitmycityandroid.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.ProtocolException;

import visitmycityandroid.app.R;
import visitmycityandroid.configuration.Variables;
import visitmycityandroid.model.LocationModel;

public class PostLocationTask extends AsyncTask<LocationModel, Integer, Void> {

    private Context mContext;

    public PostLocationTask (Context context){
        mContext = context;
    }

    @Override
    protected Void doInBackground(LocationModel... locations) {
        LocationModel m = locations[0];
        if(m == null){
            return null;
        }

        try {
            //convert object to json
            //ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = "";//ow.writeValueAsString(m);

            //Post WebService content
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Variables.postLocationUrl);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            httpPost.setEntity(new ByteArrayEntity(json.getBytes("UTF8")));
            HttpResponse response = client.execute(httpPost);
        }
        catch (ProtocolException e) {
            Log.v("PostMessageTask", e.getMessage());
        }
        catch (IOException e) {
            Log.v("PostMessageTask", e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(mContext, R.string.locationPost, Toast.LENGTH_SHORT).show();
    }
}
