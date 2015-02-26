package lelexxx.com.jyaccede.tools;

import android.util.Base64;

import org.apache.http.client.methods.HttpRequestBase;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class JaccedeAuthenticationTools {

    private static final String ACCESS_KEY_ID = "a41ddc10-bbf0-11e4-a8cb-fefdb24f8295";
    private static final String SECRET_ACCESS_KEY = "3d727cbf100e076ae91e8554180a8ad2cec07d84e49ecbff9c32e20f7bcdfcf8";
    private static final String HEADER_NAME_TIMESTAMP = "x-jaccedeapi-timestamp";
    private static final String HEADER_NAME_AUTH = "Authorization";
    private static final String HEADER_PART_JACCEDEAPI = "JACCEDEAPI";

    public static String computeStringToSign(HttpRequestBase request, long now) {

        URI uri = request.getURI();
        String method = request.getMethod().toUpperCase(java.util.Locale.getDefault());
        String path = uri.getPath();

        StringBuilder buff = new StringBuilder();
        buff.append(method);
        buff.append("\n");
        buff.append(HEADER_NAME_TIMESTAMP).append(":").append(now);
        buff.append("\n");
        buff.append(path);

        return buff.toString();
    }

    public static void setAuthHeaders(HttpRequestBase request) {

        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_ACCESS_KEY.getBytes("UTF-8"), mac.getAlgorithm());
            mac.init(keySpec);

            long now = System.currentTimeMillis();
            byte[] signed = mac.doFinal(computeStringToSign(request, now).getBytes());

            String signature = Base64.encodeToString(new String(Hex.encodeHex(signed)).getBytes("UTF-8"), Base64.NO_WRAP);

            StringBuilder buff = new StringBuilder(HEADER_PART_JACCEDEAPI);
            buff.append(" ").append(ACCESS_KEY_ID).append(":").append(signature);
            String auth = buff.toString();

            request.setHeader(HEADER_NAME_TIMESTAMP, String.valueOf(now));
            request.setHeader(HEADER_NAME_AUTH, auth);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (InvalidKeyException e) {
            e.printStackTrace();
        }

    }
}
