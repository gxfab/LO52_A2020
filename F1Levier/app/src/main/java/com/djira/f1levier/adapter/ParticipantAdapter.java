package com.djira.f1levier.adapter;

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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.djira.f1levier.R;
import com.djira.f1levier.database.AppDB;
import com.djira.f1levier.entity.Participant;

import java.util.List;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ViewHolder> {
    // Initialize variable
    private List<Participant> dataList;
    private FragmentActivity context;
    private AppDB database;

    // Create constructor

    public ParticipantAdapter(FragmentActivity context, List<Participant> dataList) {
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // initialize main data
        Participant data = dataList.get(position);
        // Initialize database
        database = AppDB.getInstance(context);
        //Set text on text view
        holder.textView.setText(data.getName());
        holder.textViewLevel.setText(String.valueOf(data.getLevel()));

        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Participant d = dataList.get(holder.getAdapterPosition());
                final int sID = d.getID();
                String sText = d.getName();
                int sLevel = d.getLevel();

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.getWindow().setLayout(width, height);

                dialog.show();

                final EditText editName = dialog.findViewById(R.id.edit_text);
                final EditText editLevel = dialog.findViewById(R.id.editTextLevel);
                Button btUpdate = dialog.findViewById(R.id.bt_update);

                editName.setText(sText);
                editLevel.setText(String.valueOf(sLevel));

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // dismiss dialog
                        dialog.dismiss();
                        // get updated text from edit text
                        String uName = editName.getText().toString().trim();
                        String uLevel = editLevel.getText().toString().trim();

                        // update text in data
                        database.participantDao().update(sID, uName, Integer.parseInt(uLevel));
                        // notification
                        dataList.clear();
                        dataList.addAll(database.participantDao().getAll());
                        notifyDataSetChanged();
                    }
                });


            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Participant d = dataList.get(holder.getAdapterPosition());
                database.participantDao().delete(d);
                int position = holder.getAdapterPosition();
                dataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataList.size());

            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView,textViewLevel;
        ImageView btEdit, btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            textViewLevel = itemView.findViewById(R.id.text_view_level);
            btEdit = itemView.findViewById(R.id.bt_edit);
            btDelete = itemView.findViewById(R.id.bt_delete);


        }
    }
}
