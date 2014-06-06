package visitmycityandroid.asyncTask;

import android.os.AsyncTask;
import android.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import visitmycityandroid.configuration.Variables;
import visitmycityandroid.interfaces.JaccedeTaskListener;
import visitmycityandroid.model.LocationModel;
import visitmycityandroid.tools.Hex;

public class JaccedeTask extends AsyncTask<String, Void, Void> {

    private static final String ACCESS_KEY_ID = "test-jispapi-access-key-id";
    private static final String SECRET_ACCESS_KEY = "test-jispapi-secret-access-key";
    private static final String HEADER_NAME_TIMESTAMP = "x-jispapi-timestamp";
    private static final String HEADER_NAME_AUTH = "Authorization";
    private static final String HEADER_PART_JISPAPI = "JISPAPI";

    private JaccedeTaskListener mListener;

    private ArrayList<LocationModel> mLocations = new ArrayList<LocationModel>();

    public JaccedeTask(JaccedeTaskListener jtl){
        mListener = jtl;
    }

    private static String computeStringToSign(HttpRequestBase request, long now) {

        URI uri = request.getURI();
        String method = request.getMethod().toUpperCase(java.util.Locale.getDefault());
        String path = uri.getPath();

        StringBuilder buff = new StringBuilder();
        buff.append(method);
        buff.append("\n");
        buff.append("x-jispapi-timestamp:").append(now);
        buff.append("\n");
        buff.append(path);

        return buff.toString();
    }

    private static void setAuthHeaders(HttpRequestBase request) {
        try {

            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_ACCESS_KEY.getBytes("UTF-8"), mac.getAlgorithm());
            mac.init(keySpec);

            long now = System.currentTimeMillis();
            byte[] signed = mac.doFinal(computeStringToSign(request, now).getBytes());

            String signature = Base64.encodeToString(new String(Hex.encodeHex(signed)).getBytes("UTF-8"), Base64.NO_WRAP);

            StringBuilder buff = new StringBuilder(HEADER_PART_JISPAPI);
            buff.append(" ").append(ACCESS_KEY_ID).append(":").append(signature);
            String auth = buff.toString();

            request.setHeader(HEADER_NAME_TIMESTAMP, String.valueOf(now));
            request.setHeader(HEADER_NAME_AUTH, auth);

        } catch (Exception e) {
        }
    }

    @Override
    protected Void doInBackground(String... where) {
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            //HttpGet get = new HttpGet(Variables.SearchLocationUrl + where[0]);
            HttpGet get = new HttpGet(Variables.SearchCategorieUrl);
            setAuthHeaders(get);
            HttpResponse httpResponse = httpclient.execute(get);
            InputStream inputStream = httpResponse.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line;
            while((line = bufferedReader.readLine()) != null){
                result += line;
            }

            inputStream.close();
        }
        catch (Exception e) {
            result = "ERROR";
        }

        try {
            JSONObject jsonObj = new JSONObject(result);
            JSONObject results = jsonObj.getJSONObject("results");
            JSONArray items = results.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject row = items.getJSONObject(i);
                try {
                    JSONObject categorie = row.getJSONObject("category");
                    int idCategorie = categorie.getInt("id");
                    mLocations.add(new LocationModel(row.getString("name"), row.getString("address"), row.getDouble("longitude"),
                                                     row.getDouble("latitude"), row.getString("description"), idCategorie));
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
