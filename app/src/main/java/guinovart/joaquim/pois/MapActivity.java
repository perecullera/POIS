package guinovart.joaquim.pois;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import guinovart.joaquim.pois.models.POI;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    MapFragment mapFragment;
    ArrayList<POI> poiArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getting
        Intent intent=this.getIntent();
        poiArray = intent.getParcelableArrayListExtra("POIarray");
        setContentView(R.layout.activity_map);

        //check if GooglePlayServices are installed
        int statusCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(MapActivity.this);

        //if properly installed, keep on
        if (statusCode == ConnectionResult.SUCCESS ) {
            mapFragment = ((MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map));
            mapFragment.getMapAsync(this);

        //show error dialog
        } else {
            int requestCode = 666;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(statusCode, this, requestCode);
            dialog.show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        //Bounds for centering the map on the existent POI's
        LatLngBounds bounds;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        map.setMyLocationEnabled(true);

        //Adding the POI's received into the map
        for (POI poi : poiArray) {
            LatLng latLng = new LatLng(poi.location.getLatitude(), poi.location.getLongitude());

            map.addMarker(new MarkerOptions()
                    .title(Integer.toString(poi.id))
                    .snippet(poi.title)
                    .position(latLng));

            //adding LatLng to builder to center Marker's on the map
            builder.include(latLng);
        }

        //calculate bounds
        bounds = builder.build();

        int padding = 150; // offset from edges of the map in pixels

        CameraUpdate cu;

        //if array have more than one POI, don't need zoom
        if (poiArray.size()>1) {
            cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            map.moveCamera(cu);
        //when alone POI's, fix the Map Camera with especified zoom
        } else {
            //map.setMyLocationEnabled(false);
            cu = CameraUpdateFactory.newLatLng(new LatLng(poiArray.get(0).location.getLatitude(),poiArray.get(0).location.getLongitude()));
            map.moveCamera(cu);
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);
            map.animateCamera(zoom);
        }

        //set the ClickListener to start ResourceActivity
        map.setOnInfoWindowClickListener(this);

    }
    //when info window clicked, start ResourceActivity for the POI
    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(MapActivity.this,ResourceActivity.class);
        intent.putExtra("id", Integer.valueOf(marker.getTitle()));
        startActivity(intent);
    }
}
