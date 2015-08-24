package guinovart.joaquim.pois;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import guinovart.joaquim.pois.models.POI;

/**
 * Created by perecullera on 18/8/15.
 */
public class ListPoiAdapter extends RecyclerView.Adapter<ListPoiAdapter.ViewHolder> {

    List<POI> pois;
    Context c;


    public ListPoiAdapter(List<POI> pois, Context c){
        this.pois = pois;
        this.c = c;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView id;
        public TextView title;
        public TextView location;
        OnItemClickListener mItemClickListener;

        public ViewHolder(View v, OnItemClickListener onItemClickListener) {
            super(v);
            mItemClickListener = onItemClickListener;
            id = (TextView)v.findViewById(R.id.id);
            title = (TextView)v.findViewById(R.id.title);
            location = (TextView)v.findViewById(R.id.location);
            id.setOnClickListener(this);
            title.setOnClickListener(this);
            location.setOnClickListener(this);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

                mItemClickListener.onItemClick(v, getPosition());

        }
        public interface OnItemClickListener {
            public void onItemClick(View view , int position);
        }

    }

    public POI getItem(Integer position){
        return getItem(position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.poi_list, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v, new ListPoiAdapter.ViewHolder.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                //POI poi = ;
                //System.out.print("poi............ "+ poi.id);
                Toast.makeText(c, Integer.toString(position), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(c, ResourceActivity.class);
                intent.putExtra("id", position);
                c.startActivity(intent);
            }
        });
        //v.setOnClickListener((View.OnClickListener) this);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.id.setText(String.valueOf(pois.get(i).id));
        viewHolder.title.setText(pois.get(i).title);
        viewHolder.location.setText(String.valueOf(pois.get(i).location.getLatitude()) + String.valueOf(pois.get(i).location.getLatitude()));
    }

    @Override
    public int getItemCount() {
        return pois.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}