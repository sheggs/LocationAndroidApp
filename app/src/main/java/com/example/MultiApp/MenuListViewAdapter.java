package com.example.locationapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuListViewAdapter extends ArrayAdapter<String> {
    /** Initalising variables **/
    private Context context = null;
    private String[] options = null;
    private String[] descriptions = null;

    /**
     *
     * @param context The context of the acitivty
     * @param options The titles.
     * @param descriptions The descriptions.
     */
    public MenuListViewAdapter(Context context, String options[], String[] descriptions) {
        super(context, R.layout.listview_layout,options);
        this.context = context;
        this.options = options;
        this.descriptions = descriptions;
    }

    /**
     *
     * @param i The pointer
     * @param convertView The View
     * @param parent the parent.
     * @return The layout of the listview.
     */
    @NonNull
    @Override
    public View getView(int i, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.listview_layout,parent,false);
        TextView title = layout.findViewById(R.id.title_main);
        TextView desc = layout.findViewById(R.id.description_main);

        title.setText(this.options[i]);
        desc.setText(this.descriptions[i]);
        return layout;

    }
}
