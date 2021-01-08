package fr.utbm.runf1.view.manage_order_in_team;

import android.content.Context;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import fr.utbm.runf1.R;
import fr.utbm.runf1.entities.Runner;

/**
 * Created by Yosef B.I.
 */
public class ManageOrderInTeamRecycleviewAdapter extends  RecyclerView.Adapter<ManageOrderInTeamRecycleviewAdapter.ViewHolder> implements  ItemTouchHelperAdapter {
    private List<Runner> runnerList;
    private LayoutInflater layoutInflater;
    private ManageOrderInTeamRecycleviewAdapter.ItemClickListener itemClickListener;
    private ItemTouchHelper itemTouchHelper;

    public ManageOrderInTeamRecycleviewAdapter(Context context, List<Runner> runnerList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.runnerList = runnerList;
    }

    public List<Runner> getRunnerList() {
        return runnerList;
    }

    @NonNull
    @Override
    public ManageOrderInTeamRecycleviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_manage_order_in_team_row, parent, false);
        return new ManageOrderInTeamRecycleviewAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ManageOrderInTeamRecycleviewAdapter.ViewHolder holder, int position) {
        Runner runner = runnerList.get(position);
        StringBuilder runnerName = new StringBuilder();
        runnerName.append(runner.getFirstName()).append(" ").append(runner.getLastName());
        holder.firstNameTextView.setText(runnerName);
        holder.levelTextView.setText(String.valueOf(runner.getLevel()));
        holder.runner = runner;
    }

    @Override
    public int getItemCount() {
        return this.runnerList.size();
    }

    // allows clicks events to be caught
    public void setClickListener(ManageOrderInTeamRecycleviewAdapter.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnTouchListener, GestureDetector.OnGestureListener {
        TextView firstNameTextView;
        TextView levelTextView;
        Runner runner;
        GestureDetector gestureDetector;

        ViewHolder(View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.manageOrderInTeamRecyclerViewRowFirstName);
            levelTextView = itemView.findViewById(R.id.manageOrderInTeamRecyclerViewRowLevel);
            gestureDetector = new GestureDetector(itemView.getContext(), this);
            itemView.setOnTouchListener(this);
            itemView.setOnClickListener(view -> {
                if (itemClickListener != null) itemClickListener.onItemClick(view, getAdapterPosition());
            });

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            gestureDetector.onTouchEvent(event);
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            itemTouchHelper.startDrag(this);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int teamId);
        void acceptOrder(View view, List<Runner> runnerList);
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper)  {
        this.itemTouchHelper = itemTouchHelper;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Runner fromRunner = runnerList.get(fromPosition);
        runnerList.remove(fromPosition);
        runnerList.add(toPosition, fromRunner);
        notifyItemMoved(fromPosition, toPosition);
    }
}
