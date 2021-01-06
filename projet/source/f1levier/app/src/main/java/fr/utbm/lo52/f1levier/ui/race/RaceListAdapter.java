package fr.utbm.lo52.f1levier.ui.race;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import fr.utbm.lo52.f1levier.R;
import fr.utbm.lo52.f1levier.db.entity.Race;

public class RaceListAdapter extends RecyclerView.Adapter<RaceListAdapter.ViewHolder> {

    private List<Race> mRaces = Collections.emptyList();

    private InteractionListener mListener;

    RaceListAdapter(InteractionListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_race_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Race currentRace = mRaces.get(position);
        holder.nameTextView.setText(currentRace.name);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null)
                    mListener.onRaceItemClicked(currentRace);
            }
        });
    }

    void setRaces(List<Race> races) {
        mRaces = races;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRaces.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final View mView;

        private ViewHolder(View view) {
            super(view);
            mView = view;
            nameTextView = view.findViewById(R.id.text_view_race_name);
        }
    }

    public interface InteractionListener {
        void onRaceItemClicked(Race race);
    }
}
