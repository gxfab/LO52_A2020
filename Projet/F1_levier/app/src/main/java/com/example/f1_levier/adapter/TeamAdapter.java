package com.example.f1_levier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.f1_levier.BDD.entity.Team;
import com.example.f1_levier.R;

import java.util.ArrayList;

import static com.example.f1_levier.view.MainActivity.db;
import static com.example.f1_levier.view.MainActivity.runnerList;

public class TeamAdapter extends ArrayAdapter<Team> {

    // View lookup cache
    private static class ViewHolder {
        TextView name_p1;
        TextView name_p2;
        TextView name_p3;
        TextView fname_p1;
        TextView fname_p2;
        TextView fname_p3;
        TextView level;
        TextView id_team;
    }

    public TeamAdapter(Context context, ArrayList<Team> t) {
        super(context, R.layout.item_team, t);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Team team = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_team, parent, false);
            viewHolder.name_p1 = (TextView) convertView.findViewById(R.id.textView_name_p1);
            viewHolder.name_p2 = (TextView) convertView.findViewById(R.id.textView_name_p2);
            viewHolder.name_p3 = (TextView) convertView.findViewById(R.id.textView_name_p3);
            viewHolder.fname_p1 = (TextView) convertView.findViewById(R.id.textView_fname_p1);
            viewHolder.fname_p2 = (TextView) convertView.findViewById(R.id.textView_fname_p2);
            viewHolder.fname_p3 = (TextView) convertView.findViewById(R.id.textView_fname_p3);
            viewHolder.level = (TextView) convertView.findViewById(R.id.textView_nb_lvl);
            viewHolder.id_team = (TextView) convertView.findViewById(R.id.textView_id_team);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.name_p1.setText(db.getRunnerFromId(runnerList, team.getFirstRunnerId()).getLastName());
        viewHolder.name_p2.setText(db.getRunnerFromId(runnerList, team.getSecondRunnerId()).getLastName());
        viewHolder.name_p3.setText(db.getRunnerFromId(runnerList, team.getThirdRunnerId()).getLastName());
        viewHolder.fname_p1.setText(db.getRunnerFromId(runnerList, team.getFirstRunnerId()).getFirstName());
        viewHolder.fname_p2.setText(db.getRunnerFromId(runnerList, team.getSecondRunnerId()).getFirstName());
        viewHolder.fname_p3.setText(db.getRunnerFromId(runnerList, team.getThirdRunnerId()).getFirstName());
        viewHolder.level.setText(String.valueOf(team.getLevel()));
        viewHolder.id_team.setText(String.valueOf(team.getTeamId()+1));
        // Return the completed view to render on screen
        return convertView;
    }
}
