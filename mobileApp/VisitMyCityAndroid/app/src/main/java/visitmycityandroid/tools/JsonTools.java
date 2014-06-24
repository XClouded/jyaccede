package visitmycityandroid.tools;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonTools {

    public static String getStringFromJson(String url){
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);
            JaccedeAuthenticationTools.setAuthHeaders(get);
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
