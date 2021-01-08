package com.djira.f1levier.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class EquipeAdapter extends RecyclerView.Adapter<EquipeAdapter.ViewHolder> {
    // Initialize variable
    private List<Equipe> dataList;
    private FragmentActivity context;
    private AppDB database;

    // Create constructor

    public EquipeAdapter(FragmentActivity context, List<Equipe> dataList) {
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_equipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        // initialize main data
        Equipe data = dataList.get(position);
        // Initialize database
        database = AppDB.getInstance(context);
        //Set text on text view
        holder.btnEquipe.setText(data.getName());

        holder.btnEquipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Equipe d = dataList.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, CourseActivity.class);
                intent.putExtra("EQUIPE_NAME", String.valueOf(d.getName()));
                context.startActivity(intent);
            }
        });


//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
//                android.R.layout.simple_list_item_2, prenoms);
//        holder.mListView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btnEquipe;
        ListView mListView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnEquipe = itemView.findViewById(R.id.text_view_equipe);
            //mListView = (ListView) itemView.findViewById(R.id.listView);

        }
    }
}
