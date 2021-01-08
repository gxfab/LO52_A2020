package com.example.projetf1levier;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class RunAdapter extends BaseAdapter {
    teamList m_teams;

    LayoutInflater inflter;

    long ChronoCours;
    Boolean runStart;
    Handler customeHandler = new Handler();
    long startTime = 0L, timeInMilliSeconds = 0L, timeSwapBuff = 0L;


    Run m_context;
    Runnable updateTimerThread = new Runnable() {

        @Override
        public void run() {
            timeInMilliSeconds = SystemClock.uptimeMillis() - startTime;
            ChronoCours = timeSwapBuff + timeInMilliSeconds;
            int secs = (int) (ChronoCours / 1000);
            int mins = secs / 60;
            secs %= 60;
            int milliseconds = (int) (ChronoCours % 1000);
            m_context.getTextTimmer().setText("" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%3d", milliseconds));
            customeHandler.postDelayed(this, 0);
        }
    };

    public RunAdapter(Run context, teamList teams) {

        runStart = false;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = inflter.inflate(R.layout.run_grid_view, null); // inflate the layout
        //convertView = LayoutInflater.from(m_context).inflate(R.layout.activity_team_view, parent, false);;
        team currentTeam = m_teams.getListOfTeam().get(position);

        TextView titlteTeam = (TextView) convertView.findViewById(R.id.title_team);
        ImageView numPlayerTeam = (ImageView) convertView.findViewById(R.id.num_player_team);
        ImageView stepTeam = (ImageView) convertView.findViewById(R.id.step_team);

        numPlayerTeam.setImageResource(m_teams.getImageNum(currentTeam.getNumberPlayerRun()));
        stepTeam.setImageResource(m_teams.getImageStep(currentTeam.getNumberStepRun()));

        titlteTeam.setText("Equipe " + currentTeam.getTeamNumber());

        Button nextStep = (Button) convertView.findViewById(R.id.but_team);

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (runStart) {
                    m_teams.getListOfTeam().get(position).addChrono(ChronoCours);
                    m_teams.getListOfTeam().get(position).nextStepRun();
                    refresh();
                }

            }
        });

        if (currentTeam.getFinishRun()) {
            nextStep.setVisibility(View.INVISIBLE);

            if (m_teams.getNumberTeamFinish()==m_teams.getNbTeam()) {
                m_context.resultClick2();
            }
        }
        return convertView;
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    public void start() {
        runStart = true;
        startTime = SystemClock.uptimeMillis();

        customeHandler.postDelayed(updateTimerThread, 0);
        m_context.getButtonStart().setVisibility(View.INVISIBLE);

        //init timeStart for all tieam
        for (int i = 0; i < m_teams.getListOfTeam().size(); i++) {

            m_teams.getListOfTeam().get(i).setCurrentTime(0L);
        }

    }
}
