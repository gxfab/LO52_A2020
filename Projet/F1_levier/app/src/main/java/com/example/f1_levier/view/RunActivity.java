package com.example.f1_levier.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.f1_levier.R;
import com.example.f1_levier.adapter.RunAdapter;
import com.example.f1_levier.utils.Card;
import com.example.f1_levier.utils.ElementCard;

import java.util.ArrayList;
import static com.example.f1_levier.view.TeamActivity.teams;

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
            if(win_team.size() == 10){
                b_stat.setVisibility(View.VISIBLE);
                isClickable=false;
                cdt.stop();
            }
        }

        private void editItem(View v) {
            int selectedItemPosition = recyclerView.getChildAdapterPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForAdapterPosition(selectedItemPosition);
            TextView textViewName = viewHolder.itemView.findViewById(R.id.textView_name);
            TextView textViewIdStep= viewHolder.itemView.findViewById(R.id.textView_id_step);
            ImageView imageViewStep = viewHolder.itemView.findViewById(R.id.imageView_step);
            ImageView imageViewPerson = viewHolder.itemView.findViewById(R.id.imageView_person);
            int step = teams.get(selectedItemPosition).getNb_step();
            teams.get(selectedItemPosition).setNb_step(teams.get(selectedItemPosition).getNb_step()+1);
            Log.i("value is",""+step);
            switch (step) {
                case 2:
                    textViewIdStep.setText(String.valueOf(ElementCard.id_step[1]));
                    imageViewStep.setImageResource(ElementCard.drawableArray[4]);
                    break;
                case 3:
                    imageViewStep.setImageResource(ElementCard.drawableArray[5]);
                    if (teams.get(selectedItemPosition).getIdP() == 3) {
                        teams.get(selectedItemPosition).setGoal(true);
                    }
                    break;
                case 4:
                    if (!teams.get(selectedItemPosition).getGoal()) {
                        textViewName.setText(ElementCard.nameArray.get(selectedItemPosition).get(teams.get(selectedItemPosition).getIdP()));
                        textViewIdStep.setText(String.valueOf(ElementCard.id_step[0]));
                        imageViewStep.setImageResource(ElementCard.drawableArray[4]);
                        imageViewPerson.setImageResource(ElementCard.drawableArray[teams.get(selectedItemPosition).getIdP()]);
                        teams.get(selectedItemPosition).setNb_step(0);
                        teams.get(selectedItemPosition).setIdP(teams.get(selectedItemPosition).getIdP() + 1);
                    } else {
                        imageViewStep.setImageResource(ElementCard.drawableArray[7]);
                        win_team.add(selectedItemPosition+1);
                        //TODO enregistré chrono DB

                    }
                    break;
                default:
                    if (!teams.get(selectedItemPosition).getGoal()) {
                        imageViewStep.setImageResource(ElementCard.drawableArray[4 + step + 1]);
                    }
            }
        }
    }

    public void stat(){
        Intent intent = new Intent(this, StatActivity.class);
        startActivity(intent);
    }
}
