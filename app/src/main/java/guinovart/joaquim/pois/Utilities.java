package guinovart.joaquim.pois;

import android.location.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import guinovart.joaquim.pois.models.Address;
import guinovart.joaquim.pois.models.POI;

/**
 * Created by perecullera on 18/8/15.
 */
public class Utilities {

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

    public static List<POI> JSONtoPois(String JsonStr) {
        List<POI> PoiList = new ArrayList<>();
        if (JsonStr != null) {
            try {

                JSONObject jsonObj = new JSONObject(JsonStr);

                // Getting JSON Array node
                JSONArray pois = jsonObj.getJSONArray("list");

                // looping through All Contacts
                for (int i = 0; i < pois.length(); i++) {
                    JSONObject c = pois.getJSONObject(i);
                    POI poi = JSONtoPoi(c);
                    PoiList.add(poi);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return PoiList;
        }
        return PoiList;
    }
    public static POI JSONtoPoi(JSONObject jsonObj) throws JSONException {
        POI poi = new POI();
        poi.id = jsonObj.getInt("id");
        poi.title = jsonObj.getString("title");
        String[]coordinates=jsonObj.getString("geocoordinates").split(",");
        poi.location = new Location("dummyprovider");
        poi.location.setLatitude(Double.valueOf(coordinates[0]));
        poi.location.setLongitude(Double.valueOf(coordinates[1]));
        if(jsonObj.has("address")){
            String addStr = jsonObj.getString("address");
            String[] addArr = addStr.split(",");
            Address address = new Address();
            address.street = addArr[0];
            address.number = addArr[1];
            address.city = addArr[2];
            poi.address = address;
        }
        return poi;
    }
}