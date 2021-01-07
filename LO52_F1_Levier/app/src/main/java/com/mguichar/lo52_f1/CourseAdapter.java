package com.mguichar.lo52_f1;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{

    private List<Equipe> equipeList = new ArrayList<>();
    private List<Coureur> cList = new ArrayList<>();
    private Activity context;
    private RoomDB database;
    private int i = 0, j = 666;
    private CoureurAdapter coureurAdapter;
    private EquipeAdapter equipeAdapter;

    public CourseAdapter(Activity context, List<Equipe> equipeList, CoureurAdapter cAdapter){
        this.context = context;
        this.equipeList = equipeList;
        this.coureurAdapter = cAdapter;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_courses, parent, false);
        return new CourseAdapter.ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position){
        Equipe equipe = equipeList.get(position);
        database = RoomDB.getInstance(context);
        cList = database.coureurDao().getAll();
        int k= 0;
        

        holder.nom.setText(equipe.getNom());
        holder.start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                database = RoomDB.getInstance(context);
                Equipe e = equipeList.get(holder.getAdapterPosition());
                Coureur coureur = new Coureur();
                int eID = e.getEquipe_ID();
                int cID = 0;

                if(i == 0){
                    cID = e.getCoureurById(i);
                    coureur = database.coureurDao().getCoureurById(cID).get(0);

                    switch(j){

                        case 666:
                            courses.startMinutes = courses.minutes;
                            courses.startSeconds = courses.seconds;
                            j = 0;
                            break;
                        case 0:
                            coureur.setSprint1((courses.minutes - courses.startMinutes) * 60 + (courses.seconds - courses.startSeconds));
                            holder.etape.setText("Coureur 1 - Sprint 1");
                            //System.out.println(j + " " + holder.etape.getText());
                            database.coureurDao().updateSprint1(cID, coureur.getSprint1());
                            cList.clear();
                            cList.addAll(database.coureurDao().getAll());
                            System.out.println("TEMPS: " + coureur.getSprint1());
                            //notifyDataSetChanged(); CAUSES ERROR
                            ++j;
                            //i = 0;
                            break;
                        case 1:
                            coureur.setObstacle1((courses.minutes - courses.startMinutes) * 60 + (courses.seconds - courses.startSeconds));
                            holder.etape.setText("Coureur 1 - Obstacle 1");
                            //System.out.println(j + " " + holder.etape.getText());
                            database.coureurDao().updateObstacle1(cID, coureur.getObstacle1());
                            cList.clear();
                            cList.addAll(database.coureurDao().getAll());
                            System.out.println("TEMPS: " + coureur.getObstacle1());
                            //i = 0;
                            ++j;
                            break;
                        case 2:
                            coureur.setPitstop((courses.minutes - courses.startMinutes) * 60 + (courses.seconds - courses.startSeconds));
                            holder.etape.setText("Coureur 1 - Pitstop");
                            database.coureurDao().updatePitStop(cID, coureur.getPitstop());
                            cList.clear();
                            cList.addAll(database.coureurDao().getAll());
                            System.out.println("TEMPS: " + coureur.getPitstop());
                            //notifyDataSetChanged();
                            //i = 0;
                            ++j;
                            break;
                        case 3:
                            coureur.setObstacle2((courses.minutes - courses.startMinutes) * 60 + (courses.seconds - courses.startSeconds));
                            holder.etape.setText("Coureur 1 - Obstacle 2");
                            database.coureurDao().updateObstacle2(cID, coureur.getObstacle2());
                            cList.clear();
                            cList.addAll(database.coureurDao().getAll());
                            System.out.println("TEMPS: " + coureur.getObstacle2());
                            //notifyDataSetChanged();
                            //i = 0;
                            ++j;
                            break;
                        case 4:
                            coureur.setSprint2((courses.minutes - courses.startMinutes) * 60 + (courses.seconds - courses.startSeconds));
                            coureur.setHasRun(true);
                            holder.etape.setText("Coureur 1 - Sprint 2");
                            database.coureurDao().updateSprint2(cID, coureur.getSprint2());
                            database.coureurDao().heHasRun(cID, true);
                            cList.clear();
                            cList.addAll(database.coureurDao().getAll());
                            //notifyDataSetChanged();
                            courses.startMinutes = courses.minutes;
                            courses.startSeconds = courses.seconds;
                            System.out.println("TEMPS: " + coureur.getSprint2());
                            j = 666;
                            ++i;
                            break;
                    }
                }
                else if(i == 1){
                    System.out.println("MINUTES " + courses.minutes + " SECONDES " + courses.seconds);
                    //courses.startMinutes = courses.minutes;
                    //courses.startSeconds = courses.seconds;
                    cID = e.getCoureurById(i);
                    coureur = database.coureurDao().getCoureurById(cID).get(0);

                    switch(j){

                        case 666:
                            courses.startMinutes = courses.minutes;
                            courses.startSeconds = courses.seconds;
                            holder.etape.setText("Changement de coureur...");
                            j = 0;
                            break;
                        case 0:
                            coureur.setSprint1((courses.minutes - courses.startMinutes) * 60 + (courses.seconds - courses.startSeconds));
                            holder.etape.setText("Coureur 2 - Sprint 1");
                            database.coureurDao().updateSprint1(cID, coureur.getSprint1());
                            cList.clear();
                            cList.addAll(database.coureurDao().getAll());
                            //notifyDataSetChanged();
                            ++j;
                            System.out.println("TEMPS: " + coureur.getSprint1());
                            break;
                        case 1:
                            coureur.setObstacle1((courses.minutes - courses.startMinutes) * 60 + (courses.seconds - courses.startSeconds));
                            holder.etape.setText("Coureur 2 - Obstacle 1");
                            database.coureurDao().updateObstacle1(cID, coureur.getObstacle1());
                            cList.clear();
                            cList.addAll(database.coureurDao().getAll());
                            //notifyDataSetChanged();
                            ++j;
                            System.out.println("TEMPS: " + coureur.getObstacle1());
                            break;
                        case 2:
                            coureur.setPitstop((courses.minutes - courses.startMinutes) * 60 + (courses.seconds - courses.startSeconds));
                            holder.etape.setText("Coureur 2 - Pitstop");
                            database.coureurDao().updatePitStop(cID, coureur.getPitstop());
                            cList.clear();
                            cList.addAll(database.coureurDao().getAll());
                            //notifyDataSetChanged();
                            ++j;
                            System.out.println("TEMPS: " + coureur.getPitstop());
                            break;
                        case 3:
                            coureur.setObstacle2((courses.minutes - courses.startMinutes) * 60 + (courses.seconds - courses.startSeconds));
                            holder.etape.setText("Coureur 2 - Obstacle 2");
                            database.coureurDao().updateObstacle2(cID, coureur.getObstacle2());
                            cList.clear();
                            cList.addAll(database.coureurDao().getAll());
                            //notifyDataSetChanged();
                            System.out.println("TEMPS: " + coureur.getObstacle2());
                            ++j;
                            break;
                        case 4:
                            coureur.setSprint2((courses.minutes - courses.startMinutes) * 60 + (courses.seconds - courses.startSeconds));
                            coureur.setHasRun(true);
                            holder.etape.setText("Coureur 2 - Sprint 2");
                            database.coureurDao().updateSprint2(cID, coureur.getSprint2());
                            database.coureurDao().heHasRun(cID, true);
                            cList.clear();
                            cList.addAll(database.coureurDao().getAll());
                            //notifyDataSetChanged();
                            System.out.println("TEMPS: " + coureur.getSprint2());
                            ++i;
                            j = 666;
                            courses.startMinutes = courses.minutes;
                            courses.startSeconds = courses.seconds;
                            break;
                    }
                }
                else if(i == 2){
                    cID = e.getCoureurById(i);
                    coureur = database.coureurDao().getCoureurById(cID).get(0);

                    switch(j){

                        case 666:
                            courses.startMinutes = courses.minutes;
                            courses.startSeconds = courses.seconds;
                            j = 0;
                            holder.etape.setText("Changement de coureur...");
                            break;
                        case 0:
                            coureur.setSprint1((courses.minutes - courses.startMinutes) * 60 + (courses.seconds - courses.startSeconds));
                            holder.etape.setText("Coureur 3 - Sprint 1");
                            System.out.println("TEMPS: " + coureur.getSprint1());
                            database.coureurDao().updateSprint1(cID, coureur.getSprint1());
                            cList.clear();
                            cList.addAll(database.coureurDao().getAll());
                            //notifyDataSetChanged();
                            ++j;
                            break;
                        case 1:
                            coureur.setObstacle1((courses.minutes - courses.startMinutes) * 60 + (courses.seconds - courses.startSeconds));
                            holder.etape.setText("Coureur 3 - Obstacle 1");
                            System.out.println("TEMPS: " + coureur.getObstacle1());
                            database.coureurDao().updateObstacle1(cID, coureur.getObstacle1());
                            cList.clear();
                            cList.addAll(database.coureurDao().getAll());
                            //notifyDataSetChanged();
                            ++j;
                            break;
                        case 2:
                            coureur.setPitstop((courses.minutes - courses.startMinutes) * 60 + (courses.seconds - courses.startSeconds));
                            holder.etape.setText("Coureur 3 - Pitstop");
                            System.out.println("TEMPS: " + coureur.getPitstop());
                            database.coureurDao().updatePitStop(cID, coureur.getPitstop());
                            cList.clear();
                            cList.addAll(database.coureurDao().getAll());
                            //notifyDataSetChanged();
                            ++j;
                            break;
                        case 3:
                            coureur.setObstacle2((courses.minutes - courses.startMinutes) * 60 + (courses.seconds - courses.startSeconds));
                            holder.etape.setText("Coureur 3 - Obstacle 2");
                            System.out.println("TEMPS: " + coureur.getObstacle2());
                            database.coureurDao().updateObstacle2(cID, coureur.getObstacle2());
                            cList.clear();
                            cList.addAll(database.coureurDao().getAll());
                            //notifyDataSetChanged();
                            ++j;
                            break;
                        case 4:
                            coureur.setSprint2((courses.minutes - courses.startMinutes) * 60 + (courses.seconds - courses.startSeconds));
                            coureur.setHasRun(true);
                            holder.etape.setText("Coureur 3 - Sprint 2");
                            System.out.println("TEMPS: " + coureur.getSprint2());
                            database.coureurDao().updateSprint2(cID, coureur.getSprint2());
                            database.coureurDao().heHasRun(cID, true);
                            cList.clear();
                            cList.addAll(database.coureurDao().getAll());
                            //notifyDataSetChanged();
                            ++j;
                            break;
                        case  5:
                            holder.etape.setText("Fini !");
                            holder.start.setVisibility(View.GONE);
                            holder.stats.setVisibility(View.VISIBLE);

                            equipeList.clear();
                            equipeList.addAll(database.equipeDao().getAll());
                            //notifyDataSetChanged();
                            i = 0;
                            j = 666;
                            break;
                    }
                }
                if(i == 4){

                    notifyDataSetChanged();;
                    i = 0;
                }
                //holder.etape.setText(String.format("%d:%02d", courses.minutes, courses.seconds));
            }

        });

        holder.stats.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                database = RoomDB.getInstance(context);
                Equipe e = equipeList.get(holder.getAdapterPosition());
                int[] bestas = new int[5];
                int sprint = 0, cycle = 0, obstacle = 0, pitstop = 0;
                bestas[1] = 0;

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_equipe_stats);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);

                final TextView bestcycle = dialog.findViewById(R.id.bestcycle);
                final TextView averagecycletime = dialog.findViewById(R.id.averagecycletime);
                final TextView bestsprint = dialog.findViewById(R.id.bestsprint);
                final TextView bestobstacle = dialog.findViewById(R.id.bestobstacle);
                final TextView bestpitstop = dialog.findViewById(R.id.bestpitstop);

                for(int i = 0; i < 3; ++i){

                    Coureur c = database.coureurDao().getCoureurById(equipe.getCoureurById(i)).get(0);

                    c.setObstacle1(c.getObstacle1() - c.getSprint1());
                    database.coureurDao().updateObstacle1(c.getCoureur_ID(), c.getObstacle1());
                    c.setPitstop(c.getPitstop() - (c.getObstacle1() + c.getSprint1()));
                    database.coureurDao().updatePitStop(c.getCoureur_ID(), c.getPitstop());
                    c.setObstacle2(c.getObstacle2() - (c.getPitstop() + c.getSprint1() + c.getObstacle1()));
                    database.coureurDao().updateObstacle2(c.getCoureur_ID(), c.getObstacle2());
                    c.setSprint2(c.getSprint2() - (c.getPitstop() + c.getSprint1() + c.getObstacle1() + c.getObstacle2()));
                    database.coureurDao().updateObstacle2(c.getCoureur_ID(), c.getObstacle2());

                    System.out.println(c.getSprint1() + " " + c.getObstacle1()+ " " + c.getPitstop() + " " + c.getObstacle2() + " " + c.getSprint2());
                }
                cList.clear();
                cList.addAll(database.coureurDao().getAll());
                notifyDataSetChanged();


                for(int i = 0; i < 3; ++i){

                    Coureur c = database.coureurDao().getCoureurById(equipe.getCoureurById(i)).get(0);
                    bestas[1] += c.getTotalTime();

                    if(i == 0) {
                        bestas[0] = c.getCoureur_ID();
                        bestas[2] = c.getCoureur_ID();
                        bestas[3] = c.getCoureur_ID();
                        bestas[4] = c.getCoureur_ID();
                        cycle = c.getTotalTime();
                        sprint = c.getSprint1() + c.getSprint2();
                        obstacle = c.getObstacle1() + c.getObstacle2();
                        pitstop = c.getPitstop();
                    }
                    else{
                        if (c.getTotalTime() < cycle) {
                            bestas[0] = c.getCoureur_ID();
                            cycle = c.getTotalTime();
                        }
                        if(c.getSprint1() + c.getSprint2() < sprint) {
                            bestas[2] = c.getCoureur_ID();
                            sprint = c.getSprint1() + c.getSprint2();
                        }
                        if(c.getObstacle1() + c.getObstacle2() < obstacle){
                            bestas[3] = c.getCoureur_ID();
                            obstacle = c.getObstacle1() + c.getObstacle2();
                        }
                        if(c.getPitstop() < pitstop){
                            bestas[4] = c.getCoureur_ID();
                            pitstop = c.getPitstop();
                        }
                    }
                }

                for(int k = 0; k < 5; ++k) {
                    System.out.println(bestas[k]);
                }

                bestcycle.setText("Meilleur cycle : " + database.coureurDao().getCoureurById(bestas[0]).get(0).getFullName() + "(" + String.valueOf(cycle) + "s)");
                averagecycletime.setText("Temps moyen : " + String.valueOf(bestas[1]/3) + "s");
                bestsprint.setText("Meilleur sprint : " + database.coureurDao().getCoureurById(bestas[2]).get(0).getFullName() + "(" + String.valueOf(sprint) + "s)");
                bestobstacle.setText("Meilleur obstacle : " + database.coureurDao().getCoureurById(bestas[3]).get(0).getFullName() + "(" + String.valueOf(obstacle) + "s)");
                bestpitstop.setText("Meilleur pitstop : " + database.coureurDao().getCoureurById(bestas[4]).get(0).getFullName() + "(" + String.valueOf(pitstop) + "s)");

                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount(){
        return equipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nom;
        TextView etape;
        TextView timerValue;
        ImageView start;
        ImageView stats;
        int i, j;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            i = 0;
            j = 0;
            nom = itemView.findViewById(R.id.nom_equipe);
            etape = itemView.findViewById(R.id.nom_etape);
            timerValue = itemView.findViewById(R.id.hiddenTimerText);
            start = itemView.findViewById(R.id.start);
            stats = itemView.findViewById(R.id.equiêStats);

            start.setVisibility(View.VISIBLE);
            stats.setVisibility(View.GONE);
        }
    }
}
