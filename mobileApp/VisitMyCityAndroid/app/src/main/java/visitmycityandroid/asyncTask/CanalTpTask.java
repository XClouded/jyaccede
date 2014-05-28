package visitmycityandroid.asyncTask;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CanalTpTask extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... voids) {
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet get = new HttpGet("http://dev.jaccede.com/api/v2/places/search/");
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

        return result;
    }
}
