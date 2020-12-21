package com.example.f1_levier;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.OptionalDataException;
import java.util.ArrayList;
import static com.example.f1_levier.TeamActivity.teams;

public class RunActivity extends AppCompatActivity {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Data> data_init;
    public static boolean isClickable = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        final Chronometer cdt = findViewById(R.id.chronometer);
        final Button start = findViewById(R.id.button_start);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_run);

        myOnClickListener = new MyOnClickListener(this);
        layoutManager = new GridLayoutManager(this,4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data_init = new ArrayList<Data>();
        for (int i = 0; i < teams.size(); i++) {
            data_init.add(new Data(
                    MyData.nameArray.get(i).get(0),
                    MyData.id_team[i]
            ));
        }

        adapter = new RunAdapter(data_init);
        recyclerView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setVisibility(View.INVISIBLE);

                for(int i=0;i<teams.size();i++){
                    RecyclerView.ViewHolder viewHolder
                  = recyclerView.findViewHolderForAdapterPosition(i);
                    ImageView imageViewStep = viewHolder.itemView.findViewById(R.id.imageView_step);
                    imageViewStep.setImageResource(MyData.drawableArray[4]);
                }

                cdt.setBase(SystemClock.elapsedRealtime());
                cdt.start();
                isClickable = true;
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
                    textViewIdStep.setText(String.valueOf(MyData.id_step[1]));
                    imageViewStep.setImageResource(MyData.drawableArray[4]);
                    break;
                case 3:
                    imageViewStep.setImageResource(MyData.drawableArray[5]);
                    if (teams.get(selectedItemPosition).getIdP() == 3) {
                        teams.get(selectedItemPosition).setGoal(true);
                    }
                    break;
                case 4:
                    if (!teams.get(selectedItemPosition).getGoal()) {
                        textViewName.setText(MyData.nameArray.get(selectedItemPosition).get(teams.get(selectedItemPosition).getIdP()));
                        textViewIdStep.setText(String.valueOf(MyData.id_step[0]));
                        imageViewStep.setImageResource(MyData.drawableArray[4]);
                        imageViewPerson.setImageResource(MyData.drawableArray[teams.get(selectedItemPosition).getIdP()]);
                        teams.get(selectedItemPosition).setNb_step(0);
                        teams.get(selectedItemPosition).setIdP(teams.get(selectedItemPosition).getIdP() + 1);
                    } else {
                        imageViewStep.setImageResource(MyData.drawableArray[7]);
                    }
                    break;
                default:
                    if (!teams.get(selectedItemPosition).getGoal()) {
                        imageViewStep.setImageResource(MyData.drawableArray[4 + step + 1]);
                    }
            }
        }
    }
}
