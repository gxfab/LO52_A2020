package utbm.lepysidawy.code_p25;

import androidx.appcompat.app.AppCompatActivity;
import utbm.lepysidawy.code_p25.database.AppDatabase;
import utbm.lepysidawy.code_p25.entity.Runner;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;


/**
 * In this activity, the user can create a runner by
 * entering its name, first name and level between 1 and 100
 */
public class RunnerCreationActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private NumberPicker levelPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runner_creation);
        this.levelPicker = findViewById(R.id.levelPicker);
        this.firstName = findViewById(R.id.firstName);
        this.lastName = findViewById(R.id.lastName);
        this.pickerInitialization();
    }

    /**
     * Method which initializes the picker
     */
    public void pickerInitialization() {
        this.levelPicker.setMaxValue(100);
        this.levelPicker.setMinValue(1);
    }

    /**
     * Method used to validate a runner and add it to the database
     * @param view
     */
    public void validateRunner(View view) {
        Runner newRunner = new Runner(this.firstName.getText().toString(), this.lastName.getText().toString(), this.levelPicker.getValue());
        AppDatabase db = AppDatabase.getInstance(this);
        db.runnerDAO().insertAll(newRunner);
        this.finish();
    }
}