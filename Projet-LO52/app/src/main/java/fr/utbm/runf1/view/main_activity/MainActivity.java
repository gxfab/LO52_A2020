package fr.utbm.runf1.view.main_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import fr.utbm.runf1.R;
import fr.utbm.runf1.entities.Runner;
import fr.utbm.runf1.injection.Injection;
import fr.utbm.runf1.injection.ViewModelFactory;
import fr.utbm.runf1.view.manage_runners.ManageRunnersActivity;
import fr.utbm.runf1.view.manage_runners.ManageRunnersViewModel;
import fr.utbm.runf1.view.manage_teams.ManageTeamsActivity;
import fr.utbm.runf1.view.statistics.RacesActivity;
import fr.utbm.runf1.view.time_runners.TimeRunnersActivity;

public class MainActivity extends AppCompatActivity {

    private ManageRunnersViewModel manageRunnersViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.manageRunnersViewModel = new ViewModelProvider(this, viewModelFactory).get(ManageRunnersViewModel.class);

        this.manageRunnersViewModel.clearRunnerTable();
        this.manageRunnersViewModel.clearTeamTable();
        prepopulateDatabase();

    }

    public void switchActivityRunners(View view) {
        Intent intent = new Intent(MainActivity.this, ManageRunnersActivity.class);
        startActivity(intent);
    }

    public void switchActivityTeams(View view) {
            Intent intent = new Intent(MainActivity.this, ManageTeamsActivity.class);
            startActivity(intent);

    }

    public void switchActivityTimeRunners(View view) {
            Intent intent = new Intent(MainActivity.this, TimeRunnersActivity.class);
            startActivity(intent);
    }

    public void switchActivityStatistics(View view) {
        Intent intent = new Intent(MainActivity.this, RacesActivity.class);
        startActivity(intent);
    }

    private void prepopulateDatabase() {

        this.manageRunnersViewModel.insertRunner(new Runner("Bruno", "Bonami", 50));
        this.manageRunnersViewModel.insertRunner(new Runner("Thibaut", "Cotuand", 12));
        this.manageRunnersViewModel.insertRunner(new Runner("Namo", "Aucoin", 45));
        this.manageRunnersViewModel.insertRunner(new Runner("Yves", "Boncoeur", 85));
        this.manageRunnersViewModel.insertRunner(new Runner("Paul", "Dupéré", 45));
        this.manageRunnersViewModel.insertRunner(new Runner("Jérôme", "Baron", 89));
        this.manageRunnersViewModel.insertRunner(new Runner("Marc","Brodeur", 45));
        this.manageRunnersViewModel.insertRunner(new Runner("Charlot", "Tessier", 92));
        this.manageRunnersViewModel.insertRunner(new Runner("Sébastien", "Couet", 75));
        this.manageRunnersViewModel.insertRunner(new Runner("Romain", "Duplessis", 65));
        this.manageRunnersViewModel.insertRunner(new Runner("Antoine", "Bouchard", 35));
        this.manageRunnersViewModel.insertRunner(new Runner("Arthur", "Audet", 21));
        this.manageRunnersViewModel.insertRunner(new Runner("Lucas", "Duplanty", 32));
        this.manageRunnersViewModel.insertRunner(new Runner("Hugo", "Pitre", 88));
        this.manageRunnersViewModel.insertRunner(new Runner("Alice", "Giguère", 51));
        this.manageRunnersViewModel.insertRunner(new Runner("Liam", "Piedalue", 52));
        this.manageRunnersViewModel.insertRunner(new Runner("Chloé", "Masson", 35));
        this.manageRunnersViewModel.insertRunner(new Runner("Inès", "Monrency", 95));
        this.manageRunnersViewModel.insertRunner(new Runner("Sacha", "Babin", 90));
    }

}