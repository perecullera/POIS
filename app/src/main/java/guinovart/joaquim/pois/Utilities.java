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

    // from the string with the server url in the argument, it return the JSON string
    public static String GET(String url) throws IOException {
        String result;
        URL obj = null;
        try {
            obj = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //just using GET method
        con.setRequestMethod("GET");


        int responseCode = con.getResponseCode();

        //DEBUG
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        //if server response is ok, get the buffer and put into a string
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            result = response.toString();
        //no OK server response, send message "no connection" to activity
        } else {
            result = "no connection";
        }
        //print result
        System.out.println(result);
        return result;
    }
    //parse the JSON String from argument and returns into POI objects
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
                    //parse JSON nodes into POI objects
                    POI poi = JSONtoPoi(c);
                    //add POIs to array
                    PoiList.add(poi);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return PoiList;
        }
        return PoiList;
    }

    //it parses the argument passed JSONObject into a POI object and returns it
    public static POI JSONtoPoi(JSONObject jsonObj) throws JSONException {
        POI poi = new POI();
        poi.id = jsonObj.getInt("id");
        poi.title = jsonObj.getString("title");
        String[]coordinates=jsonObj.getString("geocoordinates").split(",");
        poi.location = new Location("dummyprovider");
        poi.location.setLatitude(Double.valueOf(coordinates[0]));
        poi.location.setLongitude(Double.valueOf(coordinates[1]));

        //if it doesn't have address it is only from the POI's list
        if(jsonObj.has("address")){
            String addStr = jsonObj.getString("address");

            //split adress into street number city
            String[] addArr = addStr.split(",");
            Address address = new Address();

            //some nodes doesn't have number, set it to null
            if (addArr.length < 3) {
                address.street = addArr[0];
                address.number = null;
                address.city = addArr[1];
            } else {
                address.street = addArr[0];
                address.number = addArr[1];
                address.city = addArr[2];
            }
            poi.address = address;
            poi.phone = jsonObj.getString("phone");
            poi.email = jsonObj.getString("email");
            poi.transport = jsonObj.getString("transport");
            poi.description = jsonObj.getString("description");
        }

        return poi;
    }
}