package visitmycityandroid.service;

import android.content.Context;
import android.location.Address;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class JaccedeService extends AsyncTask<Void, Void, String> {

    private static final String ACCESS_KEY_ID = "test-jispapi-access-key-id";
    private static final String SECRET_ACCESS_KEY = "test-jispapi-secret-access-key";
    private static final String HEADER_NAME_TIMESTAMP = "x-jispapi-timestamp";
    private static final String HEADER_NAME_AUTH = "Authorization";
    private static final String HEADER_PART_JISPAPI = "JISPAPI";

    private static final String computeStringToSign(HttpRequestBase request, long now) {

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

    public static final void setAuthHeaders(HttpRequestBase request) {
        try {

            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_ACCESS_KEY.getBytes("UTF-8"), mac.getAlgorithm());
            mac.init(keySpec);

            long now = System.currentTimeMillis();
            byte[] signed = mac.doFinal(computeStringToSign(request, now).getBytes());

            String signature = "";//Base64.encodeToString(Hex.encodeHex(signed).getBytes("UTF-8"), Base64.NO_WRAP);

            StringBuilder buff = new StringBuilder(HEADER_PART_JISPAPI);
            buff.append(" ").append(ACCESS_KEY_ID).append(":").append(signature);
            String auth = buff.toString();

            request.setHeader(HEADER_NAME_TIMESTAMP, String.valueOf(now));
            request.setHeader(HEADER_NAME_AUTH, auth);

        } catch (Exception e) {
            Log.e("SetAuthHeaders", "computeAuthHeader(error computing auth header: " + e.getMessage() + ")");
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse httpResponse = httpclient.execute(new HttpGet("http://dev.jaccede.com/api/v2/places/search/"));
            InputStream inputStream = httpResponse.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line;
            while((line = bufferedReader.readLine()) != null){
                result += line;
            }

            inputStream.close();
        }
        catch (Exception e) {
            Log.v("JaccedeService", e.getMessage());
            result = "ERROR";
        }

        return result;
    }
}
