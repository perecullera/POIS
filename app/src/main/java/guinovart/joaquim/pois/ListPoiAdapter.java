package guinovart.joaquim.pois;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import guinovart.joaquim.pois.models.POI;

/**
 * Created by perecullera on 18/8/15.
 */
public class ListPoiAdapter extends ArrayAdapter<POI> {
    public ListPoiAdapter(Context context, List<POI> pois) {
        super(context, 0, pois);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        POI poi = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.poi_list, parent, false);
        }
        // Lookup view for data population
        TextView idView = (TextView) convertView.findViewById(R.id.id);
        TextView titleView = (TextView) convertView.findViewById(R.id.title);
        TextView locView = (TextView) convertView.findViewById(R.id.location);
        // Populate the data into the template view using the data object
        idView.setText(Integer.toString(poi.id));
        titleView.setText(poi.title);
        locView.setText(String.valueOf(poi.location.getLatitude()) + "-" + String.valueOf(poi.location.getLongitude()));
        // Return the completed view to render on screen
        return convertView;
    }
    public POI getItem(Integer position){
        return getItem(position);
    }
}