package fr.utbm.runf1.view.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import fr.utbm.runf1.R;
import fr.utbm.runf1.entities.Race;
import fr.utbm.runf1.injection.Injection;
import fr.utbm.runf1.injection.ViewModelFactory;

public class RacesActivity extends AppCompatActivity {
    ListView listView;
    TextView textView;
    private StatisticsViewModel manageTeamsViewModel;
    private List<Race> races;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_races);
        this.configureViewModel();
        listView=(ListView)findViewById(R.id.listView);
        textView=(TextView)findViewById(R.id.textView);

        this.manageTeamsViewModel.getAllRaces().observe(this, races1 -> {
            ArrayList<String> tempList = new ArrayList<>();
            Collections.reverse(races1);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            races1.forEach(race -> {
                if(race.getDate() != null) tempList.add(formatter.format(race.getDate()));
            });
            races = races1;
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, tempList);
            listView.setAdapter(adapter);
        });
        listView.setOnItemClickListener((adapterView, view, position, l) -> {
            Intent intent = new Intent(RacesActivity.this, StatisticsActivity.class);
            intent.putExtra("EXTRA_RACE_ID", races.get(position).getId());
            startActivity(intent);
        });
    }
    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.manageTeamsViewModel = new ViewModelProvider(this, viewModelFactory).get(StatisticsViewModel.class);
    }
}