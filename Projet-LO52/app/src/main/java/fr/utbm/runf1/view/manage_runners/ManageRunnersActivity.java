package fr.utbm.runf1.view.manage_runners;

import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.bottomappbar.BottomAppBar;

import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.utbm.runf1.R;
import fr.utbm.runf1.entities.Runner;
import fr.utbm.runf1.entities.relations.TeamWithRunners;
import fr.utbm.runf1.injection.Injection;
import fr.utbm.runf1.injection.ViewModelFactory;

public class ManageRunnersActivity extends AppCompatActivity implements ManageRunnersRecycleViewAdapter.ItemClickListener {

    private ManageRunnersViewModel manageRunnersViewModel;
    private RecyclerView recyclerView;
    private ManageRunnersRecycleViewAdapter adapter;
    private List<Runner> runnerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_runners);

        this.configureViewModel();

        this.recyclerView = findViewById(R.id.recyclerViewManageRunners);
        BottomAppBar bottomAppBar = findViewById(R.id.bottom_bar);
        ViewTreeObserver vto = bottomAppBar.getViewTreeObserver();
        int default_padding_quarter = (int) getResources().getDimension(R.dimen.default_padding_quarter);
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bottomAppBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                recyclerView.setPadding(default_padding_quarter, default_padding_quarter, default_padding_quarter,bottomAppBar.getMeasuredHeight());

            }
        });

        Button addRunner = findViewById(R.id.addRunnerButton);
        addRunner.setOnClickListener(view -> addRunner());

        this.adapter = new ManageRunnersRecycleViewAdapter();
        this.adapter.setStateRestorationPolicy(RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.recyclerView.setAdapter(adapter);
        this.adapter.setClickListener(this);

        manageRunnersViewModel.getAllRunners().observe(this, runnerList ->{
            adapter.submitList(runnerList);
            this.runnerList = runnerList;
        });
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        this.manageRunnersViewModel = new ViewModelProvider(this, viewModelFactory).get(ManageRunnersViewModel.class);
    }

    private void addRunner() {
        EditText firstNameInput = findViewById(R.id.firstNameInputText);
        EditText lastNameInput = findViewById(R.id.lastNameInputText);
        EditText level = findViewById(R.id.levelInputText);

        boolean ret = false;
        if(firstNameInput.getText().toString().isEmpty()) {
            firstNameInput.setBackgroundTintList(this.getResources().getColorStateList(R.color.button_red, getTheme()));
            ret = true;
        }
        if(lastNameInput.getText().toString().isEmpty()) {
            lastNameInput.setBackgroundTintList(this.getResources().getColorStateList(R.color.button_red, getTheme()));
            ret = true;
        }
        if(level.getText().toString().isEmpty()) {
            level.setBackgroundTintList(this.getResources().getColorStateList(R.color.button_red, getTheme()));
            ret = true;
        }
        if (ret) return;


        Runner runner = new Runner(firstNameInput.getText().toString(), lastNameInput.getText().toString(), Integer.parseInt(level.getText().toString()));

        manageRunnersViewModel.insertRunner(runner);

        firstNameInput.getText().clear();
        lastNameInput.getText().clear();
        level.getText().clear();
    }

    @Override
    public void deleteRunner(View view, int runnerId) {
        Runner runner = this.runnerList.stream().filter(runner1 -> runner1.getId() == runnerId).findFirst().orElse(null);
        if (runner.getTeamId() != null) {
            new AlertDialog.Builder(this)
                    .setTitle("Delete runner")
                    .setMessage("This runner is assigned to a team, deleting will result in team missing a runner. \nDelete anyways ?" )
                    .setNegativeButton(android.R.string.cancel, null) // dismisses by default
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> manageRunnersViewModel.deleteRunner(runnerId))
                    .create()
                    .show();
        } else {
            this.manageRunnersViewModel.deleteRunner(runnerId);
        }
    }

    @Override
    public void onItemClick(View view, Runner runner) {
        AlertDialog.Builder alertName = new AlertDialog.Builder(this);
        final EditText editTextName1 = new EditText(this);
        editTextName1.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextName1.setGravity(Gravity.CENTER_HORIZONTAL);
        alertName.setTitle("Edit runner level");
        alertName.setView(editTextName1);
        LinearLayout layoutName = new LinearLayout(this);
        layoutName.setOrientation(LinearLayout.VERTICAL);
        layoutName.addView(editTextName1);
        alertName.setView(layoutName);alertName.setPositiveButton("Ok", (dialog, whichButton) -> collectInput(runner, editTextName1.getText().toString()));
        alertName.setNegativeButton("Cancel", (dialog, whichButton) -> dialog.cancel());
        alertName.show();
    }
    private void collectInput(Runner runner, String newLevel) {
        runner.setLevel(Integer.parseInt(newLevel));
        this.manageRunnersViewModel.updateRunner(runner);
        Objects.requireNonNull(this.recyclerView.getAdapter()).notifyDataSetChanged();
    }
}