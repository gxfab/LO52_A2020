package com.mguichar.lo52_f1;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EquipeAdapter extends RecyclerView.Adapter<EquipeAdapter.ViewHolder>{

    private List<Equipe> equipeList;
    private Activity context;
    private RoomDB database;

    public EquipeAdapter(Activity context, List<Equipe> equipeList){
        this.context = context;
        this.equipeList = equipeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_equipes, parent, false);
        return new EquipeAdapter.ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull EquipeAdapter.ViewHolder holder, int position){
        Equipe equipe = equipeList.get(position);
        database = RoomDB.getInstance(context);

        holder.nom.setText(equipe.getNom());

        holder.detail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                database = RoomDB.getInstance(context);
                Equipe e = equipeList.get(holder.getAdapterPosition());
                int eID = e.getEquipe_ID();
                String nom = e.getNom();

                Coureur c1 = database.coureurDao().getCoureurById(e.getId_coureur1()).get(0);
                Coureur c2 = database.coureurDao().getCoureurById(e.getId_coureur2()).get(0);
                Coureur c3 = database.coureurDao().getCoureurById(e.getId_coureur3()).get(0);

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_equipe_detail);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);
                dialog.show();

                final TextView c1_infos = dialog.findViewById(R.id.coureur1_infos);
                final TextView c2_infos = dialog.findViewById(R.id.coureur2_infos);
                final TextView c3_infos = dialog.findViewById(R.id.coureur3_infos);

                c1_infos.setText(c1.getPrenom() + " " + c1.getNom() + " - Niveau " + String.valueOf(c1.getNiveau()));
                c2_infos.setText(c2.getPrenom() + " " + c2.getNom() + " - Niveau " + String.valueOf(c2.getNiveau()));
                c3_infos.setText(c3.getPrenom() + " " + c3.getNom() + " - Niveau " + String.valueOf(c3.getNiveau()));

            }
        });

    }

    @Override
    public int getItemCount(){
        return equipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nom;
        ImageView detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nom = itemView.findViewById(R.id.nom_equipe);
            detail = itemView.findViewById(R.id.equipe_details);
        }
    }
}