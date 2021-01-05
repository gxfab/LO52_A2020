package com.example.f1_levier.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.f1_levier.BDD.entity.Runner;
import com.example.f1_levier.R;
import com.example.f1_levier.adapter.RunAdapter;
import com.example.f1_levier.utils.Card;
import com.example.f1_levier.utils.ElementCard;

import java.util.ArrayList;

import static com.example.f1_levier.view.TeamActivity.teams;
import static com.example.f1_levier.view.MainActivity.db;
import static com.example.f1_levier.view.MainActivity.runnerList;

public class RunActivity extends AppCompatActivity {
    public static View.OnClickListener myOnClickListener;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Card> cards;
    public static boolean isClickable = false;
    public static Button b_stat;
    public static ArrayList<Integer> win_team;
    private static Chronometer cdt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        cdt = findViewById(R.id.chronometer);
        final Button b_start = findViewById(R.id.button_start);
        b_stat = findViewById(R.id.button_stat);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_run);

        myOnClickListener = new MyOnClickListener(this);
        layoutManager = new GridLayoutManager(this,4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        cards = new ArrayList<Card>();
        for (int i = 0; i < teams.size(); i++) {
            cards.add(new Card(
                    ElementCard.nameArray.get(i).get(0),
                    ElementCard.id_team[i]
            ));
        }

        adapter = new RunAdapter(cards);
        recyclerView.setAdapter(adapter);

        win_team = new ArrayList<>();

        b_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b_start.setVisibility(View.INVISIBLE);

                for(int i=0;i<teams.size();i++){
                    RecyclerView.ViewHolder viewHolder
                  = recyclerView.findViewHolderForAdapterPosition(i);
                    ImageView imageViewStep = viewHolder.itemView.findViewById(R.id.imageView_step);
                    imageViewStep.setImageResource(ElementCard.drawableArray[4]);
                }

                cdt.setBase(SystemClock.elapsedRealtime());
                cdt.start();
                isClickable = true;
            }
        });

        b_stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stat();
            }
        });
    }

    private static class MyOnClickListener implements View.OnClickListener {
        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            if(isClickable) {
                editItem(v);
            }
            if(win_team.size() == teams.size()){
                b_stat.setVisibility(View.VISIBLE);
                isClickable=false;
                cdt.stop();
            }
        }

        private void editItem(View v) {
            int selectedItemPosition = recyclerView.getChildAdapterPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForAdapterPosition(selectedItemPosition);
            TextView textViewName = viewHolder.itemView.findViewById(R.id.tv_np);
            TextView textViewIdStep= viewHolder.itemView.findViewById(R.id.textView_id_step);
            ImageView imageViewStep = viewHolder.itemView.findViewById(R.id.imageView_step);
            ImageView imageViewPerson = viewHolder.itemView.findViewById(R.id.imageView_person);
            int step = teams.get(selectedItemPosition).getNb_step();

            //Find the current runner
            int runnerId = - 1;
            switch(teams.get(selectedItemPosition).get_id_p())
            {
                case 1:
                    runnerId = teams.get(selectedItemPosition).getFirstRunnerId();
                    break;
                case 2:
                    runnerId = teams.get(selectedItemPosition).getSecondRunnerId();
                    break;
                case 3:
                    runnerId = teams.get(selectedItemPosition).getThirdRunnerId();
                    break;
            }
            Runner currentRunner = db.getRunnerFromId(runnerList, runnerId);

            //Update the time of that runner
            switch (step)
            {
                case 0:
                    currentRunner.setTime1(SystemClock.elapsedRealtime() - cdt.getBase());
                    break;
                case 1:
                    currentRunner.setTime2(SystemClock.elapsedRealtime() - cdt.getBase());
                    break;
                case 2:
                    currentRunner.setTime3(SystemClock.elapsedRealtime() - cdt.getBase());
                    break;
                case 3:
                    currentRunner.setTime4(SystemClock.elapsedRealtime() - cdt.getBase());
                    break;
                case 4:
                    currentRunner.setTime5(SystemClock.elapsedRealtime() - cdt.getBase());
                    break;
            }


            teams.get(selectedItemPosition).setNb_step(teams.get(selectedItemPosition).getNb_step()+1);
//            Log.i("value is",""+step);
            switch (step) {
                case 2:
                    textViewIdStep.setText(String.valueOf(ElementCard.id_step[1]));
                    imageViewStep.setImageResource(ElementCard.drawableArray[4]);
                    break;
                case 3:
                    imageViewStep.setImageResource(ElementCard.drawableArray[5]);
                    if (teams.get(selectedItemPosition).get_id_p() == 3) {
                        teams.get(selectedItemPosition).set_goal(true);
                    }
                    break;
                case 4:
                    db.runnerDAO().updateRunner(currentRunner);
                    if (!teams.get(selectedItemPosition).is_goal()) {
                        textViewName.setText(ElementCard.nameArray.get(selectedItemPosition).get(teams.get(selectedItemPosition).get_id_p()));
                        textViewIdStep.setText(String.valueOf(ElementCard.id_step[0]));
                        imageViewStep.setImageResource(ElementCard.drawableArray[4]);
                        imageViewPerson.setImageResource(ElementCard.drawableArray[teams.get(selectedItemPosition).get_id_p()]);
                        teams.get(selectedItemPosition).setNb_step(0);
                        teams.get(selectedItemPosition).set_id_p(teams.get(selectedItemPosition).get_id_p() + 1);
                    } else {
                        imageViewStep.setImageResource(ElementCard.drawableArray[7]);
                        teams.get(selectedItemPosition).setRating(win_team.size() + 1);
                        win_team.add(selectedItemPosition+1);
                        teams.get(selectedItemPosition).setTime(SystemClock.elapsedRealtime() - cdt.getBase());
//                        System.out.println("time ::::::" + teams.get(selectedItemPosition).getTime());
//                        System.out.println("classement ::::::" + teams.get(selectedItemPosition).getRating());
                        db.teamDAO().updateTeam(teams.get(selectedItemPosition));
                    }
                    break;
                default:
                    if (!teams.get(selectedItemPosition).is_goal()) {
                        imageViewStep.setImageResource(ElementCard.drawableArray[4 + step + 1]);
                    }
            }
        }
    }

    private void stat(){
        Intent intent = new Intent(this, StatActivity.class);
        startActivity(intent);
    }
}
