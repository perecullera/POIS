package guinovart.joaquim.pois;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

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


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=this.getIntent();
        poiArray = intent.getParcelableArrayListExtra("POIarray");
        setContentView(R.layout.activity_map);
        mapFragment = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        LatLngBounds bounds;
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        map.setMyLocationEnabled(true);

        for (POI poi : poiArray) {
            LatLng latLng = new LatLng(poi.location.getLatitude(), poi.location.getLongitude());
            builder.include(latLng);
            map.addMarker(new MarkerOptions()
                    .title(Integer.toString(poi.id))
                    .snippet(poi.title)
                    .position(latLng));
        }
        bounds = builder.build();

        int padding = 150; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.animateCamera(cu);

        map.setOnInfoWindowClickListener(this);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(MapActivity.this,ResourceActivity.class);
        intent.putExtra("id", Integer.valueOf(marker.getTitle()));
        startActivity(intent);
    }
}
