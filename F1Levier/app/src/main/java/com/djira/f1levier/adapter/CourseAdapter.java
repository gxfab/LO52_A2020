package com.djira.f1levier.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    // Initialize variable
    private List<Participant> dataList;
    private FragmentActivity context;
    private AppDB database;
    private int s1=0,s2=0,ps=0,s3=0,s4=0;

    // Create constructor

    public CourseAdapter(FragmentActivity context, List<Participant> dataList) {
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_equie_participant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // initialize main data
        Participant data = dataList.get(position);
        // Initialize database
        database = AppDB.getInstance(context);
        //Set text on text view
        holder.textViewNom.setText("Participant : " + data.getName());

        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Participant d = dataList.get(holder.getAdapterPosition());
                CourseActivity.startChronometer(v);
                s1=CourseActivity.getTime(v);
                holder.textViewS1.setText(String.valueOf(s1));
                holder.btn1.setVisibility(View.INVISIBLE);
                holder.btn2.setVisibility(View.VISIBLE);
            }
        });

        holder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseActivity.startChronometer(v);
                s2 = CourseActivity.getTime(v)
                        - Integer.parseInt(holder.textViewS1.getText().toString());
                holder.textViewS2.setText(String.valueOf(s2));
                holder.btn2.setVisibility(View.INVISIBLE);
                holder.btnPs.setVisibility(View.VISIBLE);
            }
        });

        holder.btnPs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseActivity.startChronometer(v);
                ps = CourseActivity.getTime(v) - s1 - s2;
                holder.pitStop.setText(String.valueOf(ps));
                holder.btnPs.setVisibility(View.INVISIBLE);
                holder.btn3.setVisibility(View.VISIBLE);
            }
        });

        holder.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseActivity.startChronometer(v);
                s3 = CourseActivity.getTime(v) - s1 - s2 - ps;
                holder.textViewS3.setText(String.valueOf(s3));
                holder.btn3.setVisibility(View.INVISIBLE);
                holder.btn4.setVisibility(View.VISIBLE);
            }
        });

        holder.btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Participant d = dataList.get(holder.getAdapterPosition());
                CourseActivity.startChronometer(v);
                s4 = CourseActivity.getTime(v) - s1 - s2 - ps - s3;
                holder.textViewS4.setText(String.valueOf(s4));
                holder.btn3.setVisibility(View.INVISIBLE);
                holder.btn4.setVisibility(View.VISIBLE);
                CourseActivity.pauseChronometer(v);

                // Set Time for participant
                database.participantDao().updateTemps(d.getID(),s1,s2,ps,s3,s4);
                // Set Time for équipe
                Integer tempsEquipe = database.participantDao().getEquipeByParticipantID(d.getID()).getTemps();
                int temps;
                if (tempsEquipe == null) {
                    temps = s1+s2+s3+s4+ps;
                } else {
                    temps = tempsEquipe + (s1+s2+s3+s4+ps);
                }

                database.equipeDao().updateTemps(d.getEquipe_id(), temps);
                notifyDataSetChanged();

            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNom,textViewS1,textViewS2,textViewS3,textViewS4,pitStop;
        Button btn, btn1 ,btn2 ,btn3 , btn4 ,btnPs ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNom = itemView.findViewById(R.id.text_view_nom);
            textViewS1 = itemView.findViewById(R.id.text_view_s1);
            textViewS2 = itemView.findViewById(R.id.text_view_s2);
            textViewS3 = itemView.findViewById(R.id.text_view_s3);
            textViewS4 = itemView.findViewById(R.id.text_view_s4);
            pitStop = itemView.findViewById(R.id.text_view_ps);
            btn1 = itemView.findViewById(R.id.btn_sp1);
            btn2 = itemView.findViewById(R.id.btn_sp2);
            btn3 = itemView.findViewById(R.id.btn_sp3);
            btn4 = itemView.findViewById(R.id.btn_sp4);
            btnPs = itemView.findViewById(R.id.btn_ps);




        }
    }
}
