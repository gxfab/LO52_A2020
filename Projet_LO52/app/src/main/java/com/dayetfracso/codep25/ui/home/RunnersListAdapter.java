package com.dayetfracso.codep25.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dayetfracso.codep25.R;
import com.dayetfracso.codep25.dao.AppDatabase;
import com.dayetfracso.codep25.entity.Runner;

import java.util.List;

public class RunnersListAdapter extends ArrayAdapter<Runner> {

    Context context;
    List<Runner> runners;
    LayoutInflater inflater;

    public RunnersListAdapter(@NonNull Context context, List<Runner> runners) {
        super(context, R.layout.runners_list_model,runners);
        this.context = context;
        this.runners = runners;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
        {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.runners_list_model, null);
        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.runner_name);
        TextView levelTextView = (TextView) convertView.findViewById(R.id.runner_level);
        Button deleteRunnerButton = (Button) convertView.findViewById(R.id.delete_runner_button);

        Runner runner = runners.get(position);
        nameTextView.setText(runner.getFullName());
        levelTextView.setText(Integer.toString(runner.getLevel()));
        View finalConvertView = convertView;
        deleteRunnerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                AppDatabase db = AppDatabase.getDatabase(context);
                db.runnerDao().delete(runner);
                runners.remove(runner);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }
}
