package guinovart.joaquim.pois;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import guinovart.joaquim.pois.models.POI;


public class ResourceActivity extends ActionBarActivity {

    String url = "http://t21services.herokuapp.com/points";
    TextView titleVW;
    TextView streetVW;
    TextView numberVW;
    TextView cityVW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
        Intent intent = getIntent();
        int message = intent.getIntExtra("id", 0);
        titleVW = (TextView) findViewById(R.id.title);
        streetVW = (TextView) findViewById(R.id.street);
        numberVW = (TextView) findViewById(R.id.number);
        cityVW = (TextView) findViewById(R.id.city);
        titleVW.setText(Integer.toString(message));
        url = url+"/"+message;
        new ResourceAsyncTask().execute(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_resource, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class ResourceAsyncTask extends AsyncTask<String, Void, POI> {

        @Override
        protected POI doInBackground(String... urls) {
            try {
                String resultStr = Utilities.GET(urls[0]);
                JSONObject jsonObj = new JSONObject(resultStr);
                POI poi = Utilities.JSONtoPoi(jsonObj);
                return poi;
            } catch (IOException | JSONException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(POI result){
            super.onPostExecute(result);
            titleVW.setText(result.title);
            streetVW.setText(result.address.street);
            numberVW.setText(result.address.number);
            cityVW.setText(result.address.city);
        }


    }
}
