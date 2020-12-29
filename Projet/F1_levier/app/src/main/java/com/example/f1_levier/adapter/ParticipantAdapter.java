package com.example.f1_levier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.f1_levier.BDD.entity.Runner;
import com.example.f1_levier.R;

import java.util.ArrayList;

public class ParticipantAdapter extends ArrayAdapter<Runner> {
    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView fname;
        TextView level;
    }

    public ParticipantAdapter(Context context, ArrayList<Runner> p) {
        super(context, R.layout.item_participant, p);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Runner p = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_participant, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.fname = (TextView) convertView.findViewById(R.id.tvFName);
            viewHolder.level = (TextView) convertView.findViewById(R.id.tvLvl);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.name.setText(p.getLastName());
        viewHolder.fname.setText(p.getFirstName());
        viewHolder.level.setText(String.valueOf(p.getLevel()));
        // Return the completed view to render on screen
        return convertView;
    }
}
