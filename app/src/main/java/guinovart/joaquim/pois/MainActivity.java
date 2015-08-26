package guinovart.joaquim.pois;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import guinovart.joaquim.pois.models.POI;


public class MainActivity extends ActionBarActivity{

    //url to get all POI's
    String url = "http://t21services.herokuapp.com/points";


    List<POI> PoiArray = new ArrayList<POI>();
    Context c = this;

    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the RecyclerView and set the layout manager
        rv = (RecyclerView)findViewById(R.id.poislist);
        mLayoutManager = new LinearLayoutManager(c);
        rv.setLayoutManager(mLayoutManager);

        //progress dialog to show while retrieving data from the server
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Retrieving Data");

        //execute the asynctask for retrieving data from the server
        new HttpAsyncTask().execute(url);

        //instanciate the adapter and set to to RecyclerView
        adapter = new ListPoiAdapter(PoiArray, c);
        rv.setAdapter(adapter);
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

        //opens map activity and passes POI array via intent extra
        if (id == R.id.map_activity){
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            intent.putParcelableArrayListExtra("POIarray", (ArrayList) PoiArray);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public class HttpAsyncTask extends AsyncTask<String, Void, List<POI>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // init progressdialog
            pd.show();
        }

        @Override
        protected List<POI> doInBackground(String... urls) {
            try {
                //gets the server url from the arguments, gets the Json string and send it to
                // Utilities for parsing. Returns POI's list
                String resultStr = Utilities.GET(urls[0]);
                List <POI> PoiList;
                //check good response from the server
                if(resultStr != "no connection"){
                    PoiList = Utilities.JSONtoPois(resultStr);
                }else{
                    //return empty list and show message
                    PoiList = new ArrayList<>();
                    Toast.makeText(c,"Unable to connect to server",Toast.LENGTH_LONG).show();
                }

                return PoiList;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        //reataching adapter, now with the data (POI's array)
        @Override
        protected void onPostExecute(List<POI> result){
            super.onPostExecute(result);
            PoiArray = result;
            adapter = new ListPoiAdapter(PoiArray, c);
            rv.setAdapter(adapter);
            pd.dismiss();
        }


    }
}
