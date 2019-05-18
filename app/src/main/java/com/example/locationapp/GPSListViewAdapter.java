package com.example.locationapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GPSListViewAdapter extends ArrayAdapter<String> {
    /** Intialising fields **/
    private Context context = null;
    private String[] options = null;
    private Double[] latitude = null;
    private Double[] longitude = null;

    /**
     *
     * @param context The context of the activity
     * @param options The list of titles.
     * @param longitude The longitude.
     * @param latitude The latitude.
     */
    public GPSListViewAdapter(Context context, String[] options, Double[] longitude, Double[] latitude) {
        super(context, R.layout.listview_layout,options);
        this.context = context;
        this.options = options;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     *
     * @param i The pointer.
     * @param convertView The View
     * @param parent The parent
     * @return The layout of the listview.
     */
    @NonNull
    @Override
    public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.listview_layout, parent, false);
        /** Remove the first item to make space for back button **/
         if( i != 0) {
            ImageView images = layout.findViewById(R.id.image_main);
            TextView title = layout.findViewById(R.id.title_main);
            TextView desc = layout.findViewById(R.id.description_main);
            title.setText(this.options[i]);
            desc.setText(this.latitude[i] + " " + this.longitude[i]);
         }
        return layout;

    }
}
