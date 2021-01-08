package fr.utbm.runf1.view.manage_runners;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import fr.utbm.runf1.R;
import fr.utbm.runf1.entities.Runner;

/**
 * Created by Yosef B.I.
 */
public class ManageRunnersRecycleViewAdapter extends ListAdapter<Runner, ManageRunnersRecycleViewAdapter.ViewHolder> {
    private ItemClickListener itemClickListener;

    private static final DiffUtil.ItemCallback<Runner> DIFF_CALLBACK = new DiffUtil.ItemCallback<Runner>() {
        @Override
        public boolean areItemsTheSame(@NonNull Runner oldItem, @NonNull Runner newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Runner oldItem, @NonNull Runner newItem) {
            return oldItem.getFirstName().equals(newItem.getFirstName())
                    || oldItem.getLastName().equals(newItem.getLastName())
                    || oldItem.getLevel() == newItem.getLevel();
        }
    };

    public ManageRunnersRecycleViewAdapter() {
        super(DIFF_CALLBACK);
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_manage_runners_row, parent, false);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Runner runner = getItem(position);
        holder.positionIndicator.setText((position + 1) + ")");
        holder.runnerName.setText(runner.getFirstName() + " " + runner.getLastName());
        holder.runnerLevel.setText("Level : " + runner.getLevel());
        holder.runner = runner;
    }


    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView runnerName;
        TextView runnerLevel;
        TextView positionIndicator;
        Runner runner;

        ViewHolder(View itemView) {
            super(itemView);
            positionIndicator = itemView.findViewById(R.id.recyclerViewManageRunnersRowPosition);
            runnerName = itemView.findViewById(R.id.recyclerViewManageRunnersRow);
            runnerLevel = itemView.findViewById(R.id.recyclerViewManageRunnersRowLevel);
            itemView.findViewById(R.id.deleteRunnerButton).setOnClickListener(view -> itemClickListener.deleteRunner(view, runner.getId()));
            itemView.setOnClickListener(view -> {
                if (itemClickListener != null) itemClickListener.onItemClick(view, runner);
            });

        }
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void deleteRunner(View view, int runnerId);
        void onItemClick(View view, Runner runner);
    }
}
