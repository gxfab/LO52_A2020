package com.example.f1_levier.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.f1_levier.BDD.entity.Runner;
import com.example.f1_levier.R;

import java.util.List;

public class TimeAdapter extends ArrayAdapter<List<String>> {
    private static class ViewHolder {
    TextView id;
    TextView time;
    }
    public TimeAdapter(Context context, List<List<String>> team_time) {
        super(context, R.layout.item_time, team_time);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        List<String> p = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        TimeAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new  TimeAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_time, parent, false);
            viewHolder.id = (TextView) convertView.findViewById(R.id.textView_t_team);
            viewHolder.time = (TextView) convertView.findViewById(R.id.textView_time);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (TimeAdapter.ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.id.setText(p.get(0));
        viewHolder.time.setText(p.get(1));
        // Return the completed view to render on screen
        return convertView;
    }
}
