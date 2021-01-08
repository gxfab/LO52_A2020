package com.djira.f1levier.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.djira.f1levier.CourseActivity;
import com.djira.f1levier.R;
import com.djira.f1levier.database.AppDB;
import com.djira.f1levier.entity.Equipe;
import com.djira.f1levier.entity.Participant;

import java.util.List;

public class ClassementAdapter extends RecyclerView.Adapter<ClassementAdapter.ViewHolder> {
    // Initialize variable
    private List<Equipe> equipes;
    private List<Participant> participants;
    private FragmentActivity context;
    private AppDB database;


    // Create constructor

    public ClassementAdapter(FragmentActivity context, List<Equipe> equipes, List<Participant> participants) {
        this.context = context;
        this.equipes = equipes;
        this.participants = participants;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_equipe_classement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // initialize main data
        Equipe data = equipes.get(position);
        // Initialize database
        database = AppDB.getInstance(context);
        //Set text on text view
        holder.textViewRange.setText(String.valueOf(position + 1));
        holder.textViewEquipe.setText(data.getName() + " [ Temps de course : " + data.getTemps() + " ]");
        List<Participant> participantList = database.participantDao().getAllByEquipeID(data.getID());

        for (int i=0; i<participantList.size();i++) {
            Participant p = participantList.get(i);
            if (i==0)
            holder.textViewP1.setText((i+1) + ". " + p.getName() +
                    "  \n[ SP1 : " + p.getT1() +
                    " | SP2 : " + p.getT2()+
                    " | PS : " + p.getPs()+
                    " | SP3 : " + p.getT3()+
                    " | SP4 : " + p.getT4() + " ] \n" +
                    " [ Total temps : " + (p.getT1()+ p.getT2()+ p.getPs()+ p.getT3()+ p.getT4()) + " ]" );
            if (i==1)
                holder.textViewP2.setText((i+1) + ". " + p.getName() +
                        " \n [ SP1 : " + p.getT1() +
                        " | SP2 : " + p.getT2()+
                        " | PS : " + p.getPs()+
                        " | SP3 : " + p.getT3()+
                        " | SP4 : " + p.getT4() + " ] \n" +
                        " [ Total temps : " + (p.getT1()+ p.getT2()+ p.getPs()+ p.getT3()+ p.getT4()) + " ]" );
            if (i==2)
                holder.textViewP3.setText((i+1) + ". " + p.getName() +
                        "  \n[ SP1 : " + p.getT1() +
                        " | SP2 : " + p.getT2()+
                        " | PS : " + p.getPs()+
                        " | SP3 : " + p.getT3()+
                        " | SP4 : " + p.getT4() + " ] \n" +
                        " [ Total temps : " + (p.getT1()+ p.getT2()+ p.getPs()+ p.getT3()+ p.getT4()) + " ]" );
        }

    }

    @Override
    public int getItemCount() {
        return equipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewRange, textViewEquipe, textViewP1, textViewP2, textViewP3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRange = itemView.findViewById(R.id.text_view_range);
            textViewEquipe = itemView.findViewById(R.id.text_view_equipe);
            textViewP1 = itemView.findViewById(R.id.text_view_class_p1);
            textViewP2 = itemView.findViewById(R.id.text_view_class_p2);
            textViewP3 = itemView.findViewById(R.id.text_view_class_p3);
        }
    }
}
