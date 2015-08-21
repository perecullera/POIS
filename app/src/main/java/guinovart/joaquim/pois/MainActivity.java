package guinovart.joaquim.pois;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import guinovart.joaquim.pois.models.POI;


public class MainActivity extends ActionBarActivity{

    String url = "http://t21services.herokuapp.com/points";
    List<POI> PoiArray = new ArrayList<POI>();
    List<String> PoiStrArray = new ArrayList<String>();
    String result;
    ListView listView;
    ListPoiAdapter adapter;
    Context c = this;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.poislist);
        final Button button = (Button) findViewById(R.id.mapButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                intent.putParcelableArrayListExtra("POIarray", (ArrayList) PoiArray);
                startActivity(intent);
            }
        });



        new HttpAsyncTask().execute(url);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                POI poi = (POI) listView.getItemAtPosition(position);
                System.out.print("poi............ "+ poi.id);
                Toast.makeText(getApplicationContext(), Integer.toString(poi.id), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ResourceActivity.class);
                intent.putExtra("id", poi.id);
                startActivity(intent);
            }
        });
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class HttpAsyncTask extends AsyncTask<String, Void, List<POI>> {

        @Override
        protected List<POI> doInBackground(String... urls) {
            try {
                String resultStr = Utilities.GET(urls[0]);
                List <POI> PoiList = Utilities.JSONtoPois(resultStr);
                return PoiList;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<POI> result){
            super.onPostExecute(result);
            PoiArray = result;
            adapter = new ListPoiAdapter(c, PoiArray);
            listView.setAdapter(adapter);


        }


    }
}
