package com.example.f1_levier.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.f1_levier.utils.Card;
import com.example.f1_levier.utils.Card;
import com.example.f1_levier.R;
import com.example.f1_levier.view.RunActivity;

import java.util.ArrayList;

public class RunAdapter extends RecyclerView.Adapter<RunAdapter.MyViewHolder> {

    private ArrayList<Card> cardSet;


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewIdTeam;
        ImageView imageViewPerson;
        TextView textViewIdStep;
        ImageView imageViewStep;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.textView_name);
            this.textViewIdTeam = itemView.findViewById(R.id.textView_id_team);
            this.imageViewPerson = itemView.findViewById(R.id.imageView_person);
            this.textViewIdStep = itemView.findViewById(R.id.textView_id_step);
            this.imageViewStep = itemView.findViewById(R.id.imageView_step);
        }
    }

    public RunAdapter(ArrayList<Card> card) {
        this.cardSet = card;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_run, parent, false);

        view.setOnClickListener(RunActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewIdTeam = holder.textViewIdTeam;
        ImageView imageViewPerson = holder.imageViewPerson;
        TextView textViewIdStep= holder.textViewIdStep;
        ImageView imageViewStep = holder.imageViewStep;

        textViewName.setText(cardSet.get(listPosition).getName());
        textViewIdTeam.setText(String.valueOf(cardSet.get(listPosition).getIdTeam()));
        imageViewPerson.setImageResource(cardSet.get(listPosition).getImagePerson());
        textViewIdStep.setText(String.valueOf(cardSet.get(listPosition).getIdStep()));
        imageViewStep.setImageResource(cardSet.get(listPosition).getImageStep());
    }

    @Override
    public int getItemCount() {
        return cardSet.size();
    }
}
