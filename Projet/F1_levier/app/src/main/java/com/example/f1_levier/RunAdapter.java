package com.example.f1_levier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import BDD.entity.Team;

public class RunAdapter extends ArrayAdapter<Team> {
    private static class ViewHolder {
        TextView name;
        TextView fname;
        TextView id_team;
        TextView id_step;
        ImageView p1;
        ImageView sprint;

    }

    public RunAdapter(Context context, ArrayList<Team> team) {
        super(context, R.layout.item_run, team);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Team t = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        RunAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new RunAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_run, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textViewName);
            viewHolder.fname = (TextView) convertView.findViewById(R.id.textViewFName);
            viewHolder.id_team = (TextView) convertView.findViewById(R.id.textView_id_team);
            viewHolder.id_step= (TextView) convertView.findViewById(R.id.textView_id_pass);
            viewHolder.p1 = (ImageView) convertView.findViewById(R.id.imageViewP1);
            viewHolder.sprint = (ImageView) convertView.findViewById(R.id.imageViewSprt);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (RunAdapter.ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.name.setText(t.getParticipants().get(0).getName());
        viewHolder.fname.setText(t.getParticipants().get(0).getFirstName());
        viewHolder.id_team.setText(String.valueOf(t.getId()));
        viewHolder.id_step.setText(String.valueOf(1));
        viewHolder.p1.setVisibility(View.VISIBLE);
        viewHolder.sprint.setVisibility(View.VISIBLE);

        // Return the completed view to render on screen
        return convertView;
    }
}
