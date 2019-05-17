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

    private Context context = null;
    private String[] options = null;
    private Double[] latitude = null;
    private Double[] longitude = null;
    public GPSListViewAdapter(Context context, String[] options, Double[] longitude, Double[] latitude) {
        super(context, R.layout.listview_layout,options);
        this.context = context;
        this.options = options;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    @NonNull
    @Override
    public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.listview_layout,parent,false);
        ImageView images = layout.findViewById(R.id.image_main);
        TextView title = layout.findViewById(R.id.title_main);
        TextView desc = layout.findViewById(R.id.description_main);

        title.setText(this.options[i]);
        desc.setText(this.latitude[i] + " " + this.longitude[i]);
        return layout;

    }
}
