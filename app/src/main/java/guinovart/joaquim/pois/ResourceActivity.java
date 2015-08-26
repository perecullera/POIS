package guinovart.joaquim.pois;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import guinovart.joaquim.pois.models.POI;


public class ResourceActivity extends ActionBarActivity {

    //base url to server
    String url = "http://t21services.herokuapp.com/points";

    //views
    TextView titleVW;
    TextView streetVW;
    TextView numberVW;
    TextView cityVW;
    TextView emailTW;
    TextView phoneTW;
    TextView transportTW;
    TextView descriptionTW;

    List<POI> PoiArray;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

        //get POI id from the intent and atache it to url
        Intent intent = getIntent();
        int message = intent.getIntExtra("id", 0);
        url = url+"/"+message;

        //layout
        titleVW = (TextView) findViewById(R.id.title);
        streetVW = (TextView) findViewById(R.id.street);
        numberVW = (TextView) findViewById(R.id.number);
        cityVW = (TextView) findViewById(R.id.city);
        emailTW = (TextView) findViewById(R.id.email);
        phoneTW = (TextView) findViewById(R.id.phone);
        transportTW = (TextView) findViewById(R.id.transport);
        descriptionTW = (TextView) findViewById(R.id.description);

        //init POI array
        PoiArray = new ArrayList<>();

        //init progress dialog
        pd = new ProgressDialog(this);
        pd.setMessage("Retrieving data");

        //execute asynctask to get the data and atache it to layout
        new ResourceAsyncTask().execute(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //opens map activity passing the POI array (with just one POI)
        if (id == R.id.map_activity){
            Intent intent = new Intent(ResourceActivity.this, MapActivity.class);
            intent.putParcelableArrayListExtra("POIarray", (ArrayList) PoiArray);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    public class ResourceAsyncTask extends AsyncTask<String, Void, POI> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // init progressdialog
            pd.show();
        }

        @Override
        protected POI doInBackground(String... urls) {

            POI poi = new POI();

            try {
                //get the JSON string
                String resultStr = Utilities.GET(urls[0]);

                //check good response from server
                if (resultStr != "no connection") {
                    //parse JSON string
                    JSONObject jsonObj = new JSONObject(resultStr);
                    poi = Utilities.JSONtoPoi(jsonObj);
                    //return the POI
                    return poi;
                } else {
                    //not good response, return empty POI and message TOAST
                    poi.title = "Unalble to get to server";
                    poi.address.street= "";
                    poi.address.city = "";
                    poi.address.number="";
                    return poi;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(POI result){
            super.onPostExecute(result);
            //add the POI to the POI array
            PoiArray.add(result);
            //set POI to view
            titleVW.setText(result.title);
            streetVW.setText(result.address.street);
            numberVW.setText(result.address.number);
            cityVW.setText(result.address.city);
            //if no address, phone, transport for the POI, set the view to HIDE
            if (result.email.equals("null")||result.email.equals("undefined")){
                emailTW.setVisibility(View.GONE);
            }else{
                emailTW.setText(result.email);
            }
            if (result.phone.equals("null")||result.phone.equals("undefined")){
                phoneTW.setVisibility(View.GONE);
            }else{
                phoneTW.setText(result.phone);
            }
            if (result.transport.equals("null")||result.transport.equals("undefined")){
                transportTW.setVisibility(View.GONE);
            }else{
                transportTW.setText(result.transport);
            }
            descriptionTW.setText(result.description);

            pd.dismiss();
        }



    }
}
