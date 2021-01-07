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

import java.util.List;

public class CoureurAdapter extends RecyclerView.Adapter<CoureurAdapter.ViewHolder> {

    private List<Coureur> coureurList;
    private Activity context;
    private RoomDB database;

    public CoureurAdapter(Activity context, List<Coureur> coureurList){
        this.context = context;
        this.coureurList = coureurList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_coureurs, parent, false);
        return new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Coureur coureur = coureurList.get(position);
        database = RoomDB.getInstance(context);

        holder.nom.setText(coureur.getNom());
        holder.prenom.setText((coureur.getPrenom()));
        holder.niveau.setText(String.valueOf(coureur.getNiveau()));

        holder.edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Coureur c = coureurList.get(holder.getAdapterPosition());
                int cID = c.getCoureur_ID();
                String nom = c.getNom();
                String prenom = c.getPrenom();
                int niveau = c.getNiveau();

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_coureur_update);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);
                dialog.show();

                final EditText eNom = dialog.findViewById(R.id.edit_nom);
                final EditText ePrenom = dialog.findViewById(R.id.edit_prenom);
                final EditText eNiveau = dialog.findViewById(R.id.edit_niveau);
                Button update = dialog.findViewById(R.id.coureur_update);

                eNom.setText(nom);
                ePrenom.setText(prenom);
                eNiveau.setText(String.valueOf(niveau));

                update.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        dialog.dismiss();
                        String uNom = eNom.getText().toString().trim();
                        String uPrenom = ePrenom.getText().toString().trim();
                        int uNiveau;

                        try{
                            uNiveau = Integer.parseInt(eNiveau.getText().toString().trim());
                        }catch (NumberFormatException e){

                            uNiveau = 0;
                        }

                        database.coureurDao().update(cID, uNom, uPrenom, uNiveau);
                        coureurList.clear();
                        coureurList.addAll(database.coureurDao().getAll());
                        notifyDataSetChanged();
                    }
                });

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Coureur c = coureurList.get(holder.getAdapterPosition());
                database.coureurDao().delete(c);

                int position = holder.getAdapterPosition();
                coureurList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, coureurList.size());
            }
        });
    }

    @Override
    public int getItemCount(){
        return coureurList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nom, prenom, niveau;
        ImageView edit, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nom = itemView.findViewById(R.id.coureur_text_nom);
            prenom = itemView.findViewById(R.id.coureur_text_prenom);
            niveau = itemView.findViewById(R.id.coureur_text_niveau);
            edit = itemView.findViewById(R.id.coureurs_edit);
            delete = itemView.findViewById(R.id.coureurs_delete);
        }
    }
}
