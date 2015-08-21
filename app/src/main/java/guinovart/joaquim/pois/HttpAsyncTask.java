package guinovart.joaquim.pois;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by perecullera on 18/8/15.
 */
public class HttpAsyncTask extends AsyncTask <String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        try {
            return GET(urls[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return "nul";
        }
    }

    @Override
    protected void onPostExecute(String result){

    }
    public static String GET(String url) throws IOException {
        String result;
        URL obj = null;
        try {
            obj = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");


        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        result = response.toString();
        System.out.println(result);
        return result;
    }

    private static void convertInputStreamToString(InputStream inputStream) {
    }


}
