package com.example.projetf1levier;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TeamAdapter extends BaseAdapter {
    teamList m_teams;

    LayoutInflater inflter;
    private Context m_context;

    public TeamAdapter(Context context, teamList teams) {
        m_context = context;
        inflter = (LayoutInflater.from(context));
        m_teams = teams;
    }

    @Override
    public int getCount() {
        return m_teams.getNbTeam();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflter.inflate(R.layout.grid_view_team, null); // inflate the layout
        //convertView = LayoutInflater.from(m_context).inflate(R.layout.activity_team_view, parent, false);;
        team t = m_teams.getListOfTeam().get(position);

        player p1 = t.getPlayerList().get(0);
        player p2 = t.getPlayerList().get(1);
        player p3 = t.getPlayerList().get(2);

        TextView nameP1 = (TextView) convertView.findViewById(R.id.nameP1);
        TextView nameP2 = (TextView) convertView.findViewById(R.id.nameP2);
        TextView nameP3 = (TextView) convertView.findViewById(R.id.nameP3);

        TextView teamName = (TextView) convertView.findViewById(R.id.teamName);

        nameP1.setText(p1.getFullName());
        nameP2.setText(p2.getFullName());
        nameP3.setText(p3.getFullName());

        teamName.setText("Equipe " + t.getTeamNumber());

        return convertView;
    }
}
